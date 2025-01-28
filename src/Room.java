public class Room {
    private String roomType;
    private String roomNumber;
    private int price;

    public Room( String roomType, String roomNumber, int price) {
        setOrder(roomNumber, roomType , price);
        System.out.println(getRoomNumber());
    }

    public void setOrder(String roomNumber, String roomType , int price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
    }
    public String isFree(boolean isFree) {
        if (isFree) {
            return "Free Room";
        }
        else
            return "Not Free Room";
    }
    public String getRoomNumber() {
       String Number = "room:" + this.roomNumber +" type"+ this.roomType + " price"+this.price;
        return Number;
    }

}
