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
        if (user == null || !user.hasPermission(requiredPermission)) {
            throw new AuthorizationException("Недостаточно прав для выполнения данной операции: " + requiredPermission);
        }
    }
}
