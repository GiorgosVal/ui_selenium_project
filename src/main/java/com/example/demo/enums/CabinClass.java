package com.example.demo.enums;

public enum CabinClass {

    ECONOMY("Economy"),
    PREMIUM("Premium"),
    BUSINESS("Business"),
    FIRST("First");

    private final String cabin;

    CabinClass(String cabin) {
        this.cabin = cabin;
    }

    public String getCabin() {
        return this.cabin;
    }
}
