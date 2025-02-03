package repositories.interfaces;

import models.Product;
import java.util.List;

public interface IProductRepository {
    List<Product> getProductsByCategory(int categoryId);
}
