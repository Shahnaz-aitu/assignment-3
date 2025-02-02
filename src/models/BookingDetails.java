package models;

public class BookingDetails {
    private final Booking booking;
    private final User user;
    private final Room room;
    private final Hotel hotel;

    public BookingDetails(Booking booking, User user, Room room, Hotel hotel) {
        this.booking = booking;
        this.user = user;
        this.room = room;
        this.hotel = hotel;
    }

    // Геттеры для всех полей
    public Booking getBooking() { return booking; }
    public User getUser() { return user; }
    public Room getRoom() { return room; }
    public Hotel getHotel() { return hotel; }

    @Override
    public String toString() {
        return String.format(
                "Бронирование №%d\n" +
                        "Гость: %s (%s)\n" +
                        "Отель: %s\n" +
                        "Номер: %s (Категория: %s)\n" +
                        "Даты: %s - %s",
                booking.getId(),  // Метод getId() должен быть добавлен в класс Booking
                user.getName(),
                user.getEmail(),
                hotel.getName(),
                room.getType(),
                room.getCategory(),
                booking.getCheckIn(),
                booking.getCheckOut()
        );
    }
}
