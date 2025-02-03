package models;

import java.util.List;

public class OrderFactory {
    public static Order createOrder(int userId, List<OrderDetails> details) {
        return new Order(0, userId, "Новый пользователь", "email@placeholder.com", details);
    }
}
