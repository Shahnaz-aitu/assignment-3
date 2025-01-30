package repositories;

import data.interfaces.IDB;
import models.Hotel;
import repositories.interfaces.IHotelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelRepository implements IHotelRepository {
    private final IDB db;

    public HotelRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createHotel(Hotel hotel) {
        String sql = "INSERT INTO Hotels (name, address, rating) VALUES (?, ?, ?)";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getAddress());
            stmt.setDouble(3, hotel.getRating());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Hotel getHotelById(int id) {
        String sql = "SELECT * FROM Hotels WHERE hotel_id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Hotel(
                        rs.getInt("hotel_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getDouble("rating")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Hotel> getAllHotels() {
        String sql = "SELECT * FROM Hotels";
        List<Hotel> hotels = new ArrayList<>();
        try (Connection con = db.getConnection(); Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                hotels.add(new Hotel(
                        rs.getInt("hotel_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getDouble("rating")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    @Override
    public boolean updateHotel(Hotel hotel) {
        String sql = "UPDATE Hotels SET name = ?, address = ?, rating = ? WHERE hotel_id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getAddress());
            stmt.setDouble(3, hotel.getRating());
            stmt.setInt(4, hotel.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteHotel(int id) {
        String sql = "DELETE FROM Hotels WHERE hotel_id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}