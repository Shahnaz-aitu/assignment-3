package controllers.interfaces;

import models.User;

public interface IUserController {
    User getUserByEmail(String email);
    User createUser(String name, String email, int age, String password);
    User searchUser(String query);
    boolean deleteUser(String email); // Добавляем метод
}
