package models;

import models.interfaces.IBookable;
import java.util.Date;

public abstract class Room extends AbstractEntity implements IBookable {
    protected int hotelId;
    protected double price;
    protected boolean isAvailable;
    protected RoomCategory category;

    public Room(int id, int hotelId, double price, boolean isAvailable, RoomCategory category) {
        super(id);
        this.hotelId = hotelId;
        this.price = price;
        this.isAvailable = isAvailable;
        this.category = category;
    }

    public abstract String getRoomType(); // ✅ Абстрактный метод для типа номера

    @Override
    public boolean isAvailable(Date checkIn, Date checkOut) {
        return isAvailable; // Простая проверка доступности номера
    }

    public int getHotelId() { return hotelId; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public RoomCategory getCategory() { return category; }

    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public void setPrice(double price) { this.price = price; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setCategory(RoomCategory category) { this.category = category; }

    public static class RoomValidator {
        public static boolean isValid(Room room) {
            return room != null
                    && room.getPrice() > 0
                    && room.getCategory() != null;
        }
    }
}
