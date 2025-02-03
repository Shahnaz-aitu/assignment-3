public class Main {
    public static void main(String[] args) {
        System.out.println("Запуск Hotel Booking System...");
        try {
            // Получаем приложение из Dependency Injector
            HotelBookingApplication app = DependencyInjector.createApplication();

            // Запуск главного меню
            app.mainMenu();

            System.out.println("Приложение завершено.");
        } catch (Exception e) {
            System.err.println("Ошибка во время работы приложения: " + e.getMessage());
            e.printStackTrace();
        }
    }
}