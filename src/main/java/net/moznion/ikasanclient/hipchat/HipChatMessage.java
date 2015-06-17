package net.moznion.ikasanclient.hipchat;

import net.moznion.ikasanclient.IkasanClient;
import net.moznion.ikasanclient.Message;
import net.moznion.uribuildertiny.URIBuilderTiny;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public interface HipChatMessage extends Message {
    HipChatMessage nickname(String nickname);

    HipChatMessage color(HipChatColor color);

    HipChatMessage messageFormat(HipChatMessageFormat messageFormat);

    default HttpResponse postMessage(IkasanClient ikasanClient, MessageType messageType, String channel, String message,
                                     String nickname, HipChatColor color, HipChatMessageFormat messageFormat)
            throws URISyntaxException, IOException {
        URIBuilderTiny uriBuilder = new URIBuilderTiny()
                .setScheme("http")
                .setHost(ikasanClient.getHost())
                .setPort(ikasanClient.getPort())
                .setPaths(messageType.getValue());

        if (ikasanClient.isUseSSL()) {
            uriBuilder.setScheme("https");
        }

        HttpPost httpPost = new HttpPost(uriBuilder.build());

        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new BasicNameValuePair("channel", channel));
        requestParams.add(new BasicNameValuePair("message", message));
        requestParams.add(new BasicNameValuePair("nickname", nickname));
        requestParams.add(new BasicNameValuePair("color", color.getValue()));
        requestParams.add(new BasicNameValuePair("message_format", messageFormat.getValue()));

        httpPost.setEntity(new UrlEncodedFormEntity(requestParams, StandardCharsets.UTF_8));
        return ikasanClient.getHttpClient().execute(httpPost);
    }
}
