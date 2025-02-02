package models;

public class User {
    private int id;
    private String name;
    private String email;
    private int age;
    private String password;

    // Конструктор с id (используется при получении из БД)
    public User(int id, String name, String email, int age, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        setAge(age);
        this.password = password;
    }

    // Конструктор без id (используется при регистрации нового пользователя)
    public User(String name, String email, int age, String password) {
        this.name = name;
        this.email = email;
        setAge(age);
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 18) {
            throw new IllegalArgumentException("Ошибка: возраст должен быть больше 18 лет.");
        }
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Пользователь {" +
                "ID=" + id +
                ", Имя='" + name + '\'' +
                ", Email='" + email + '\'' +
                ", Возраст=" + age +
                '}';
    }
}