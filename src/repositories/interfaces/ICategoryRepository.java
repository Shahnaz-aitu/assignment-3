package repositories.interfaces;

import models.Category;
import java.util.List;

public interface ICategoryRepository {
    Category createCategory(String name);
    Category getCategoryById(int id);
    List<Category> getAllCategories();
    boolean updateCategory(int id, String newName);
    boolean deleteCategory(int id);
}
