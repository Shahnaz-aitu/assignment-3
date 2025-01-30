package repositories;

import data.interfaces.IDB; // ✅ Исправленный импорт
import repositories.interfaces.IUserRepository;
import models.User;

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
                        rs.getString("email")
                );
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении пользователя по email: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User createUser(String name, String email) {
        String sql = "INSERT INTO Users (name, email) VALUES (?, ?) RETURNING id";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        name,
                        email
                );
            }
        } catch (Exception e) {
            System.err.println("Ошибка при создании пользователя: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
