package models;

public class Room {
    private int id;
    private int hotelId;
    private String roomType; // Изменено с "type" на "roomType"
    private double price;
    private boolean isAvailable;
    private RoomCategory category;

    public Room(int id, int hotelId, String roomType, double price, boolean isAvailable, RoomCategory category) {
        this.id = id;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
        this.category = category;
    }

    public int getId() { return id; }
    public int getHotelId() { return hotelId; }
    public String getRoomType() { return roomType; } // Изменено с "getType" на "getRoomType"
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public RoomCategory getCategory() { return category; }

    public void setId(int id) { this.id = id; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public void setRoomType(String roomType) { this.roomType = roomType; } // Изменено с "setType"
    public void setPrice(double price) { this.price = price; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setCategory(RoomCategory category) { this.category = category; }

    public String getType() {
        return "";
    }

    public static class RoomValidator {
        public static boolean isValid(Room room) {
            return room != null
                    && room.getPrice() > 0
                    && room.getRoomType() != null // Изменено с "getType"
                    && room.getCategory() != null;
        }
    }
}
