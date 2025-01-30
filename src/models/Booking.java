package models;

import java.util.Date;

public class Booking {
    private int userId;
    private int roomId;
    private Date checkIn;
    private Date checkOut;

    public Booking(int userId, int roomId, Date checkIn, Date checkOut) {
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public int getUserId() { return userId; }
    public int getRoomId() { return roomId; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
}
