package models.interfaces;

import java.util.Date;

public interface IBookable {
    boolean isAvailable(Date checkIn, Date checkOut);
}
