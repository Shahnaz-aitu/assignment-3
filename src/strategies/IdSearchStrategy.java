package strategies;

import models.User;
import repositories.interfaces.IUserRepository;

public class IdSearchStrategy implements SearchStrategy {
    @Override
    public User search(IUserRepository userRepository, String query) {
        try {
            int id = Integer.parseInt(query);
            return userRepository.getUserById(id);
        } catch (NumberFormatException e) {
            System.out.println("❌ Ошибка: Введите корректный числовой ID.");
            return null;
        }
    }
}
