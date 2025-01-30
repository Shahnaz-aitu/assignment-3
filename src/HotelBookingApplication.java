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
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> manageHotels();
                case 4 -> manageRooms();
                case 3 -> manageBookings();
                case 2 -> manageUsers();
                case 5 -> {
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
        scanner.nextLine(); // Очистка буфера222211

        if (age < 18) {
            System.out.println("Ошибка: возраст должен быть больше 18 лет.");
            return;
        }

        System.out.print("Введите ваш пароль: ");
        String password = scanner.nextLine();

        // Создаем пользователя в базе данных
        User newUser = userController.createUser(userName, email, age, password);
        if (newUser != null) {
            System.out.println(userName + ", вы успешно зарегистрировались!");
        } else {
            System.out.println("Ошибка при регистрации. Попробуйте снова.");
        }
    }

    private void manageHotels() {
        System.out.println("hotel: Hilton; City: Astana; Rank: 4.8");
    }

    private void manageRooms() {
        System.out.println("Управление номерами...");
    }

    private void manageBookings() {
        System.out.println("Управление бронированиями...");
    }
}