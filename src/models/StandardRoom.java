package models;

public class StandardRoom extends Room {
    public StandardRoom(int id, int hotelId, double price, boolean isAvailable, RoomCategory category) {
        super(id, hotelId, price, isAvailable, category);
    }

    @Override
    public String getRoomType() {
        return "Standard";
    }
}
