package repositories.interfaces;

import models.User;

public interface IUserRepository {
    User getUserByEmail(String email);
    User createUser(String name, String email, int age, String password);
    User searchUser(String query);
    boolean deleteUser(String email);
}
