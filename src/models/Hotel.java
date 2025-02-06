package models;

public class Hotel extends AbstractEntity {
    private String name;
    private String city;
    private double rank;
    private String location;

    public Hotel(int id, String name, String city, double rank, String location) {
        super(id);  // Наследуем id от AbstractEntity
        this.name = name;
        this.city = city;
        this.rank = rank;
        this.location = location;
    }

    public String getName() { return name; }
    public String getCity() { return city; }
    public double getRank() { return rank; }
    public String getLocation() { return location; }

    public void setName(String name) { this.name = name; }
    public void setCity(String city) { this.city = city; }
    public void setRank(double rank) { this.rank = rank; }
    public void setLocation(String location) { this.location = location; }
}
