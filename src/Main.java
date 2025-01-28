import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Main{
    public static void main(String[] args) {

        Room room1=new Room("famaly","43", 657);
        Room room2=new Room("famaly","41", 657);
        Room room3=new Room("famaly","46", 657);


        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите роль: 1. Админ 2. Гость 3. Выйти");
            int role = scanner.nextInt();
            scanner.nextLine();

            switch (role) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    guestMenu();
                    break;
                case 3:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void adminLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        System.out.println("Введите код:");
        String code = scanner.nextLine();

        if (name.equals("admin") && code.equals("1234")) {
            System.out.println("Авторизация успешна.");
            showAdminTables();
        } else {
            System.out.println("Неверные данные.");
        }
    }

    private static void showAdminTables() {
        System.out.println("Свободные номера:");


        for (Map.Entry<Integer, String> entry : HotelBookingSystem.rooms.entrySet()) {
            if (entry.getValue().equals("Свободен")) {
                System.out.println("Номер " + entry.getKey());
            }
        }

        System.out.println("Забронированные номера:");
        for (Map.Entry<Integer, String> entry : HotelBookingSystem.bookedRooms.entrySet()) {
            System.out.println("Номер " + entry.getKey() + " - " + entry.getValue());
        }
    }

    private static void guestMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите действие: 1. Просмотр брони 2. Забронировать");
        int action = scanner.nextInt();
        scanner.nextLine(); // Очистка ввода

        switch (action) {
            case 1:
                viewBooking();
                break;
            case 2:
                makeBooking();
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private static void viewBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер комнаты:");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        if (HotelBookingSystem.bookedRooms.containsKey(roomNumber)) {
            System.out.println("Бронь найдена: " + HotelBookingSystem.bookedRooms.get(roomNumber));
        } else {
            System.out.println("Бронь не найдена.");
        }
    }

    private static void makeBooking() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваше имя:");
        String name = scanner.nextLine();
        System.out.println("Введите дату (дд.мм.гггг):");
        String date = scanner.nextLine();

        System.out.println("Свободные номера:");
        for (Map.Entry<Integer, String> entry : HotelBookingSystem.rooms.entrySet()) {
            if (entry.getValue().equals("Свободен")) {
                System.out.println("Номер " + entry.getKey());
            }
        }

        System.out.println("Введите номер для бронирования:");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        if (HotelBookingSystem.rooms.containsKey(roomNumber) && HotelBookingSystem.rooms.get(roomNumber).equals("Свободен")) {
            HotelBookingSystem.rooms.put(roomNumber, "Занят");
            HotelBookingSystem.bookedRooms.put(roomNumber, "Имя: " + name + ", Дата: " + date);
            System.out.println("Номер успешно забронирован.");
        } else {
            System.out.println("Номер уже занят или не существует.");
        }
    }
}

