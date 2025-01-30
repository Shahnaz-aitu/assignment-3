package models;

public class OrderDetails {
    private int orderId;
    private String product;
    private int quantity;

    public OrderDetails(int orderId, String product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
    }

    // Геттеры и сеттеры
} 