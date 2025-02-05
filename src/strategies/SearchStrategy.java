package strategies;

import models.User;
import repositories.interfaces.IUserRepository;

public interface SearchStrategy {
    User search(IUserRepository userRepository, String query);
}
