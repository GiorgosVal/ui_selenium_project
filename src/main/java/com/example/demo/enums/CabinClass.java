package com.example.demo.enums;

public enum CabinClass {

    ECONOMY("Economy"),
    PREMIUM("Premium"),
    BUSINESS("Business"),
    FIRST("First");

    private final String cabinClass;

    CabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public String getCabinClass() {
        return this.cabinClass;
    }
}
