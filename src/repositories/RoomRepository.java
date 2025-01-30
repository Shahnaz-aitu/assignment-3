import data.interfaceces.IDB;
import repositories.interfaces.IRoomRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class RoomRepository implements IRoomRepository {
    private final IDB db;

    public RoomRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut) {
        String sql = """
            SELECT COUNT(*) 
            FROM Bookings 
            WHERE room_id = ? 
              AND ((check_in_date BETWEEN ? AND ?) 
                OR (check_out_date BETWEEN ? AND ?)
                OR (? BETWEEN check_in_date AND check_out_date))
            """;

        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, roomId);
            stmt.setDate(2, new java.sql.Date(checkIn.getTime()));
            stmt.setDate(3, new java.sql.Date(checkOut.getTime()));
            stmt.setDate(4, new java.sql.Date(checkIn.getTime()));
            stmt.setDate(5, new java.sql.Date(checkOut.getTime()));
            stmt.setDate(6, new java.sql.Date(checkIn.getTime()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
