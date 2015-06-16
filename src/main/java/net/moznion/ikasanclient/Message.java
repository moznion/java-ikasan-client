package net.moznion.ikasanclient;

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

interface Message {
    HttpResponse send() throws IOException, URISyntaxException;

    default HttpResponse postMessage(IkasanClient ikasanClient, MessageType messageType, String channel, String message,
                                     String nickname, Color color, MessageFormat messageFormat)
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
