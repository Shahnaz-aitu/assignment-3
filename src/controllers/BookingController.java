package controllers;

import controllers.interfaces.IBookingController;
import models.*;
import repositories.interfaces.*;

import java.util.Date;

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
        // Проверка роли пользователя
        User user = userRepository.getUserByEmail(userEmail);
        if (user == null || !user.hasPermission("CREATE_BOOKING")) {
            System.out.println("Ошибка: недостаточно прав для создания бронирования.");
            return false;
        }

        // Проверка доступности номера
        Room room = roomRepository.getRoomById(roomId);
        if (room == null || !RoomValidator.isValid(room)) {
            System.out.println("Ошибка: некорректные данные номера.");
            return false;
        }

        if (!roomRepository.isRoomAvailable(roomId, checkIn, checkOut)) {
            System.out.println("Ошибка: номер недоступен на выбранные даты.");
            return false;
        }

        // Создание бронирования
        Booking booking = new Booking(user.getId(), roomId, checkIn, checkOut);
        return bookingRepository.createBooking(booking);
    }

    public BookingDetails getFullBookingDescription(int bookingId) {
        Booking booking = bookingRepository.getBookingById(bookingId);
        if (booking == null) {
            return null;
        }

        User user = userRepository.getUserById(booking.getUserId());
        Room room = roomRepository.getRoomById(booking.getRoomId());
        Hotel hotel = hotelRepository.getHotelById(room.getHotelId());

        return new BookingDetails(booking, user, room, hotel);
    }

    // Метод для проверки прав доступа
    private boolean checkAccess(User user, String action) {
        return user != null && user.hasPermission(action);
    }
}