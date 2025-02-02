package repositories.interfaces;

import models.User;

public interface IUserProfileRepository {
    User getUserProfile(int userId);
}
