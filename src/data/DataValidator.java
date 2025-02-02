package data;

import models.Order;
import models.User;

public class DataValidator {
    public static boolean isValidOrder(Order order) {
        return order != null && order.getOrderDetails() != null;
    }

    public static boolean isValidUser(User user) {
        return user != null && user.getAge() >= 18;
    }
}
