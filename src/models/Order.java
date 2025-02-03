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
    private String orderDate;
    private List<OrderDetails> orderDetails;

    public Order(int orderId, int userId, String orderDate, List<OrderDetails> orderDetails) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
    }

    public int getOrderId() {
        return orderId;
    }
    public int getUserId() {
        return userId;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public static Order getFullOrderDescription(int orderId, IDB db) {
        String sql = "SELECT o.order_id, o.user_id, o.order_date, " +
                "od.product_id, od.quantity, p.name AS product_name, p.category " +
                "FROM Orders o " +
                "JOIN OrderDetails od ON o.order_id = od.order_id " +
                "JOIN Products p ON od.product_id = p.id " +
                "WHERE o.order_id = ?";
        try (Connection con = db.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            List<OrderDetails> detailsList = new ArrayList<>();
            int userId = 0;
            String orderDate = null;
            while (rs.next()) {
                if (orderDate == null) {
                    userId = rs.getInt("user_id");
                    orderDate = rs.getString("order_date");
                }
                OrderDetails details = new OrderDetails(
                        orderId,
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getString("product_name"),
                        rs.getString("category")
                );
                detailsList.add(details);
            }
            if (orderDate != null) {
                return new Order(orderId, userId, orderDate, detailsList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
