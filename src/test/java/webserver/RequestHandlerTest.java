package webserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parser.HTTPRequestParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

class RequestHandlerTest {

    @Test
    @DisplayName("파서가 잘 동작하는지 Test")
    void parseRequestHeadersTest() throws IOException {
        String line = "GET /index.html HTTP/1.1 Host: localhost:8080 Connection: keep-alive";
    }

    @Test
    @DisplayName("index.html을 잘반납하는지 test")
    void getRequestURLTest() throws IOException {
        String line = "GET /index.html HTTP/1.1 Host: localhost:8080 Connection: keep-alive";
        String url = HTTPRequestParser.getRequestURL(line);
        Assertions.assertEquals(url, "/index.html");
    }
}
