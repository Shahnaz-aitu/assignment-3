package repositories;

import data.interfaces.IDB;
import models.Booking;
import models.BookingDetails;
import models.Hotel;
import models.Room;
import models.User;
import repositories.interfaces.IBookingRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements IBookingRepository {
    private final IDB db;

    public BookingRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, room_id, check_in_date, check_out_date, status) VALUES (?, ?, ?, ?, 'CONFIRMED')";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getRoomId());
            stmt.setDate(3, new java.sql.Date(booking.getCheckIn().getTime()));
            stmt.setDate(4, new java.sql.Date(booking.getCheckOut().getTime()));
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Booking getBookingById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookingDetails> getUserBookings(int userId) {
        List<BookingDetails> bookings = new ArrayList<>();
        String sql = "SELECT b.id, b.room_id, b.check_in_date, b.check_out_date, " +
                "COALESCE(r.room_type, 'Unknown') AS room_type, COALESCE(r.price, 0) AS room_price, " +
                "COALESCE(h.name, 'Unknown') AS hotel_name " +
                "FROM bookings b " +
                "LEFT JOIN rooms r ON b.room_id = r.id " +
                "LEFT JOIN hotels h ON r.hotel_id = h.id " +
                "WHERE b.user_id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("id"),
                        userId,
                        rs.getInt("room_id"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date")
                );
                BookingDetails details = new BookingDetails(
                        booking,
                        null,
                        new Room(rs.getInt("room_id"), 0, rs.getString("room_type"),
                                rs.getDouble("room_price"), true, null),
                        new Hotel(0, rs.getString("hotel_name"), "", 0, "")
                );
                bookings.add(details);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public BookingDetails getFullBookingDescription(int bookingId) {
        System.out.println("🔎 Проверка бронирования с ID: " + bookingId);

        String sql = "SELECT b.id AS booking_id, b.check_in_date, b.check_out_date, " +
                "b.status, u.id AS user_id, u.name AS user_name, u.email AS user_email, " +
                "COALESCE(u.age, 18) AS user_age, " +
                "COALESCE(r.id, 0) AS room_id, COALESCE(r.room_type, 'Unknown') AS room_type, COALESCE(r.price, 0) AS room_price, " +
                "COALESCE(h.id, 0) AS hotel_id, COALESCE(h.name, 'Unknown') AS hotel_name " +
                "FROM bookings b " +
                "LEFT JOIN users u ON b.user_id = u.id " +
                "LEFT JOIN rooms r ON b.room_id = r.id " +
                "LEFT JOIN hotels h ON r.hotel_id = h.id " +
                "WHERE b.id = ?";

        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("⚠️ Бронирование с ID " + bookingId + " не найдено в БД.");
                return null;
            }

            System.out.println("✅ Найдено бронирование с ID: " + bookingId);

            Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("room_id"),
                    rs.getDate("check_in_date"),
                    rs.getDate("check_out_date")
            );

            int age = rs.getInt("user_age");
            System.out.println("📌 Проверяем возраст пользователя: " + age);

            User user = null;
            if (rs.getInt("user_id") != 0) {
                if (age < 18) {
                    System.out.println("⚠️ Возраст пользователя меньше 18, устанавливаем 18.");
                    age = 18;
                }
                user = new User(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        age
                );
            }

            Room room = (rs.getInt("room_id") != 0) ?
                    new Room(rs.getInt("room_id"), 0, rs.getString("room_type"), rs.getDouble("room_price"), true, null)
                    : null;

            Hotel hotel = (rs.getInt("hotel_id") != 0) ?
                    new Hotel(rs.getInt("hotel_id"), rs.getString("hotel_name"), "", 0, "")
                    : null;

            return new BookingDetails(booking, user, room, hotel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Проверяет, доступен ли номер на указанные даты.
     */
    public boolean isRoomAvailable(int roomId, java.sql.Date checkIn, java.sql.Date checkOut) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE room_id = ? " +
                "AND ((check_in_date BETWEEN ? AND ?) OR (check_out_date BETWEEN ? AND ?))";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setDate(2, checkIn);
            stmt.setDate(3, checkOut);
            stmt.setDate(4, checkIn);
            stmt.setDate(5, checkOut);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Если 0, значит номер доступен
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
