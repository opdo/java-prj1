package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private final IRoom room;
    private final Customer customer;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(IRoom room, Customer customer, Date checkInDate, Date checkOutDate) {
        this.room = room;
        this.customer = customer;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getCustomerEmail() {
        return customer.getEmail();
    }

    public String getRoomNumber() {
        return room.getRoomNumber();
    }

    public String getFormatCheckInDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(checkInDate);
    }

    public String getFormatCheckOutDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(checkOutDate);
    }

    public String toString() {
        return "Reservation\nRoom number: " + room.getRoomNumber() + " | Customer: " + customer.getFullName() + " | Check In: " + getFormatCheckInDate() + " | Check Out: " + getFormatCheckOutDate();
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }
}
