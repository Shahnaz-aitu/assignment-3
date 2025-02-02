package repositories;

import data.interfaces.IDB;
import models.User;
import repositories.interfaces.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("password")
                );
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении пользователя по email: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User createUser(String name, String email, int age, String password) {
        if (age < 18) {
            System.err.println("Ошибка: возраст должен быть больше 18 лет.");
            return null;
        }

        String sql = "INSERT INTO Users (name, email, age, password) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, age);
            stmt.setString(4, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), name, email, age, password);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при создании пользователя: " + e.getMessage());
        }
        return null;
    }
    @Override
    public boolean deleteUser(String email) {
        String sql = "DELETE FROM Users WHERE email = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Если удалили хотя бы одну строку, возвращаем true
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            return false;
        }
    }


    @Override
    public User searchUser(String query) {
        String sql = "SELECT * FROM Users WHERE name ILIKE ? OR email ILIKE ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("password")
                );
            }
        } catch (Exception e) {
            System.err.println("Ошибка при поиске пользователя: " + e.getMessage());
        }
        return null;
    }
}
