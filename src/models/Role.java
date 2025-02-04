package models;

public enum Role {
    USER, MANAGER, ADMIN;

    public boolean equalsIgnoreCase(String user) {
        return false;
    }
}
