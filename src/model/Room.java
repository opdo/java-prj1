package model;

public class Room implements IRoom {

    protected String roomNumber;
    protected Double price;
    protected RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    public Room() {
    }


    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    public String toString() {
        String roomInfo = "- Room number: " + getRoomNumber();
        switch (getRoomType()) {
            case SINGLE:
                roomInfo += " | Single Bed";
                break;
            case DOUBLE:
                roomInfo += " | Double Bed";
                break;
        }

        roomInfo += " | Price: $" + getRoomPrice() + " per night";

        return roomInfo;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setEnumeration(RoomType enumeration) {
        this.enumeration = enumeration;
    }
}
