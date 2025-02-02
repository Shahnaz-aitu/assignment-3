package controllers;

import controllers.interfaces.IUserController;
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
        User newUser = new User(name, email, age, password);
        if (!DataValidator.isValidUser(newUser)) {
            System.out.println("Ошибка: возраст должен быть 18 лет или старше.");
            return null;
        }
        return userRepository.createUser(name, email, age, password);
    }

    @Override
    public User searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public boolean deleteUser(String email, User currentUser) {
        if (!currentUser.hasPermission("MANAGE_USERS")) {
            System.out.println("Ошибка: недостаточно прав");
            return false;
        }
        return userRepository.deleteUser(email);
    }
}
