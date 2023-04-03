package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;

public class HTTPRequestParser {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    public static String getRequestURL(String line) {
        String[] token = line.split(" ");
        String path = token[1];
        log.debug("request path={}", path);
        return path;
    }

    public static void parseRequestHeaders(String line, BufferedReader br) throws IOException {
        while (!line.equals("") && !line.equals(null)) {
            log.debug("requestHeader={}", line);
            line = br.readLine();
        }
    }
}
