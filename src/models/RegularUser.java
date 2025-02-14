package models;

import java.util.Set;

public class RegularUser extends User {
    public RegularUser(int id, String name, String email, int age, String password) {
        super(id, name, email, age, password, Role.USER);
        this.permissions.add(Permission.CREATE_BOOKING);
        this.permissions.add(Permission.VIEW_ROOMS); // ✅ Обычный юзер может бронировать и смотреть номера
    }
}
