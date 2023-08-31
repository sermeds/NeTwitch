package com.example.netwitch.models.enums;

import java.util.Arrays;

public enum Category {
    MUSIC("Музыка"),
    SPORT("Спорт"),
    CHAT("Just Chatting");

    private String value;

    public String getValue() {
        return value;
    }

    Category(String value) {
        this.value = value;
    }

    public static boolean contains(String value) {
        return Arrays.stream(values()).anyMatch(e -> e.name().equals(value.toUpperCase()));
    }
}
