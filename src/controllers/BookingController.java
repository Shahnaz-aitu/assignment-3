package controllers;

import controllers.interfaces.IBookingController;
import models.Booking;
import models.User;
import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IUserRepository;
import repositories.interfaces.IRoomRepository;


import java.util.Date;

public class BookingController implements IBookingController {
    private final IBookingRepository bookingRepository;
    private final IUserRepository userRepository;
    private final IRoomRepository roomRepository;

    public BookingController(IBookingRepository bookingRepository, IUserRepository userRepository, IRoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public boolean createBooking(String userEmail, int roomId, Date checkIn, Date checkOut) {
        // Проверяем, есть ли метод isRoomAvailable в IRoomRepository
        if (!roomRepository.isRoomAvailable(roomId, checkIn, checkOut)) {
            System.out.println("Ошибка: Номер уже забронирован на эти даты.");
            return false;
        }

        // Получаем пользователя или создаем нового гостя
        User user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            user = userRepository.createUser("Guest", userEmail);
        }

        // Создаем объект бронирования
        Booking booking = new Booking(user.getId(), roomId, checkIn, checkOut);
        return bookingRepository.createBooking(booking);
    }
}
