public class HotelFactory {

    public static Hotel createHotel(int id, String name, String address, double rating) {
        return new Hotel(id, name, address, rating);
    }
}