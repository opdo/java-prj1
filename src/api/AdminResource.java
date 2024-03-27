package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminResource {
    public static AdminResource reference = new AdminResource();
    public static AdminResource getInstance() {
        return reference;
    }

    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            ReservationService.getInstance().addRoom(rooms.get(i));
        }
    }

    public boolean isRoomNumberExist(String roomNumber) {
        return ReservationService.getInstance().getRooms().containsKey(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        Collection<IRoom> rooms = new ArrayList<>();
        ReservationService.getInstance().getRooms().forEach((key, room) -> rooms.add(room));
        return rooms;
    }

    public Collection<Customer> getAllCustomers() {
        return CustomerService.getInstance().getAllCustomers();
    }

    public void displayAllReservations() {
        ReservationService.getInstance().printAllReservation();
    }
}
