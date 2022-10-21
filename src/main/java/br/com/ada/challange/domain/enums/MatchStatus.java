package br.com.ada.challange.domain.enums;

public enum MatchStatus {

    OPEN ("Open"),
    CLOSED ("Close");

    private String description;

    MatchStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

}
