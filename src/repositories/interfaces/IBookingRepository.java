package repositories.interfaces;

import models.BookingDetails;
import java.util.List;

public interface IBookingRepository {
    boolean createBooking(models.Booking booking);
    models.Booking getBookingById(int id);

    // Новый метод для получения бронирований пользователя
    List<BookingDetails> getUserBookings(int userId);

    BookingDetails getFullBookingDescription(int bookingId);
}
