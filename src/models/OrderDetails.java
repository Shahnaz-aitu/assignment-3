package models;

public class OrderDetails {
    private int orderId;
    private int productId;
    private int quantity;
    private String productName;
    private String category;

    public OrderDetails(int orderId, int productId, int quantity, String productName, String category) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.category = category;
    }

    public int getOrderId() {
        return orderId;
    }
    public int getProductId() {
        return productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getProductName() {
        return productName;
    }
    public String getCategory() {
        return category;
    }
}
