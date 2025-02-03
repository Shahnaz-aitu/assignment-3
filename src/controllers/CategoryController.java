package controllers;

import models.Category;
import repositories.interfaces.ICategoryRepository;

import java.util.List;

public class CategoryController {
    private final ICategoryRepository categoryRepository;

    public CategoryController(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(String name) {
        return categoryRepository.createCategory(name);
    }

    public Category getCategory(int id) {
        return categoryRepository.getCategoryById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public boolean updateCategory(int id, String newName) {
        return categoryRepository.updateCategory(id, newName);
    }

    public boolean deleteCategory(int id) {
        return categoryRepository.deleteCategory(id);
    }
}
