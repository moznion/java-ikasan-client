package net.moznion.ikasanclient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.moznion.ikasanclient.hipchat.HipChatMessage;
import net.moznion.ikasanclient.hipchat.Notice;
import net.moznion.ikasanclient.hipchat.Privmsg;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;

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

    @Getter
    private final String host;
    @Getter
    private final int port;
    @Getter
    private final boolean useSSL;
    @Getter
    private final String messagePrefix;

    private final HttpClientBuilder httpClientBuilder;
    private final Registry<ConnectionSocketFactory> registry;

    @Setter
    @Accessors(fluent = true)
    public static class IkasanClientBuilder {
        private final String host;
        private int port = 4979;
        private boolean useSSL = false;
        private boolean verifySSL = true;
        private String messagePrefix = "";

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
        this.messagePrefix = b.messagePrefix;

        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE);

        if (this.useSSL) {
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            if (!b.verifySSL) {
                sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            }
            registryBuilder.register("https", new SSLConnectionSocketFactory(sslContextBuilder.build()));
        }

        this.registry = registryBuilder.build();

        this.httpClientBuilder = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.DEFAULT)
                .setDefaultHeaders(defaultHeaders);
    }

    public HipChatMessage notice(String channel, String message) {
        return new Notice(this, channel, messagePrefix + message);
    }

    public HipChatMessage privmsg(String channel, String message) {
        return new Privmsg(this, channel, messagePrefix + message);
    }

    public CloseableHttpClient getHttpClient() {
        return this.httpClientBuilder
                .setConnectionManager(new BasicHttpClientConnectionManager(registry))
                .build();
    }
}
