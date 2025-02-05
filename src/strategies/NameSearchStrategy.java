package strategies;

import models.User;
import repositories.interfaces.IUserRepository;

public class NameSearchStrategy implements SearchStrategy {
    @Override
    public User search(IUserRepository userRepository, String query) {
        return userRepository.searchUser(query);
    }
}
