import java.util.Scanner;

public class HotelBookingApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IHotelController hotelController;
    private final IRoomController roomController;
    private final IBookingController bookingController;

    public HotelBookingApplication(IHotelController hotelController, IRoomController roomController, IBookingController bookingController) {
        this.hotelController = hotelController;
        this.roomController = roomController;
        this.bookingController = bookingController;
    }

    public void mainMenu() {
        while (true) {
            System.out.println("\n=== Hotel Booking System ===");
            System.out.println("1. Управление отелями");
            System.out.println("2. Управление номерами");
            System.out.println("3. Управление бронированиями");
            System.out.println("4. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> manageHotels();
                case 2 -> manageRooms();
                case 3 -> manageBookings();
                case 4 -> {
                    System.out.println("Выход из системы.");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void manageHotels() {
        System.out.println("Функция управления отелями.");
    }

    private void manageRooms() {
        System.out.println("Функция управления номерами.");
    }

    private void manageBookings() {
        System.out.println("Функция управления бронированиями.");
    }
}
