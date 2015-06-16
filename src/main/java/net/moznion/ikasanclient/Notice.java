package net.moznion.ikasanclient;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

@Setter
@Accessors(fluent = true)
public class Notice implements Message {
    private static final MessageType MESSAGE_TYPE = MessageType.NOTICE;

    private final IkasanClient ikasanClient;
    private final String channel;
    private final String message;

    private String nickname = "ikasan";
    private Color color = Color.YELLOW;
    private MessageFormat messageFormat = MessageFormat.TEXT;

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
