package net.moznion.ikasanclient.hipchat;

enum HipChatMessageType {
    NOTICE("notice"),
    PRIVMSG("privmsg");

    private String value;

    HipChatMessageType(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}
