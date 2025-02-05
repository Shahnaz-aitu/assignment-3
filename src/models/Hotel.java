package models;

public class Hotel {
    private int id;
    private String name;
    private String city;  // ✅ Добавлено поле city
    private double rank;
    private String location;

    public Hotel(int id, String name, String city, double rank, String location) {
        this.id = id;
        this.name = name;
        this.city = city;  // ✅ Добавлено city
        this.rank = rank;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {  // ✅ Теперь этот метод точно есть!
        return city;
    }

    public double getRank() {
        return rank;
    }

    public String getLocation() {
        return location;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
