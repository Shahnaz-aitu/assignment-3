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
        String sql = "INSERT INTO hotels (name, city, rank, location) VALUES (?, ?, ?, ?)";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getCity());
            stmt.setDouble(3, hotel.getRank());
            stmt.setString(4, hotel.getLocation());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Hotel getHotelById(int id) {
        String sql = "SELECT * FROM hotels WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getDouble("rank"),
                        rs.getString("location")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Hotel> getAllHotels() {
        String sql = "SELECT * FROM hotels";
        List<Hotel> hotels = new ArrayList<>();
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hotels.add(new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getDouble("rank"),
                        rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    @Override
    public List<Hotel> getHotelsByCity(String city) {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE city ILIKE ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + city + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hotels.add(new Hotel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getDouble("rank"),
                        rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    @Override
    public boolean updateHotel(Hotel hotel) {
        String sql = "UPDATE hotels SET name = ?, city = ?, rank = ?, location = ? WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getCity());
            stmt.setDouble(3, hotel.getRank());
            stmt.setString(4, hotel.getLocation());
            stmt.setInt(5, hotel.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteHotel(int id) {
        String sql = "DELETE FROM hotels WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
