package net.moznion.ikasanclient.mock;

import net.moznion.ikasanclient.IkasanClient;
import net.moznion.ikasanclient.Message;

public class MockIkasanClient implements IkasanClient {
    @Override
    public Message notice(String channel, String message) {
        // NOP
        return null;
    }

    @Override
    public Message privmsg(String channel, String message) {
        // NOP
        return null;
    }
}
