package models;

import java.util.Arrays;

public class AdminUser extends User {
    public AdminUser(int id, String name, String email, int age, String password) {
        super(id, name, email, age, password, Role.ADMIN);
        this.permissions.addAll(Arrays.asList(Permission.values())); // ✅ Админ получает все права
    }
}
