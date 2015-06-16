package net.moznion.ikasanclient.hipchat;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.moznion.ikasanclient.IkasanClient;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@Setter
@Accessors(fluent = true)
public class Notice implements HipChatMessage {
    private static final MessageType MESSAGE_TYPE = MessageType.NOTICE;

    private final IkasanClient ikasanClient;
    private final String channel;
    private final String message;

    private String nickname = "ikasan";
    private HipChatColor color = HipChatColor.YELLOW;
    private HipChatMessageFormat messageFormat = HipChatMessageFormat.TEXT;

    public Notice(IkasanClient ikasanClient, String channel, String message) {
        this.ikasanClient = ikasanClient;
        this.channel = channel;
        this.message = message;
    }

    @Override
    public HttpResponse send() throws IOException, URISyntaxException {
        return postMessage(ikasanClient, MESSAGE_TYPE, channel, message, nickname, color, messageFormat);
    }
}
