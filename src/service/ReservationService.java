package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    public static ReservationService reference = new ReservationService();
    private final Collection<Reservation> reservations;
    private final Map<String, IRoom> rooms;

    public ReservationService() {
        reservations = new ArrayList<>();
        rooms = new HashMap<>();
    }

    public static ReservationService getInstance() {
        return reference;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation data = new Reservation(room, customer, checkInDate, checkOutDate);
        reservations.add(data);
        return data;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> roomsAvailable = new ArrayList<>();
        rooms.forEach((key, room) -> {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                roomsAvailable.add(room);
            }
        });

        return roomsAvailable;
    }

    public boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        for (Reservation data : reservations) {
            if (room.getRoomNumber().equals(data.getRoomNumber())) {
                // Check is available
                if (checkInDate.compareTo(data.getCheckInDate()) >= 0 && checkInDate.compareTo(data.getCheckOutDate()) <= 0) {
                    return false;
                }

                if (checkOutDate.compareTo(data.getCheckInDate()) >= 0 && checkOutDate.compareTo(data.getCheckOutDate()) <= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> result = new ArrayList<>();
        reservations.forEach(data -> {
            if (customer.getEmail().equals(data.getCustomerEmail())) {
                result.add(data);
            }
        });

        return result;
    }

    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("Empty list!");
            return;
        }
        reservations.forEach(data -> System.out.println(data.toString()));
    }

    public Map<String, IRoom> getRooms() {
        return rooms;
    }
}
