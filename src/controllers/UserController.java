package controllers;

import controllers.interfaces.IUserController;
import models.User;
import repositories.interfaces.IUserRepository;

public class UserController implements IUserController {
    private final IUserRepository userRepository;

    // Конструктор
    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Метод для получения пользователя по email
    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    // Метод для создания нового пользователя
    @Override
    public User createUser(String name, String email) {
        return userRepository.createUser(name, email);
    }
}
