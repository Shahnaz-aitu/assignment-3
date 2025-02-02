package repositories;

import data.interfaces.IDB;
import models.Booking;
import repositories.interfaces.IBookingRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingRepository implements IBookingRepository {
    private final IDB db;

    public BookingRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO Bookings (user_id, room_id, check_in_date, check_out_date, status) VALUES (?, ?, ?, ?, 'CONFIRMED')";
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
        String sql = "SELECT b.user_id, b.room_id, b.check_in_date, b.check_out_date, " +
                "u.name AS user_name, u.email AS user_email, " +
                "r.type AS room_type, r.price AS room_price, r.hotel_id, " +
                "h.name AS hotel_name, h.address AS hotel_address, h.rating AS hotel_rating " +
                "FROM Bookings b " +
                "JOIN Users u ON b.user_id = u.id " +
                "JOIN Rooms r ON b.room_id = r.id " +
                "JOIN Hotels h ON r.hotel_id = h.hotel_id " +
                "WHERE b.booking_id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Создаём объект Booking с базовыми данными.
                // Если потребуется, можно расширить логику и создать объект BookingDetails с полученными данными.
                Booking booking = new Booking(
                        rs.getInt("user_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in_date"),
                        rs.getDate("check_out_date")
                );
                return booking;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
