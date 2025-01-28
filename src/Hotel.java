public class Hotel {
    private int date;
    private long phoneNumber;
    private String customerName;

    public void setValue(int date, long phoneNumber, String customerName) {
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
    }
    public String getValue() {
        String info= "The date is: " + this.date + " and the id is: " + this.phoneNumber + " and the customer name is: " + this.customerName;
        return info;
    }
    public Hotel(int date, long phoneNumber, String customerName) {
        setValue(date,phoneNumber,customerName);
        System.out.println(getValue());
    }
}
