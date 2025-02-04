package repositories;

import data.interfaces.IDB;
import models.Booking;
import models.BookingDetails;
import models.Hotel;
import models.Room;
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
                "r.type AS room_type, r.price AS room_price, h.name AS hotel_name " +
                "FROM bookings b " +
                "JOIN rooms r ON b.room_id = r.id " +
                "JOIN hotels h ON r.hotel_id = h.id " +
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
}
