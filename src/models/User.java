package models;

import java.util.HashSet;
import java.util.Set;

// ✅ Делаем User абстрактным классом
public abstract class User extends AbstractEntity {
    protected String name;
    protected String email;
    protected int age;
    protected String password;
    protected Role role;
    protected Set<Permission> permissions = new HashSet<>();

    public User(int id, String name, String email, int age, String password, Role role) {
        super(id);
        this.name = name;
        this.email = email;
        setAge(age);
        this.password = password;
        this.role = role;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public Role getRole() { return role; }
    public String getPassword() { return password; }
    public Set<Permission> getPermissions() { return new HashSet<>(permissions); }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }

    public void setAge(int age) {
        if (age < 18) {
            System.out.println("⚠️ Возраст меньше 18, автоматически исправляем.");
            this.age = 18;
        } else {
            this.age = age;
        }
    }

    // ✅ Убрали логику с проверкой на ADMIN, теперь это в AdminUser
    public boolean hasPermission(Permission permission) {
        return this.permissions.contains(permission);
    }
}
