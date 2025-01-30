package repositories.interfaces;

import models.Booking;

public interface IBookingRepository {
    boolean createBooking(Booking booking);
    Booking getBookingById(int id);
}
