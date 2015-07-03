package net.moznion.ikasanclient.hipchat;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@Setter
@Accessors(fluent = true)
class HipChatNotice implements HipChatMessage {
    private static final HipChatMessageType MESSAGE_TYPE = HipChatMessageType.NOTICE;

    private final BasicHipChatIkasanClient basicHipChatIkasanClient;
    private final String channel;
    private final String message;

    private String nickname = "ikasan";
    private HipChatColor color = HipChatColor.YELLOW;
    private HipChatMessageFormat messageFormat = HipChatMessageFormat.TEXT;

    public HipChatNotice(BasicHipChatIkasanClient basicHipChatIkasanClient, String channel, String message) {
        this.basicHipChatIkasanClient = basicHipChatIkasanClient;
        this.channel = channel;
        this.message = message;
    }

    @Override
    public HttpResponse send() throws IOException, URISyntaxException {
        return postMessage(basicHipChatIkasanClient, MESSAGE_TYPE, channel, message, nickname, color, messageFormat);
    }
}
