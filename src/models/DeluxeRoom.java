package models;

public class DeluxeRoom extends Room {
    public DeluxeRoom(int id, int hotelId, double price, boolean isAvailable, RoomCategory category) {
        super(id, hotelId, price * 1.2, isAvailable, category); // ✅ +20% к цене
    }

    @Override
    public String getRoomType() {
        return "Deluxe";
    }
}
