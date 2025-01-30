package repositories.interfaces;

import models.User;

public interface IUserRepository {
    User getUserByEmail(String email);
    User createUser(String name, String email, int age, String password);
    User searchUser(String query);
    boolean deleteUser(String email);
    User getUserById(int id);
}

public interface IUserAuthRepository {
    User authenticate(String email, String password);
}

public interface IUserProfileRepository {
    User getUserProfile(int userId);
}
