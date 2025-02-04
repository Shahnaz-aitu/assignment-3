package controllers;

import controllers.interfaces.IBookingController;
import models.*;
import repositories.interfaces.*;
import services.AuthorizationService;
import services.AuthorizationException;
import models.Permission;

import java.util.Date;
import java.util.List;

public class BookingController implements IBookingController {
    private final IBookingRepository bookingRepository;
    private final IUserRepository userRepository;
    private final IRoomRepository roomRepository;
    private final IHotelRepository hotelRepository;

    public BookingController(
            IBookingRepository bookingRepository,
            IUserRepository userRepository,
            IRoomRepository roomRepository,
            IHotelRepository hotelRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public boolean createBooking(String userEmail, int roomId, Date checkIn, Date checkOut) {
        User user = userRepository.getUserByEmail(userEmail);
        try {
            AuthorizationService.checkPermission(user, Permission.CREATE_BOOKING);
        } catch (AuthorizationException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return false;
        }

        Room room = roomRepository.getRoomById(roomId);
        if (room == null || !Room.RoomValidator.isValid(room)) {
            System.out.println("Ошибка: некорректные данные номера.");
            return false;
        }

        if (!roomRepository.isRoomAvailable(roomId, checkIn, checkOut)) {
            System.out.println("Ошибка: номер недоступен на выбранные даты.");
            return false;
        }

        Booking booking = new Booking(user.getId(), roomId, checkIn, checkOut);
        boolean success = bookingRepository.createBooking(booking);

        if (success) {
            System.out.println("✅ Бронирование успешно создано!");
        } else {
            System.out.println("❌ Ошибка при создании бронирования.");
        }

        return success;
    }

    public void showUserBookings(String userEmail) {
        User user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            System.out.println("Ошибка: пользователь не найден.");
            return;
        }

        List<BookingDetails> bookings = bookingRepository.getUserBookings(user.getId());
        if (bookings.isEmpty()) {
            System.out.println("Нет активных бронирований.");
            return;
        }

        System.out.println("Ваши бронирования:");
        for (BookingDetails booking : bookings) {
            System.out.println("- Номер " + booking.getRoom().getType() + " в " +
                    booking.getHotel().getName() + " с " +
                    booking.getBooking().getCheckIn() + " по " + booking.getBooking().getCheckOut());
        }
    }
}
