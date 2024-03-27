package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu extends BaseMenu {
    public static MainMenu reference = new MainMenu();

    public static MainMenu getInstance() {
        return reference;
    }

    public MainMenu() {
        title = "Main Menu";
        listMenu.add("Find and reserve a room");
        listMenu.add("See my reservations");
        listMenu.add("Create an account");
        listMenu.add("Admin");
        listMenu.add("Exit");
    }

    @Override
    public void handleUserInputMenu() {
        int userInput = getUserInputMenu();
        switch (userInput) {
            case 1:
                findAndReserveARoom();
                break;
            case 2:
                seeMyReservations();
                break;
            case 3:
                createAnAccount();
                break;
            case 4:
                AdminMenu.getInstance().printMenu();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("Error: doesn't support menu " + userInput);
                handleUserInputMenu();
        }

        super.handleUserInputMenu();
    }

    public String createAnAccount() {
        Scanner scanner = new Scanner(System.in);
        String email, fName, lName;

        // Email
        do {
            System.out.println("Enter your email");
            email = scanner.nextLine();
            if (HotelResource.getInstance().isEmailExist(email)) {
                System.out.println("Email address is already registered by another customer!");
                continue;
            }

            try {
                Customer.verifyEmail(email);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
                continue;
            }

            break;
        } while (true);

        System.out.println("Enter your first name");
        fName = scanner.nextLine();

        System.out.println("Enter your last name");
        lName = scanner.nextLine();

        // Create account
        HotelResource.getInstance().createACustomer(email, fName, lName);

        // Success message
        System.out.println("Created account successfully!");

        return email;
    }

    public void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        String email;
        do {
            System.out.println("Enter your email");
            email = scanner.nextLine();
            if (!HotelResource.getInstance().isEmailExist(email)) {
                System.out.println("Email address is not registered by any customer!");
                continue;
            }

            try {
                Customer.verifyEmail(email);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
                continue;
            }

            break;
        } while (true);

        Collection<Reservation> reservations = HotelResource.getInstance().getCustomersReservations(email);
        System.out.println();
        System.out.println("LIST RESERVATION");
        System.out.println("-----------------------------------------------------");
        reservations.forEach(data -> System.out.println(data.toString()));
        System.out.println("-----------------------------------------------------");
    }

    public void findAndReserveARoom() {
        // Format
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        System.out.println("Input check in date (MM/dd/yyyy)");
        Date checkIn = intputDate();

        System.out.println("Input check out date (MM/dd/yyyy)");
        Date checkOut = intputDate();

        if (checkOut.before(checkIn)) {
            System.out.println("Error: check in date can not after check out date!");
            return;
        }

        Collection<IRoom> availableRooms = HotelResource.getInstance().findARoom(checkIn, checkOut);
        if (availableRooms.isEmpty()) {
            // Add 7 days to recomended user book in new range date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkIn);
            calendar.add(Calendar.DATE, 7);
            checkIn = calendar.getTime();

            // Add checkout
            calendar.setTime(checkOut);
            calendar.add(Calendar.DATE, 7);
            checkOut = calendar.getTime();

            Boolean recomendedFlag = handleUserYesNoQuestion("There are no available room in your range date." +
                    "\nDo you want to change check in and check out date to " + formatter.format(checkIn) + " -> " + formatter.format(checkOut) + "?");

            if (!recomendedFlag) return;

            // Find again
            availableRooms = HotelResource.getInstance().findARoom(checkIn, checkOut);
        }

        // Check if still empty ~> show error msg
        if (availableRooms.isEmpty()) {
            System.out.println("There are no available rooms from  " + formatter.format(checkIn) + " -> " + formatter.format(checkOut));
            return;
        }

        System.out.println();
        System.out.println("List room available from  " + formatter.format(checkIn) + " -> " + formatter.format(checkOut));
        System.out.println("-----------------------------------------------------");
        availableRooms.forEach(room -> System.out.println(room.toString()));
        System.out.println("-----------------------------------------------------");

        // Call book room method
        bookRoom(checkIn, checkOut);
    }

    public void bookRoom(Date checkIn, Date checkOut) {
        String email = "";
        Boolean bookRoomFlag = handleUserYesNoQuestion("Would you like to book a room?");
        if (!bookRoomFlag) return;

        Boolean haveAccountFlag = handleUserYesNoQuestion("Do you have an account with us?");
        if (!haveAccountFlag) {
            Boolean createAccountFlag = handleUserYesNoQuestion("Do you want to create an account with us?");
            if (createAccountFlag) {
                email = createAnAccount();
            } else {
                System.out.println("Sorry! You can not book a room without an account with us!");
                return;
            }
        }

        // Enter email to book a room
        while (Objects.equals(email, "")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your email to reserve a room");
            email = scanner.nextLine();
            if (!HotelResource.getInstance().isEmailExist(email)) {
                System.out.println("Email address is not registered by any customer!");
                email = "";
                continue;
            }

            try {
                Customer.verifyEmail(email);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
                email = "";
                continue;
            }

            break;
        }

        // What room number
        IRoom room;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter room number");
            String roomNumber = scanner.nextLine();
            room = HotelResource.getInstance().getRoom(roomNumber);
            if (room == null) {
                System.out.println("The room number is not exist, please check again!");
                continue;
            }

            if (!HotelResource.getInstance().canBookTheRoom(room, checkIn, checkOut)) {
                System.out.println("Can not book the room because it is not available at the time you want, please check again!");
                continue;
            }

            break;
        } while (true);

        // Book room
        System.out.println();
        System.out.println(HotelResource.getInstance().bookARoom(email, room, checkIn, checkOut));
    }

    public Date intputDate() {
        try {
            Scanner scanner = new Scanner(System.in);
            String dateString = scanner.next();
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            return formatter.parse(dateString);
        } catch (Exception ex) {
            System.out.println("Wrong format, please input again!");
            return intputDate();
        }
    }
}
