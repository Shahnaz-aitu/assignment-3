package models;

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

    public int getId() { return id; }
    public int getHotelId() { return hotelId; }
    public String getType() { return type; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public RoomCategory getCategory() { return category; }

    public void setId(int id) { this.id = id; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public void setType(String type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setCategory(RoomCategory category) { this.category = category; }

    // Внутренний класс-валидатор остаётся без изменений
    public static class RoomValidator {
        public static boolean isValid(Room room) {
            return room != null
                    && room.getPrice() > 0
                    && room.getType() != null
                    && room.getCategory() != null;
        }
    }
}
