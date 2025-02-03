package repositories.interfaces;

import data.interfaces.IDB;
import models.User;

public interface IUserRepository {
    User getUserByEmail(String email);
    User createUser(String name, String email, int age, String password);
    User searchUser(String query);
    boolean deleteUser(String email);
    User getUserById(int id);

    IDB getDb();
}

