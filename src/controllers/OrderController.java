package controllers;

import models.Order;
import repositories.interfaces.IOrderRepository;
import data.interfaces.IDB;

public class OrderController {
    private final IOrderRepository orderRepository;
    private final IDB db;

    public OrderController(IOrderRepository orderRepository, IDB db) {
        this.orderRepository = orderRepository;
        this.db = db;
    }

    public Order getFullOrderDescription(int orderId) {
        return orderRepository.getFullOrderDescription(orderId, db);
    }

    // Другие методы контроллера...
}
