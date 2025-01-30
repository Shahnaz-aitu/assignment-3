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
                case 6 -> deleteUser(); // Добавили этот пункт
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

        if (age < 18) {
            System.out.println("Ошибка: возраст должен быть больше 18 лет.");
            return;
        }

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
    private void deleteUser() {
        System.out.print("Введите email пользователя для удаления: ");
        String email = scanner.nextLine();

        boolean success = userController.deleteUser(email);
        if (success) {
            System.out.println("Пользователь успешно удален.");
        } else {
            System.out.println("Ошибка: пользователь не найден или не удалось удалить.");
        }
    }


    private void manageBookings() {
        System.out.println("Управление бронированиями...");
        List<Order> orders = getOrders();
        orders.forEach(order -> System.out.println(order.getOrderId()));
    }
}

public class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        // Приватный конструктор
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}

public class UserFactory {
    public static User createUser(String role) {
        switch (role.toLowerCase()) {
            case "manager":
                return new Manager();
            case "admin":
                return new Admin();
            default:
                return new Guest();
        }
    }
}

public class RoleManager {
    public boolean hasAccess(User user, String endpoint) {
        // Пример логики проверки доступа
        return user.getRole().hasPermission(endpoint);
    }
}

public class DataValidator {
    public static boolean isValidOrder(Order order) {
        // Логика проверки данных
        return order != null && order.getOrderDetails() != null;
    }
}

public class Product {
    private String name;
    private Category category;

    // Конструктор и геттеры/сеттеры
}

public enum Category {
    ELECTRONICS, FURNITURE, CLOTHING
}

public class Order {
    private int orderId;
    private OrderDetails orderDetails;
    private Customer customer;

    // Конструктор и геттеры/сеттеры

    public static Order getFullOrderDescription(int orderId) {
        // Пример получения данных из базы данных
        OrderDetails details = Database.getOrderDetails(orderId);
        Customer customer = Database.getCustomerByOrderId(orderId);
        return new Order(orderId, details, customer);
    }
}
