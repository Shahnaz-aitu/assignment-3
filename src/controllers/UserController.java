package controllers;

import controllers.interfaces.IUserController;
import models.Permission;
import models.User;
import repositories.interfaces.IUserRepository;
import data.DataValidator;

public class UserController implements IUserController {
    private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User createUser(String name, String email, int age, String password) {
        if (age < 18) {
            System.out.println("❌ Ошибка: возраст должен быть 18 лет или старше.");
            return null;
        }
        return userRepository.createUser(name, email, age, password);
    }

    @Override
    public User searchUser(String query) {
        User foundUser = userRepository.searchUser(query);
        if (foundUser == null) {
            System.out.println("❌ Пользователь не найден.");
        } else {
            System.out.println("✅ Найден пользователь: " + foundUser.getName() + " (Email: " + foundUser.getEmail() + ")");
        }
        return foundUser;
    }

    @Override
    public boolean deleteUser(String email, User currentUser) {
        System.out.println("🔍 Удаление пользователя: " + email + " (Запрос от: " + currentUser.getEmail() + ")");

        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole().toString())) {
            System.out.println("❌ Ошибка: У вас нет прав на удаление пользователей.");
            return false;
        }

        boolean success = userRepository.deleteUser(email);
        if (success) {
            System.out.println("✅ Пользователь " + email + " успешно удален.");
        } else {
            System.out.println("❌ Ошибка: пользователь " + email + " не найден или не удалось удалить.");
        }

        return success;
    }

    // Добавленный метод, чтобы исправить ошибку "cannot find symbol method getUserRepository()"
    public IUserRepository getUserRepository() {
        return userRepository;
    }
}
