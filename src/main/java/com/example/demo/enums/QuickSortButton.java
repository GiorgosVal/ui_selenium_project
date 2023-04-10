package com.example.demo.enums;

public enum QuickSortButton {

    RECOMMENDED("Recommended"),
    PROMOTION("Promotion"),
    CHEAPEST("Cheapest"),
    SHORTEST("Shortest");

    private final String button;

    QuickSortButton(String button) {
        this.button = button;
    }

    public String getButton() {
        return this.button;
    }
}
