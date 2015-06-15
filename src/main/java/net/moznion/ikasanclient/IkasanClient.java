package net.moznion.ikasanclient;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.moznion.uribuildertiny.URIBuilderTiny;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
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
        private boolean verifySSL = false;

        public IkasanClientBuilder(String host) {
            this.host = host;
        }

        public IkasanClient build() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
            return new IkasanClient(this);
        }
    }

    public static IkasanClientBuilder ikasanClientBuilder(String host) {
        return new IkasanClientBuilder(host);
    }

    private IkasanClient(IkasanClientBuilder b) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        this.host = b.host;
        this.port = b.port;
        this.useSSL = b.useSSL;

        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE);

        if (this.useSSL) {
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            if (!b.verifySSL) {
                sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            }
            registryBuilder.register("https", new SSLConnectionSocketFactory(sslContextBuilder.build()));
        }

        HttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(registryBuilder.build());

        this.httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.DEFAULT)
                .setConnectionManager(connectionManager)
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
        URIBuilderTiny uriBuilder = new URIBuilderTiny()
                .setScheme("http")
                .setHost(host)
                .setPort(port)
                .setPaths(messageType.getValue());

        if (useSSL) {
            uriBuilder.setScheme("https");
        }

        HttpPost httpPost = new HttpPost(uriBuilder.build());

        List<NameValuePair> requestParams = new ArrayList<>();
        requestParams.add(new BasicNameValuePair("channel", channel));
        requestParams.add(new BasicNameValuePair("message", message));
        requestParams.add(new BasicNameValuePair("color", color.getValue()));
        requestParams.add(new BasicNameValuePair("message_format", messageFormat.getValue()));

        httpPost.setEntity(new UrlEncodedFormEntity(requestParams, StandardCharsets.UTF_8));
        return httpClient.execute(httpPost);
    }
}
