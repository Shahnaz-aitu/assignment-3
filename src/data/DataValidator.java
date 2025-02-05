package data;

import models.Order;
import models.User;

public class DataValidator {
    public static boolean isValidOrder(Order order) {
        return order != null && order.getOrderDetails() != null;
    }

    public static boolean isValidUser(User user) {
        return user != null && isValidEmail(user.getEmail()) && isValidPassword(user.getPassword()) && isValidName(user.getName());
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6; // Минимальная длина пароля 6 символов
    }

    public static boolean isValidName(String name) {
        return name != null && name.trim().length() > 1; // Имя не должно быть пустым или слишком коротким
    }
}

