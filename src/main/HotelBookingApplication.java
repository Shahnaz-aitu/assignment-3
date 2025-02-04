package main;

import controllers.interfaces.IBookingController;
import controllers.interfaces.IHotelController;
import controllers.interfaces.IRoomController;
import controllers.interfaces.IUserController;
import models.User;

import java.util.Scanner;

public class HotelBookingApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IHotelController hotelController;
    private final IRoomController roomController;
    private final IBookingController bookingController;
    private final IUserController userController;
    private User currentUser = null;

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
            System.out.println("3. Войти в систему");
            System.out.println("4. Управление бронированиями");
            System.out.println("5. Забронировать");
            System.out.println("6. Поиск пользователя");
            System.out.println("7. Удаление пользователя");
            System.out.println("8. Выход");
            System.out.println("9. Просмотр бронирования"); // ✅ Добавлен новый пункт
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> manageHotels();
                case 2 -> registerUser();
                case 3 -> loginUser();
                case 4 -> manageBookings();
                case 5 -> bookRoom();
                case 6 -> searchUser();
                case 7 -> deleteUser();
                case 8 -> {
                    System.out.println("Выход...");
                    return;
                }
                case 9 -> viewBooking(); // ✅ Вызов нового метода
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private void registerUser() {
        System.out.print("Введите ваше имя: ");
        String userName = scanner.nextLine();
        System.out.print("Введите ваш email: ");
        String email = scanner.nextLine();
        System.out.print("Введите ваш возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите ваш пароль: ");
        String password = scanner.nextLine();

        User newUser = userController.createUser(userName, email, age, password);
        if (newUser != null) {
            System.out.println(userName + ", вы успешно зарегистрировались!");
            currentUser = newUser;
        } else {
            System.out.println("Ошибка при регистрации. Попробуйте снова.");
        }
    }

    private void loginUser() {
        System.out.print("Введите ваш email: ");
        String email = scanner.nextLine();
        System.out.print("Введите ваш пароль: ");
        String password = scanner.nextLine();

        User user = userController.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("✅ Добро пожаловать, " + user.getName() + "!");
            currentUser = user;
        } else {
            System.out.println("❌ Ошибка входа. Проверьте email и пароль.");
        }
    }

    private void manageHotels() {
        System.out.println("hotel: Hilton; City: Astana; Rank: 4.8");
    }

    private void manageBookings() {
        if (currentUser == null) {
            System.out.println("❌ Вам нужно войти в систему.");
            return;
        }
        System.out.println("Ваши бронирования:");
        bookingController.showUserBookings(currentUser.getEmail());
    }

    private void bookRoom() {
        if (currentUser == null) {
            System.out.println("❌ Вам нужно войти в систему.");
            return;
        }

        System.out.print("Введите ID отеля: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine();

        roomController.getRoomsByHotelId(hotelId, currentUser);

        System.out.print("Введите ID номера для бронирования: ");
        int roomId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введите дату заезда (YYYY-MM-DD): ");
        String checkInDate = scanner.nextLine();
        System.out.print("Введите дату выезда (YYYY-MM-DD): ");
        String checkOutDate = scanner.nextLine();

        boolean success = bookingController.createBooking(
                currentUser.getEmail(), roomId,
                java.sql.Date.valueOf(checkInDate),
                java.sql.Date.valueOf(checkOutDate));

        if (success) {
            System.out.println("✅ Бронирование успешно!");
        } else {
            System.out.println("❌ Не удалось выполнить бронирование.");
        }
    }

    private void searchUser() {
        System.out.print("Введите имя или email для поиска: ");
        String query = scanner.nextLine();
        User foundUser = userController.searchUser(query);
        if (foundUser != null) {
            System.out.println("Найден пользователь: " + foundUser.getName());
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    private void deleteUser() {
        if (currentUser == null || !currentUser.getRole().equals("ADMIN")) {
            System.out.println("❌ Только администратор может удалять пользователей.");
            return;
        }

        System.out.print("Введите email пользователя для удаления: ");
        String email = scanner.nextLine();

        boolean success = userController.deleteUser(email, currentUser);
        if (success) {
            System.out.println("✅ Пользователь успешно удален.");
        } else {
            System.out.println("❌ Ошибка: пользователь не найден или не удалось удалить.");
        }
    }

    private void viewBooking() {
        if (currentUser == null) {
            System.out.println("❌ Вам нужно войти в систему.");
            return;
        }

        System.out.print("\nВведите ID бронирования: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n=== ✅ Проверка полной информации о бронировании ===");
        bookingController.showFullBookingDescription(bookingId);
    }
}
