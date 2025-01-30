package repositories.interfaces; // ✅ Должен быть этот пакет

import java.util.Date;

public interface IRoomRepository {
    boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut);
}
