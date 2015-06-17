package net.moznion.ikasanclient;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Message {
    HttpResponse send() throws IOException, URISyntaxException;
}
