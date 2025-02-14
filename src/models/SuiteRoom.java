package models;

public class SuiteRoom extends Room {
    public SuiteRoom(int id, int hotelId, double price, boolean isAvailable, RoomCategory category) {
        super(id, hotelId, price * 1.5, isAvailable, category); // ✅ +50% к цене
    }

    @Override
    public String getRoomType() {
        return "Suite";
    }
}
