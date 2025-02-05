
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
    public User getUserByEmail(String email, User currentUser) {
        System.out.println("🔍 Поиск пользователя по email: " + email +
                (currentUser != null ? " (Запрос от: " + currentUser.getEmail() + ")" : ""));

        // Если currentUser == null (например, при входе), НЕ проверяем роль
        if (currentUser != null && !"ADMIN".equalsIgnoreCase(currentUser.getRole().toString())) {
            System.out.println("❌ Ошибка: Только администраторы могут искать пользователей по email.");
            return null;
        }

        return userRepository.getUserByEmail(email);
    }

    @Override
    public User createUser(String name, String email, int age, String password) {
        if (!DataValidator.isValidEmail(email)) {
            System.out.println("❌ Ошибка: Некорректный email. Введите email в формате example@mail.com.");
            return null;
        }
        if (!DataValidator.isValidPassword(password)) {
            System.out.println("❌ Ошибка: Пароль должен быть минимум 6 символов.");
            return null;
        }
        if (!DataValidator.isValidName(name)) {
            System.out.println("❌ Ошибка: Имя не может быть пустым или слишком коротким.");
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

    // Метод для получения userRepository (если нужно где-то еще)
    public IUserRepository getUserRepository() {
        return userRepository;
    }
}
