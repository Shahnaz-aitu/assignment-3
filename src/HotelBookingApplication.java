import controllers.interfaces.IHotelController;
import controllers.interfaces.IRoomController;
import controllers.interfaces.IBookingController;
import controllers.interfaces.IUserController;
import java.util.Scanner;

public class HotelBookingApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IHotelController hotelController;
    private final IRoomController roomController;
    private final IBookingController bookingController;
    private final IUserController userController; // ✅ Добавлен UserController

    public HotelBookingApplication(
            IHotelController hotelController,
            IRoomController roomController,
            IBookingController bookingController,
            IUserController userController) { // ✅ Теперь принимает UserController

        this.hotelController = hotelController;
        this.roomController = roomController;
        this.bookingController = bookingController;
        this.userController = userController; // ✅ Сохраняем в поле класса
    }

    public void mainMenu() {
        while (true) {
            System.out.println("\n=== Hotel Booking System ===");
            System.out.println("1. Управление отелями");
            System.out.println("2. Управление номерами");
            System.out.println("3. Управление бронированиями");
            System.out.println("4. Управление пользователями"); // ✅ Добавлено управление пользователями
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> manageHotels();
                case 2 -> manageRooms();
                case 3 -> manageBookings();
                case 4 -> manageUsers(); // ✅ Добавлена работа с пользователями
                case 5 -> {
                    System.out.println("Выход...");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private void manageUsers() {
        System.out.println("Функции управления пользователями еще не реализованы."); // 🚀 Можешь добавить методы
    }

    private void manageHotels() {
        System.out.println("Управление отелями...");
    }

    private void manageRooms() {
        System.out.println("Управление номерами...");
    }

    private void manageBookings() {
        System.out.println("Управление бронированиями...");
    }
}
