package models;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private double rating;

    // Конструкторы
    public Hotel() {}

    public Hotel(int id, String name, String address, double rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
    }

    // Геттеры и сеттеры
   public int getId() {
        return id;
   }
   public void setId(int id) {
        this.id = id;
   }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}