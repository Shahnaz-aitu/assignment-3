package repositories;

import data.interfaces.IDB;
import models.Role;
import models.User;
import repositories.interfaces.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT id, name, email, age, password, role FROM users WHERE email = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? mapUser(rs) : null;
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении пользователя по email: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("✅ Пользователь с email " + email + " успешно удален.");
                return true;
            } else {
                System.out.println("❌ Ошибка: Пользователь с email " + email + " не найден.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при удалении пользователя: " + e.getMessage());
            return false;
        }
    }

    @Override
    public User createUser(String name, String email, int age, String password) {
        if (age < 18) {
            System.err.println("❌ Ошибка: возраст должен быть больше 18 лет.");
            return null;
        }

        String sql = "INSERT INTO users (name, email, age, password, role) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setInt(3, age);
            stmt.setString(4, password);
            stmt.setString(5, "USER");

            ResultSet rs = stmt.executeQuery();
            return rs.next() ? new User(rs.getInt("id"), name, email, age, password, Role.USER) : null;
        } catch (Exception e) {
            System.err.println("❌ Ошибка при создании пользователя: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User searchUser(String query) {
        System.out.println("🔍 Поиск пользователя: " + query);

        String sql = "SELECT id, name, email, age, password, role FROM users WHERE email ILIKE ? OR name ILIKE ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, "%" + query.trim() + "%");
            stmt.setString(2, "%" + query.trim() + "%");

            System.out.println("📌 SQL-запрос: " + stmt.toString());

            ResultSet rs = stmt.executeQuery();
            List<User> users = new ArrayList<>();

            while (rs.next()) {
                users.add(mapUser(rs));
            }

            return users.stream().findFirst().orElse(null);
        } catch (Exception e) {
            System.err.println("🚨 Ошибка при поиске пользователя: " + e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT id, name, email, age, password, role FROM users WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? mapUser(rs) : null;
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении пользователя по ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public IDB getDb() {
        return db;
    }

    /**
     * ✅ **Метод для маппинга пользователя из ResultSet**
     */
    private User mapUser(ResultSet rs) throws Exception {
        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age"),
                rs.getString("password"),
                Role.valueOf(rs.getString("role").toUpperCase()) // Fix role case
        );
    }
}

