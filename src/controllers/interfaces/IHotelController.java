package controllers.interfaces;

import models.Hotel;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface IHotelController {
    List<Hotel> getHotelsByCity(String city);
}

// Добавить специальные исключения
public class BookingException extends RuntimeException {
    public BookingException(String message) {
        super(message);
    }
}

// Добавить логирование операций
private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
