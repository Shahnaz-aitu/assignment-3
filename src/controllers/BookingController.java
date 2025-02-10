package controllers;

import controllers.interfaces.IBookingController;
import models.*;
import repositories.interfaces.*;

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
        System.out.println("🔎 Проверка пользователя...");
        User user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            System.out.println("❌ Ошибка: пользователь не найден.");
            return false;
        }

        // ✅ Проверяем, имеет ли пользователь право на бронирование
        if (!user.hasPermission(Permission.CREATE_BOOKING)) {
            System.out.println("❌ Ошибка: у вас нет прав на бронирование.");
            return false;
        }

        System.out.println("✅ Пользователь найден: " + user.getName());

        // ✅ Проверяем существование номера
        System.out.println("🔎 Проверка номера...");
        Room room = roomRepository.getRoomById(roomId);
        if (room == null) {
            System.out.println("❌ Ошибка: номер не найден.");
            return false;
        }

        // ✅ Проверяем доступность номера
        if (!roomRepository.isRoomAvailable(roomId, checkIn, checkOut)) {
            System.out.println("❌ Ошибка: номер уже забронирован на эти даты.");
            return false;
        }

        System.out.println("✅ Номер доступен: " + room.getRoomType() + " | Цена: " + room.getPrice());

        // ✅ Создаем бронирование
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
        System.out.println("🔎 Проверка бронирований пользователя...");
        User user = userRepository.getUserByEmail(userEmail);
        if (user == null) {
            System.out.println("❌ Ошибка: пользователь не найден.");
            return;
        }

        List<BookingDetails> bookings = bookingRepository.getUserBookings(user.getId());
        if (bookings.isEmpty()) {
            System.out.println("⚠️ У вас нет активных бронирований.");
            return;
        }

        System.out.println("📌 Ваши бронирования:");
        for (BookingDetails booking : bookings) {
            System.out.println("- Номер " + booking.getRoom().getRoomType() + " в " +
                    booking.getHotel().getName() + " с " +
                    booking.getBooking().getCheckIn() + " по " + booking.getBooking().getCheckOut());
        }
    }

    public void showFullBookingDescription(int bookingId) {
        System.out.println("🔎 Проверка бронирования с ID: " + bookingId);
        BookingDetails details = bookingRepository.getFullBookingDescription(bookingId);
        if (details == null) {
            System.out.println("❌ Ошибка: бронирование не найдено.");
            return;
        }

        System.out.println("📌 Полная информация о бронировании:");
        System.out.println("- ID бронирования: " + details.getBooking().getId());
        System.out.println("- Гость: " + (details.getUser() != null ? details.getUser().getName() + " (Email: " + details.getUser().getEmail() + ")" : "Unknown"));
        System.out.println("- Номер: " + (details.getRoom() != null ? details.getRoom().getRoomType() : "Unknown") + ", Цена: " + (details.getRoom() != null ? details.getRoom().getPrice() : "0"));
        System.out.println("- Отель: " + (details.getHotel() != null ? details.getHotel().getName() : "Unknown"));
        System.out.println("- Даты: с " + details.getBooking().getCheckIn() + " по " + details.getBooking().getCheckOut());
    }
}

