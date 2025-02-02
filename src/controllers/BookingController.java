package controllers;

import controllers.interfaces.IBookingController;
import models.Booking;
import models.User;
import repositories.interfaces.IBookingRepository;
import repositories.interfaces.IRoomRepository;
import repositories.interfaces.IUserRepository;

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
        // Проверяем доступность номера
        if (!roomRepository.isRoomAvailable(roomId, checkIn, checkOut)) {
            System.out.println("Ошибка: Номер уже забронирован на эти даты.");
            return false;
        }

        // Получаем пользователя или создаем нового гостя
        User user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            int defaultAge = 25; // Возраст по умолчанию для гостя
            String defaultPassword = "guest123"; // Генерируемый пароль для гостей
            user = userRepository.createUser("Guest", userEmail, defaultAge, defaultPassword);
        }

        // Создаем объект бронирования
        Booking booking = new Booking(user.getId(), roomId, checkIn, checkOut);
        return bookingRepository.createBooking(booking);
    }
}