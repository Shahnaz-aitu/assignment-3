package repositories;

import data.interfaces.IDB;
import models.Order;
import models.OrderDetails;
import repositories.interfaces.IOrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {

    private final IDB db;

    public OrderRepository(IDB db) {
        this.db = db;
    }

    @Override
    public Order getFullOrderDescription(int orderId, IDB db) {
        String sql = "SELECT o.order_id, o.user_id, u.name AS user_name, u.email AS user_email, " +
                "od.product_id, p.name AS product_name, p.price, c.name AS category_name, od.quantity " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.id " +
                "LEFT JOIN orderdetails od ON o.order_id = od.order_id " +
                "LEFT JOIN products p ON od.product_id = p.id " +
                "LEFT JOIN categories c ON p.category_id = c.id " +
                "WHERE o.order_id = ?";

        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            List<OrderDetails> details = new ArrayList<>();
            int userId = -1;
            String userName = "", userEmail = "";

            while (rs.next()) {
                if (userId == -1) { // Заполняем данные покупателя только один раз
                    userId = rs.getInt("user_id");
                    userName = rs.getString("user_name");
                    userEmail = rs.getString("user_email");
                }
                if (rs.getInt("product_id") != 0) {
                    details.add(new OrderDetails(
                            orderId,
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getString("product_name"),
                            rs.getString("category_name")
                    ));
                }
            }
            return new Order(orderId, userId, userName, userEmail, details);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Реализация других методов репозитория...
}
