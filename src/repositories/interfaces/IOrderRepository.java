package repositories.interfaces;

import models.Order;
import data.interfaces.IDB;

public interface IOrderRepository {
    // Другие методы репозитория...

    Order getFullOrderDescription(int orderId, IDB db);
}
