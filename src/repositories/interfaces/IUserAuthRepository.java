package repositories.interfaces;

import models.User;

public interface IUserAuthRepository {
    User authenticate(String email, String password);
}
