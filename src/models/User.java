package models;

import java.util.HashSet;
import java.util.Set;

public class User {
    private int id;
    private String name;
    private String email;
    private int age;
    private String password;
    private Role role;
    private Set<Permission> permissions = new HashSet<>();

    // Конструктор с id (например, при получении из БД)
    public User(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        setAge(this.age);  // Используем сеттер для проверки
        this.password = password;
        this.role = Role.valueOf(String.valueOf(role));
    }

    // Конструктор без id (при регистрации нового пользователя)
    public User(String name, String email, int age, String password) {
        this.name = name;
        this.email = email;
        setAge(age);
        this.password = password;
        this.role = Role.USER;
        // Добавляем базовые разрешения для пользователя
        this.permissions.add(Permission.CREATE_BOOKING);
        this.permissions.add(Permission.VIEW_ROOMS);
    }

    // Геттеры
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getAge() {
        return age;
    }
    public Role getRole() {
        return role;
    }
    public String getPassword() {
        return password;
    }
    public Set<Permission> getPermissions() {
        return new HashSet<>(permissions);
    }

    // Сеттеры
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("Возраст должен быть 18 лет или старше.");
        }
        this.age = age;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    // Методы для работы с разрешениями
    public boolean hasPermission(Permission requiredPermission) {
        return this.role == Role.ADMIN || permissions.contains(requiredPermission);
    }
    public void addPermission(Permission permission) {
        permissions.add(permission);
    }
    public void removePermission(Permission permission) {
        permissions.remove(permission);
    }
}
