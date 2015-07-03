package net.moznion.ikasanclient.hipchat;

public interface HipChatIkasanClient {
    HipChatMessage notice(String channel, String message);

    HipChatMessage privmsg(String channel, String message);
}
