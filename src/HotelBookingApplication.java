import controllers.interfaces.IBookingController;
import controllers.interfaces.IHotelController;
import controllers.interfaces.IRoomController;
import controllers.interfaces.IUserController;
import models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
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
            System.out.println("5. Проверить доступность номера");
            System.out.println("7 for find hotel by city");
            System.out.println("6. Выход");

            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            switch (choice) {
                case 1 -> manageHotels();
                case 4 -> manageRooms();
                case 3 -> manageBookings();
                case 2 -> manageUsers();
                case 5 -> checkRoomAvailability(scanner, dateFormat); // Функция для доступности номера
                case 6 -> {
                    System.out.println("Выход...");
                    return;
                }
              case 7 -> searchHotelsByCity();

                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }

        }
    }


    private void searchHotelsByCity() {
        System.out.print("Введите название города для поиска отелей: ");
        String city = scanner.nextLine();

        List<Hotel> hotels = hotelController.getHotelsByCity(city); // Вызов функции из контроллера

        if (hotels.isEmpty()) {
            System.out.println("Отели не найдены в указанном городе.");
        } else {
            System.out.println("Найденные отели:");
            for (Hotel hotel : hotels) {
                System.out.println("ID: " + hotel.getId() + ", Название: " + hotel.getName() +
                        ", Адрес: " + hotel.getAddress() + ", Рейтинг: " + hotel.getRating());
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
    private void checkRoomAvailability(Scanner scanner, SimpleDateFormat dateFormat) {
        try {
            System.out.println("Введите ID номера:");
            int roomId = scanner.nextInt();

            System.out.println("Введите дату начала (в формате yyyy-MM-dd):");
            String startDateInput = scanner.next();
            Date startDate = dateFormat.parse(startDateInput);

            System.out.println("Введите дату окончания (в формате yyyy-MM-dd):");
            String endDateInput = scanner.next();
            Date endDate = dateFormat.parse(endDateInput);

            roomController.checkRoomAvailability(roomId, startDate, endDate);
        } catch (Exception e) {
            System.err.println("Ошибка в процессе проверки: " + e.getMessage());
        }
    }

}