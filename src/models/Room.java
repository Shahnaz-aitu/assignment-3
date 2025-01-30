package models;

public enum RoomCategory {
    STANDARD,
    DELUXE,
    SUITE,
    PRESIDENTIAL
}

public class Room {
    private int id;
    private int hotelId;
    private String type;
    private double price;
    private boolean isAvailable;
    private RoomCategory category;

    public Room(int id, int hotelId, String type, double price, boolean isAvailable, RoomCategory category) {
        this.id = id;
        this.hotelId = hotelId;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
        this.category = category;
    }

    public Room(int id, String type, double price, int hotelId) {
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    public RoomCategory getCategory() { return category; }
    public void setCategory(RoomCategory category) { this.category = category; }

    public static class RoomValidator {
        public static boolean isValid(Room room) {
            return room != null 
                && room.getPrice() > 0 
                && room.getType() != null
                && room.getCategory() != null;
        }
    }
}