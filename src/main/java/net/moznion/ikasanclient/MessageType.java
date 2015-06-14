package net.moznion.ikasanclient;

enum MessageType {
    NOTICE("notice"),
    PRIVMSG("privmsg");

    private String value;

    MessageType(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}
