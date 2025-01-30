package controllers;

import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IUserRepository;
import repositories.interfaces.IRoomRepository;
import models.Booking;
import models.User;

import java.util.Date;

public class BookingController {
    private final IBookingRepository bookingRepository;
    private final IUserRepository userRepository;
    private final IRoomRepository roomRepository;

    public BookingController(IBookingRepository bookingRepository, IUserRepository userRepository, IRoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public boolean createBooking(String userName, String userEmail, int roomId, Date checkIn, Date checkOut) {
        if (!roomRepository.isRoomAvailable(roomId, checkIn, checkOut)) {
            System.out.println("Номер недоступен для бронирования.");
            return false;
        }

        User user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            user = userRepository.createUser(userName, userEmail);
        }

        Booking booking = new Booking(user.getId(), roomId, checkIn, checkOut);
        return bookingRepository.createBooking(booking);
    }
}
