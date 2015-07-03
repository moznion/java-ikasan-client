package net.moznion.ikasanclient.hipchat;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MockHipChatIkasanClient implements HipChatIkasanClient {
    private final List<HipChatMessageUnit> notices;
    private final List<HipChatMessageUnit> privmsgs;

    public MockHipChatIkasanClient() {
        notices = new ArrayList<>();
        privmsgs = new ArrayList<>();
    }

    @Override
    public HipChatMessage notice(String channel, String message) {
        notices.add(new HipChatMessageUnit(channel, message));
        return null;
    }

    @Override
    public HipChatMessage privmsg(String channel, String message) {
        privmsgs.add(new HipChatMessageUnit(channel, message));
        return null;
    }

    @Getter
    @AllArgsConstructor
    public static class HipChatMessageUnit {
        private final String channel;
        private final String message;
    }
}
