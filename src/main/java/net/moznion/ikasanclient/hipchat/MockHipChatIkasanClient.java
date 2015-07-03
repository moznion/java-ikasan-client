package net.moznion.ikasanclient.hipchat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import java.io.IOException;
import java.net.URISyntaxException;
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
        return new MockHipChatMessage();
    }

    @Override
    public HipChatMessage privmsg(String channel, String message) {
        privmsgs.add(new HipChatMessageUnit(channel, message));
        return new MockHipChatMessage();
    }

    @Getter
    @AllArgsConstructor
    public static class HipChatMessageUnit {
        private final String channel;
        private final String message;
    }

    private static class MockHipChatMessage implements HipChatMessage {
        @Override
        public HipChatMessage nickname(String nickname) {
            return this;
        }

        @Override
        public HipChatMessage color(HipChatColor color) {
            return this;
        }

        @Override
        public HipChatMessage messageFormat(HipChatMessageFormat messageFormat) {
            return this;
        }

        @Override
        public HttpResponse send() throws IOException, URISyntaxException {
            return new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("http", 1, 0), 200, ""));
        }
    }
}
