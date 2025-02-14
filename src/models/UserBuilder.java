package models;

public class UserBuilder {
    private String name;
    private String email;
    private int age;
    private String password;
    private boolean isAdmin = false; // Добавляем флаг для админа

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setAdmin(boolean isAdmin) { // Указываем, админ ли это
        this.isAdmin = isAdmin;
        return this;
    }

    public User build() {
        return isAdmin
                ? new AdminUser(0, name, email, age, password)  // Если админ
                : new RegularUser(0, name, email, age, password);  // Если обычный юзер
    }
}
