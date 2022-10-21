package br.com.ada.challange.domain.enums;

public enum UserRole {

    ADMIN ("ADMIN"),
    USER ("USER");

    private String description;

    UserRole(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
