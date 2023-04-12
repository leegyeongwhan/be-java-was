package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {
    private Map<String, String> body;
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    public HttpRequestBody(Map<String, String> body) {
        this.body = body;
    }

    public static HttpRequestBody of(BufferedReader br, int contentLength) throws IOException {
        String queryString = readData(br, contentLength);
        if (contentLength == 0) {
            return new HttpRequestBody(new HashMap<>());
        }
        Map<String, String> parsingQueryMap = ParsingQueryString(queryString);
        log.debug(" HttpRequestBody contentLength : {}", contentLength);
        return new HttpRequestBody(parsingQueryMap);
    }

    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static Map<String, String> ParsingQueryString(String queryString) {
        log.debug(" HttpRequestBody queryString : {}", queryString);
        Map<String, String> body = new HashMap<>();

        Arrays.stream(URLDecoder.decode(queryString, StandardCharsets.UTF_8).split("\\&"))
                .map(str -> str.split("="))
                .forEach(split -> body.put(split[0], split[1]));

        return body;
    }

    public Map<String, String> getBody() {
        return body;
    }
}
