package models;

public class User {
    private int id;
    private String name;
    private String email;
    private int age;
    private String password;
    private Role role;

    // Конструктор с id и строковой ролью (используется при получении из БД)
    public User(int id, String name, String email, int age, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        setAge(age);
        this.password = password;
        setRole(role); // Конвертация строки в Role
    }

    // Конструктор без id (используется при регистрации нового пользователя)
    public User(String name, String email, int age, String password) {
        this.name = name;
        this.email = email;
        setAge(age);
        this.password = password;
        this.role = Role.USER;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("Ошибка: возраст должен быть больше 18 лет.");
        }
        this.age = age;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }

    // Новый метод setRole, который принимает строку из БД
    public void setRole(String role) {
        try {
            if (role != null) {
                this.role = Role.valueOf(role.toUpperCase());
            } else {
                this.role = Role.USER; // Если роль null, назначаем USER
            }
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Ошибка: Некорректная роль '" + role + "', назначаем USER.");
            this.role = Role.USER; // Если роль неизвестна, назначаем USER
        }
    }

    public void setRole(Role role) { this.role = role; }

    // Метод проверки прав через роль
    public boolean hasPermission(Role requiredRole) {
        return this.role == requiredRole || this.role == Role.ADMIN; // Админ может все
    }

    @Override
    public String toString() {
        return "Пользователь {" +
                "ID=" + id +
                ", Имя='" + name + '\'' +
                ", Email='" + email + '\'' +
                ", Возраст=" + age +
                ", Роль=" + role +
                '}';
    }
}
