package models;

import models.interfaces.IBookable;
import java.util.Date;

public class Booking extends AbstractEntity implements IBookable {
    private int userId;
    private int roomId;
    private Date checkIn;
    private Date checkOut;

    // Конструктор с id (например, при получении из БД)
    public Booking(int id, int userId, int roomId, Date checkIn, Date checkOut) {
        super(id);  // Наследуем id от AbstractEntity
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // Конструктор без id (при создании нового бронирования)
    public Booking(int userId, int roomId, Date checkIn, Date checkOut) {
        super(0); // id будет присвоен БД
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public boolean isAvailable(Date checkIn, Date checkOut) {
        return !(this.checkIn.before(checkOut) && this.checkOut.after(checkIn));
    }

    public int getUserId() { return userId; }
    public int getRoomId() { return roomId; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }
}
