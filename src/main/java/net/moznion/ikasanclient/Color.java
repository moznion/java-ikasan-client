package net.moznion.ikasanclient;

public enum Color {
    YELLOW("yellow"),
    RED("red"),
    GREEN("green"),
    PURPLE("purple"),
    GRAY("gray"),
    RANDOM("random");

    private final String value;

    Color(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}
