package net.moznion.ikasanclient.hipchat;

public enum HipChatColor {
    YELLOW("yellow"),
    RED("red"),
    GREEN("green"),
    PURPLE("purple"),
    GRAY("gray"),
    RANDOM("random");

    private final String value;

    HipChatColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
