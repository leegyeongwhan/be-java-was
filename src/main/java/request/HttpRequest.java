package request;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;


public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    //start line
    private String method;
    private String url;

    private Map<String, String> requestHeader;
    private String requestBody;

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line;
        while (!(line = br.readLine()).equals("")) {
            if (line == null) {
                return;
            }
            // log.debug("line : {}", line);
            readRequest(line);
        }
    }

    private void readRequest(String readLine) {
        //TODO startLine, header ,body 으로 구분한다.
        //http 메서드로 구분
        if (readLine.startsWith("GET") || readLine.startsWith("POST")) {
            readStartLine(readLine);
        }
        //TODO Header 부분을 분리한다.
        if (readLine.contains(":")) {
            readHeaderLine(readLine);
        }
        //TODO Body 부분을 분리한다.
    }

    private void readHeaderLine(String readLine) {
        //TODO header부분을 파싱한다.
        //requestHeader.put(headerName, value);
    }

    private void readStartLine(String readLine) {
        String[] tokens = readLine.split(" ");
        this.method = tokens[0];
        String url = tokens[1];
        log.debug("readStartLine  method : {}", method);

        if (url.startsWith("/user/create?")) {
            this.url = url.split("\\?")[0];
            String queryString = url.split("\\?")[1];

            queryString = URLDecoder.decode(queryString, StandardCharsets.UTF_8);
            log.debug("queryString : {}", queryString);

            User user = User.createUser(queryString);
            log.debug("user : {}", user);
            log.debug("url : {}", this.url);
            return;
        }
        log.debug("readStartLine url : {}", url);
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", requestBody='" + requestBody + '\'' +
                '}';
    }
}
