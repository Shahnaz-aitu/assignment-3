package repositories;

import data.interfaceces.IDB;
import repositories.interfaces.IBookingRepository;
import models.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

