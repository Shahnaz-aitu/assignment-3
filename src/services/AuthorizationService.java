package services;

import models.Permission;
import models.User;

/**
 * Сервис для централизованной проверки прав доступа.
 */
public class AuthorizationService {

    /**
     * Проверяет, обладает ли пользователь требуемым разрешением.
     * Если нет, выбрасывает исключение AuthorizationException.
     *
     * @param user пользователь, для которого проводится проверка
     * @param requiredPermission требуемое разрешение
     * @throws AuthorizationException если пользователь не имеет необходимого разрешения
     */
    public static void checkPermission(User user, Permission requiredPermission) throws AuthorizationException {
        if (user == null) {
            throw new AuthorizationException("❌ Ошибка: Пользователь не найден.");
        }

        // Разрешаем пользователям (USER) бронировать номера
        if (user.getRole().equalsIgnoreCase("USER") && requiredPermission == Permission.CREATE_BOOKING) {
            return; // Позволяет пользователям создавать бронирования
        }

        // Проверка остальных прав пользователя
        if (!user.hasPermission(requiredPermission)) {
            throw new AuthorizationException("❌ Ошибка: Недостаточно прав для выполнения данной операции: " + requiredPermission);
        }
    }
}
