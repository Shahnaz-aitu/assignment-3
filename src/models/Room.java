package models;

public class Room {
    private int id;
    private int hotelId;
    private String type;
    private double price;
    private boolean isAvailable;

    public Room(int id, int hotelId, String type, double price, boolean isAvailable) {
        this.id = id;
        this.hotelId = hotelId;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    // Геттеры и сеттеры
}