package controllers.interfaces;

import models.User;

public interface IUserController {
    User getUserByEmail(String email, User currentUser); // Теперь метод принимает текущего пользователя для проверки роли
    User createUser(String name, String email, int age, String password);
    User searchUser(String query);
    boolean deleteUser(String email, User currentUser);
}
