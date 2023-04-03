package parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPRequestParser {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    static public void parseRequestHeaders(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = br.readLine();
        log.debug("request line = {}", line);

        while (!line.equals("")) {
            line = br.readLine();
            log.debug("header = {}", line);
        }
    }

    public static String getRequestURL(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = br.readLine();
        log.debug("request line = {}", line);
        String[] tokens = line.split(" ");
        return tokens[1];
    }
}
