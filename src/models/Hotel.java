package models;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private double rating;

    //builder pattern Конструкторы
    public Hotel() {}

    public Hotel(int id, String name, String address, double rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
    }
    public static class Builder {
        private int id;
        private String name;
        private String address;
        private double rating;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public Hotel build() {
            return new Hotel();
        }
    }
}
