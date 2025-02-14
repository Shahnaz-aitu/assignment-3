package repositories;

import data.interfaces.IDB;
import models.*;
import repositories.interfaces.IRepository;
import repositories.interfaces.IUserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository implements IUserRepository, IRepository<User> {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean add(User user) {
        String sql = "INSERT INTO users (name, email, age, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getAge());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole().name());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("❌ Ошибка при добавлении пользователя: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT id, name, email, age, password, role FROM users";
        List<User> users = new ArrayList<>();
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (Exception e) {
            System.err.println("❌ Ошибка при получении всех пользователей: " + e.getMessage());
        }
        return users;
    }

    public List<User> getUsersByRole(Role role) {
        return getAll().stream()
                .filter(user -> user.getRole() == role)
                .collect(Collectors.toList());
    }

    public List<User> getUsersSortedByAge() {
        return getAll().stream()
                .sorted((u1, u2) -> Integer.compare(u1.getAge(), u2.getAge()))
                .collect(Collectors.toList());
    }

    public List<User> searchUsersByName(String query) {
        return getAll().stream()
                .filter(user -> user.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
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
            return stmt.executeUpdate() > 0;
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

        // ✅ Используем админа или обычного пользователя
        User newUser = Role.USER == Role.ADMIN
                ? new AdminUser(0, name, email, age, password)
                : new RegularUser(0, name, email, age, password);

        if (add(newUser)) {
            return newUser;
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
    public User getById(int id) {
        return getUserById(id);
    }

    @Override
    public User searchUser(String query) {
        return getAll().stream()
                .filter(user -> user.getName().toLowerCase().contains(query.toLowerCase()) ||
                        user.getEmail().toLowerCase().contains(query.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public IDB getDb() {
        return db;
    }

    private User mapUser(ResultSet rs) throws Exception {
        Role role = Role.valueOf(rs.getString("role").toUpperCase());

        // ✅ Проверяем роль пользователя и создаем нужный объект
        return role == Role.ADMIN
                ? new AdminUser(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age"),
                rs.getString("password")
        )
                : new RegularUser(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age"),
                rs.getString("password")
        );
    }
}
