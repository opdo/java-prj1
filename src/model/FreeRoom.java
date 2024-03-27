package model;

public class FreeRoom extends Room {
    public FreeRoom() {
        price = 0.0;
    }

    public FreeRoom(String roomNumber, RoomType type) {
        this.setRoomNumber(roomNumber);
        this.setEnumeration(type);
        price = 0.0;
    }

    @Override
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

        roomInfo += " | Price: FREE";

        return roomInfo;
    }
}
