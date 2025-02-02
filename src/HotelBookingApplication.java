import controllers.interfaces.IBookingController;
import controllers.interfaces.IHotelController;
import controllers.interfaces.IRoomController;
import controllers.interfaces.IUserController;
import models.User;

import java.util.Scanner;
import java.util.List;

public class HotelBookingApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IHotelController hotelController;
    private final IRoomController roomController;
    private final IBookingController bookingController;
    private final IUserController userController;

    public HotelBookingApplication(
            IHotelController hotelController,
            IRoomController roomController,
            IBookingController bookingController,
            IUserController userController) {
        this.hotelController = hotelController;
        this.roomController = roomController;
        this.bookingController = bookingController;
        this.userController = userController;
    }

    public void mainMenu() {
        while (true) {
            System.out.println("\n=== Hotel Booking System ===");
            System.out.println("1. Информация об отеле");
            System.out.println("2. Регистрация");
            System.out.println("3. Управление бронированиями");
            System.out.println("4. Забронировать");
            System.out.println("5. Поиск пользователя");
            System.out.println("6. Удаление пользователя");
            System.out.println("7. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> manageHotels();
                case 2 -> manageUsers();
                case 3 -> manageBookings();
                case 4 -> manageRooms();
                case 5 -> searchUser();
                case 6 -> deleteUser();
                case 7 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private void manageUsers() {
        System.out.print("Введите ваше имя: ");
        String userName = scanner.nextLine();
        System.out.print("Введите ваш email: ");
        String email = scanner.nextLine();
        System.out.print("Введите ваш возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера

        System.out.print("Введите ваш пароль: ");
        String password = scanner.nextLine();

        User newUser = userController.createUser(userName, email, age, password);
        if (newUser != null) {
            System.out.println(userName + ", вы успешно зарегистрировались!");
        } else {
            System.out.println("Ошибка при регистрации. Попробуйте снова.");
        }
    }

    private void searchUser() {
        System.out.print("Введите имя или email для поиска: ");
        String query = scanner.nextLine();
        User foundUser = userController.searchUser(query);
        if (foundUser != null) {
            System.out.println("Найден пользователь:");
            System.out.println("Имя: " + foundUser.getName());
            System.out.println("Email: " + foundUser.getEmail());
            System.out.println("Возраст: " + foundUser.getAge());
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    private void manageHotels() {
        System.out.println("hotel: Hilton; City: Astana; Rank: 4.8");
    }

    private void manageRooms() {
        System.out.println("Управление номерами...");
    }

    // Здесь для примера используется заглушка текущего пользователя (admin)
    private void deleteUser() {
        System.out.print("Введите email пользователя для удаления: ");
        String email = scanner.nextLine();

        // Заглушка: получаем пользователя с правами админа
        User currentUser = userController.getUserByEmail("admin@domain.com");
        boolean success = userController.deleteUser(email, currentUser);
        if (success) {
            System.out.println("Пользователь успешно удален.");
        } else {
            System.out.println("Ошибка: пользователь не найден или не удалось удалить.");
        }
    }

    private void manageBookings() {
        System.out.println("Управление бронированиями...");
        List<Order> orders = getOrders(); // Предполагается, что метод getOrders() существует
        orders.forEach(order -> System.out.println(order.getOrderId()));
    }

    // Заглушка для получения списка заказов (Order)
    private List<Order> getOrders() {
        return List.of();
    }
}
