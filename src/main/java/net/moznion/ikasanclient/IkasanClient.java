package net.moznion.ikasanclient;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class IkasanClient {
    private static final String USER_AGENT = "Java-Ikasan-Client (Java, version: "
            + Package.getPackage("net.moznion.ikasanclient").getImplementationVersion() + ")";
    private static final List<Header> defaultHeaders;

    static {
        defaultHeaders = new ArrayList<>();
        defaultHeaders.add(new BasicHeader("Accept-Charset", "utf-8"));
        defaultHeaders.add(new BasicHeader("User-Agent", USER_AGENT));
    }

    private final String host;
    private final HttpClient httpClient;
    private final int port;
    private final boolean useSSL;

    @Setter
    @Accessors(fluent = true)
    public static class IkasanClientBuilder {
        private final String host;
        private int port = 4979;
        private boolean useSSL = false;

        public IkasanClientBuilder(String host) {
            this.host = host;
        }

        public IkasanClient build() {
            return new IkasanClient(this);
        }
    }

    public static IkasanClientBuilder ikasanClientBuilder(String host) {
        return new IkasanClientBuilder(host);
    }

    private IkasanClient(IkasanClientBuilder b) {
        this.host = b.host;
        this.port = b.port;
        this.useSSL = b.useSSL;

        this.httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.DEFAULT)
                .setDefaultHeaders(defaultHeaders)
                .build();
    }

    public HttpResponse notice(String channel, String message) throws IOException, URISyntaxException {
        return notice(channel, message, Color.YELLOW, MessageFormat.TEXT);
    }

    public HttpResponse notice(String channel, String message, Color color) throws IOException, URISyntaxException {
        return notice(channel, message, color, MessageFormat.TEXT);
    }

    public HttpResponse notice(String channel, String message, MessageFormat messageFormat) throws IOException, URISyntaxException {
        return notice(channel, message, Color.YELLOW, messageFormat);
    }

    public HttpResponse notice(String channel, String message, Color color, MessageFormat messageFormat)
            throws IOException, URISyntaxException {
        return postMessage(MessageType.NOTICE, channel, message, color, messageFormat);
    }

    public HttpResponse privmsg(String channel, String message) throws IOException, URISyntaxException {
        return privmsg(channel, message, Color.YELLOW, MessageFormat.TEXT);
    }

    public HttpResponse privmsg(String channel, String message, Color color) throws IOException, URISyntaxException {
        return privmsg(channel, message, color, MessageFormat.TEXT);
    }

    public HttpResponse privmsg(String channel, String message, MessageFormat messageFormat) throws IOException, URISyntaxException {
        return privmsg(channel, message, Color.YELLOW, messageFormat);
    }

    public HttpResponse privmsg(String channel, String message, Color color, MessageFormat messageFormat)
            throws IOException, URISyntaxException {
        return postMessage(MessageType.PRIVMSG, channel, message, color, messageFormat);
    }

    private HttpResponse postMessage(MessageType messageType, String channel, String message, Color color, MessageFormat messageFormat)
            throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme("http")
                .setHost(host)
                .setPort(port)
                .setPath(messageType.getValue());

        if (useSSL) {
            uriBuilder.setScheme("https");
        }

        HttpPost httpPost = new HttpPost(uriBuilder.build());

        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new BasicNameValuePair("channel", channel));
        requestParams.add(new BasicNameValuePair("message", message));
        requestParams.add(new BasicNameValuePair("color", color.getValue()));
        requestParams.add(new BasicNameValuePair("message_format", messageFormat.getValue()));

        httpPost.setEntity(new UrlEncodedFormEntity(requestParams));
        return httpClient.execute(httpPost);
    }
}
