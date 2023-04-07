package com.example.demo.enums;

public enum PassengerType {
    ADULTS("adults"),
    CHILDREN("children"),
    INFANTS("infants");

    private final String type;

    PassengerType(String passengerType) {
        this.type = passengerType;
    }

    public String get() {
        return type;
    }
}
