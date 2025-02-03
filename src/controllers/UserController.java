package controllers;

import controllers.interfaces.IUserController;
import models.User;
import models.Role;
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
        User newUser = new User(name, email, age, password);
        if (!DataValidator.isValidUser(newUser)) {
            System.out.println("Ошибка: возраст должен быть 18 лет или старше.");
            return null;
        }
        return userRepository.createUser(name, email, age, password);
    }

    @Override
    public User searchUser(String query) {
        return null;
    }

    @Override
    public boolean deleteUser(String email, User currentUser) {
        // ✅ Теперь проверяем роль правильно
        if (currentUser.getRole() != Role.ADMIN) {
            System.out.println("❌ Ошибка: только администратор может удалять пользователей.");
            return false;
        }

        // ✅ Если роль ADMIN, удаляем пользователя
        boolean success = userRepository.deleteUser(email);
        if (success) {
            System.out.println("✅ Пользователь успешно удален.");
        } else {
            System.out.println("❌ Ошибка при удалении пользователя.");
        }
        return success;
    }
}
