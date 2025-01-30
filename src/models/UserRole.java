package models;

import java.util.Set;
import java.util.HashSet;

public enum UserRole {
    GUEST(Set.of("VIEW_ROOMS", "CREATE_BOOKING")),
    MANAGER(Set.of("VIEW_ROOMS", "CREATE_BOOKING", "MANAGE_BOOKINGS", "VIEW_USERS")),
    ADMIN(Set.of("VIEW_ROOMS", "CREATE_BOOKING", "MANAGE_BOOKINGS", "VIEW_USERS", "MANAGE_USERS", "MANAGE_ROOMS"));

    private final Set<String> permissions;

    UserRole(Set<String> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermission(String action) {
        return permissions.contains(action);
    }

    public Set<String> getPermissions() {
        return new HashSet<>(permissions);
    }
} 