package models;

import java.util.Date;

public class Booking {
    private int id;    // добавлено поле id
    private int userId;
    private int roomId;
    private Date checkIn;
    private Date checkOut;

    // Конструктор с id (например, при получении из БД)
    public Booking(int id, int userId, int roomId, Date checkIn, Date checkOut) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // Конструктор без id (при создании нового бронирования)
    public Booking(int userId, int roomId, Date checkIn, Date checkOut) {
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public int getRoomId() {
        return roomId;
    }
    public Date getCheckIn() {
        return checkIn;
    }
    public Date getCheckOut() {
        return checkOut;
    }
}
