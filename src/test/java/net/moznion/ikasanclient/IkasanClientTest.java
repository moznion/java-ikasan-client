package net.moznion.ikasanclient;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class IkasanClientTest {
    @Test
    public void shouldSendMessage() throws IOException, URISyntaxException {
        IkasanClient.ikasanClientBuilder("localhost")
                .port(9292)
                .build()
                .notice("#hoge", "fuga");
    }
}

