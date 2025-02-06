package models;

import java.util.HashSet;
import java.util.Set;

public class User extends AbstractEntity {
    private String name;
    private String email;
    private int age;
    private String password;
    private Role role;
    private Set<Permission> permissions = new HashSet<>();

    // ✅ Конструктор с id (Используется в UserRepository)
    public User(int id, String name, String email, int age, String password, Role role) {
        super(id);  // Наследуем id от AbstractEntity
        this.name = name;
        this.email = email;
        setAge(age);
        this.password = password;
        this.role = role;

        // Добавляем разрешения по умолчанию
        this.permissions.add(Permission.CREATE_BOOKING);
        this.permissions.add(Permission.VIEW_ROOMS);

        // ✅ Если пользователь - админ, даем все права
        if (role == Role.ADMIN) {
            for (Permission p : Permission.values()) {
                this.permissions.add(p);
            }
        }
    }

    // ✅ Другие конструкторы (без изменений)
    public User(int id, String name, String email, int age) {
        this(id, name, email, age, null, Role.USER);
    }

    public User(String name, String email, int age, String password) {
        this(0, name, email, age, password, Role.USER);
    }

    // ✅ Методы (убираем id, потому что он есть в AbstractEntity)
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public Role getRole() { return role; }
    public String getPassword() { return password; }
    public Set<Permission> getPermissions() { return new HashSet<>(permissions); }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) {
        if (age < 18) {
            System.out.println("⚠️ Возраст меньше 18, автоматически исправляем.");
            this.age = 18;
        } else {
            this.age = age;
        }
    }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }

    public boolean hasPermission(Permission permission) {
        // ✅ Администратор получает все права автоматически
        if (this.role == Role.ADMIN) {
            return true;
        }
        // Остальные роли должны иметь явное разрешение
        return this.permissions.contains(permission);
    }
}
