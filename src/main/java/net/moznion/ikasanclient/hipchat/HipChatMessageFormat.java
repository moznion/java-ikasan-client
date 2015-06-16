package net.moznion.ikasanclient.hipchat;

public enum HipChatMessageFormat {
    TEXT("text"),
    HTML("html");

    private final String value;

    HipChatMessageFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
