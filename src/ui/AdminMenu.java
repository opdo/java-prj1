package ui;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu extends BaseMenu {
    public static AdminMenu reference = new AdminMenu();
    public static AdminMenu getInstance() {
        return reference;
    }
    public AdminMenu() {
        title = "Admin Menu";
        listMenu.add("See all Customers");
        listMenu.add("See all Rooms");
        listMenu.add("See all Reservations");
        listMenu.add("Add a Room");
        listMenu.add("[TEST] Add Test Data");
        listMenu.add("Back to Main Menu");
    }

    @Override
    public void handleUserInputMenu() {
        int userInput = getUserInputMenu();
        switch (userInput) {
            case 1:
                seeAllCustomer();
                break;
            case 2:
                seeAllRoom();
                break;
            case 3:
                seeAllReservation();
                break;
            case 4:
                addRoom();
                break;
            case 5:
                addTestData();
                break;
            case 6:
                MainMenu.getInstance().printMenu();
                break;
            default:
                System.out.println("Error: doesn't support menu " + userInput);
                handleUserInputMenu();
        }

        super.handleUserInputMenu();
    }

    public void addTestData() {
        List<IRoom> rooms = new ArrayList<>();
        rooms.add(new Room("100", 100.1, RoomType.SINGLE));
        rooms.add(new FreeRoom("102", RoomType.SINGLE));
        rooms.add(new Room("201", 200.1, RoomType.DOUBLE));
        rooms.add(new Room("203", 250.5, RoomType.SINGLE));
        rooms.add(new Room("304", 99.9, RoomType.DOUBLE));
        rooms.add(new Room("401", 100.0, RoomType.SINGLE));
        rooms.add(new Room("500", 1000.5, RoomType.DOUBLE));
        rooms.add(new Room("501", 800.5, RoomType.DOUBLE));
        rooms.add(new Room("503", 203.3, RoomType.DOUBLE));
        rooms.add(new FreeRoom("600", RoomType.SINGLE));

        AdminResource.getInstance().addRoom(rooms);
        HotelResource.getInstance().createACustomer("vinh@test.com", "Vinh", "Pham Ngoc");
    }

    public void addRoom() {
        List<IRoom> rooms = new ArrayList<>();

        while (true) {
            // Input room info
            IRoom room = inputARoom();
            rooms.add(room);

            // Question to user add more or not
            Boolean addMoreFlag = handleUserYesNoQuestion("Would you like to add another room?");

            // Add more room
            if (Boolean.FALSE.equals(addMoreFlag)) {
                break;
            }
        }

        // Add list room
        AdminResource.getInstance().addRoom(rooms);
    }

    private IRoom inputARoom() {
        Scanner scanner = new Scanner(System.in);
        IRoom addRoomModel;
        String roomNumber;
        double price;
        int type;

        do {
            System.out.println("Enter room number");
            roomNumber = scanner.nextLine();
            if (AdminResource.getInstance().isRoomNumberExist(roomNumber)) {
                System.out.println("The room number is already exist. Please input another room number!");
                continue;
            }

            break;
        } while (true);

        do {
            try {
                System.out.println("Enter price by night");
                scanner = new Scanner(System.in);
                price = scanner.nextDouble();
                if (price < 0) {
                    System.out.println("The price should not be negative number!");
                    continue;
                }

                break;
            } catch (Exception ex) {
                System.out.println("Error format, please input again!");
            }
        } while (true);

        do {
            try {
                System.out.println("Enter room type, 1 for single bed, 2 for double bed");
                scanner = new Scanner(System.in);
                type = scanner.nextInt();

                if (type != 1 && type != 2) {
                    System.out.println("Please input 1 for single bed, 2 for double bed!");
                    continue;
                }

                break;
            } catch (Exception ex) {
                System.out.println("Error format, please input again!");
            }
        } while (true);

        if (price <= 0.0) addRoomModel = new FreeRoom();
        else addRoomModel = new Room();

        addRoomModel.setRoomNumber(roomNumber);
        addRoomModel.setPrice(price);
        switch (type) {
            case 1:
                addRoomModel.setEnumeration(RoomType.SINGLE);
                break;
            case 2:
                addRoomModel.setEnumeration(RoomType.DOUBLE);
                break;
        }

        return addRoomModel;
    }

    public void seeAllRoom() {
        System.out.println();
        System.out.println("LIST ROOM");
        System.out.println("-----------------------------------------------------");
        if (AdminResource.getInstance().getAllRooms().isEmpty())
        {
            System.out.println("Empty list!");
        }
        else {
            for (IRoom room : AdminResource.getInstance().getAllRooms()) {
                System.out.println(room.toString());
            }
        }
        System.out.println("-----------------------------------------------------");
    }

    public void seeAllCustomer() {
        System.out.println();
        System.out.println("LIST CUSTOMER");
        System.out.println("-----------------------------------------------------");
        if (AdminResource.getInstance().getAllCustomers().isEmpty())
        {
            System.out.println("Empty list!");
        }
        else {
            for (Customer customer : AdminResource.getInstance().getAllCustomers()) {
                System.out.println(customer.toString());
            }
        }
        System.out.println("-----------------------------------------------------");
    }

    public void seeAllReservation() {
        System.out.println();
        System.out.println("LIST RESERVATION");
        System.out.println("-----------------------------------------------------");
        AdminResource.getInstance().displayAllReservations();
        System.out.println("-----------------------------------------------------");
    }
}
