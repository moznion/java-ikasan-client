package net.moznion.ikasanclient;

import lombok.Getter;

public enum MessageFormat {
    TEXT("text"),
    HTML("html");

    private final String value;

    MessageFormat(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}
