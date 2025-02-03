package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import data.interfaces.IDB;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private String userName;
    private String userEmail;
    private List<OrderDetails> orderDetails;

    public Order(int orderId, int userId, String userName, String userEmail, List<OrderDetails> orderDetails) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.orderDetails = orderDetails;
    }

    public int getOrderId() { return orderId; }
    public int getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public List<OrderDetails> getOrderDetails() { return orderDetails; }

    public static Order getFullOrderDescription(int orderId, IDB db) {
        String sql = "SELECT o.order_id, o.user_id, u.name AS user_name, u.email AS user_email, " +
                "od.product_id, p.name AS product_name, p.price, c.name AS category_name, od.quantity " +
                "FROM orders o " +  // исправлено на нижний регистр
                "JOIN users u ON o.user_id = u.id " +
                "LEFT JOIN orderdetails od ON o.order_id = od.order_id " +  // LEFT JOIN, чтобы показать заказы без товаров
                "LEFT JOIN products p ON od.product_id = p.id " +
                "LEFT JOIN categories c ON p.category_id = c.id " +
                "WHERE o.order_id = ?";

        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            List<OrderDetails> details = new ArrayList<>();
            int userId = -1;
            String userName = "", userEmail = "";

            while (rs.next()) {
                if (userId == -1) { // заполняем данные клиента только один раз
                    userId = rs.getInt("user_id");
                    userName = rs.getString("user_name");
                    userEmail = rs.getString("user_email");
                }
                if (rs.getInt("product_id") != 0) { // избегаем null-товаров
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
}
