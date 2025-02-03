public class Main {
    public static void main(String[] args) {
        Database db = Database.getInstance(); // Singleton Database
        HotelRepository hotelRepository = new HotelRepository(db);
        IHotelController hotelController = new HotelController(hotelRepository);

        // Пример вызова
        List<Hotel> hotels = hotelController.getHotelsByCity("New York");
        hotels.forEach(hotel -> System.out.println("ID: " + hotel.getId() + ", Name: " + hotel.getName()));
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