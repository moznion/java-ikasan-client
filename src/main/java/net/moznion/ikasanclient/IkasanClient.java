package net.moznion.ikasanclient;

public interface IkasanClient {
    Message notice(String channel, String message);

    Message privmsg(String channel, String message);
}
