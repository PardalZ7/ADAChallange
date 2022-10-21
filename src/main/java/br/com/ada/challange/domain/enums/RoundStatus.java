package br.com.ada.challange.domain.enums;

public enum RoundStatus {

    OPEN ("Open"),
    CLOSED ("Close");

    private String description;

    RoundStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
