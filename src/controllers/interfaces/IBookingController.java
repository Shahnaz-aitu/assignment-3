package controllers.interfaces;

import java.util.Date;

public interface IBookingController {
    boolean createBooking(String userEmail, int roomId, Date checkIn, Date checkOut);

    void showUserBookings(String email);

    void showFullBookingDescription(int bookingId);
}
