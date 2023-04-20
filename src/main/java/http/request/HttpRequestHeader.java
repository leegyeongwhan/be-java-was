package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HttpRequestHeader {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Map<String, String> headerMap;
    private ContentType contentType;

    public HttpRequestHeader(Map<String, String> headerMap, ContentType contentType) {
        this.headerMap = headerMap;
        this.contentType = contentType;
    }

    public static HttpRequestHeader of(BufferedReader br) throws IOException {
        String line;
        Map<String, String> header = new HashMap<>();
        ContentType contentType = null;

        while (!(line = br.readLine()).equals("")) {
            String[] tokens = line.split(" ");
            //  log.debug(" HttpRequestHeader tokens : {}", Arrays.toString(tokens));

            if (tokens[0].contains("Accept:")) {
//                log.debug("tokens[0] : {}", tokens[0]);
//                log.debug("tokens[1] : {}", tokens[1]);
//                log.debug("tokens[1].split()[0] : {}", tokens[1].split(",")[0]);
                contentType = ContentType.of(tokens[1].split(",")[0]);
                //               log.debug("contentType : {}", contentType);
                continue;
            }

            //TODO Header 부분을 분리한다.
            if (line.contains(": ")) {
                String headerName = line.split(": ")[0].trim(); // Header 이름을 trim() 메소드를 사용하여 양쪽 공백 제거
                String headerValue = line.split(": ")[1].trim(); // Header 값을 trim() 메소드를 사용하여 양쪽 공백 제거
                header.put(headerName, headerValue);
            }

        }
        return new HttpRequestHeader(header, contentType);
    }

    public int getContentLength() {
        String contentLengthHeader = this.headerMap.get("Content-Length");
        //      log.debug(" HttpRequestHeader contentLengthHeader : {}", contentLengthHeader);

        if (contentLengthHeader != null) {
            return Integer.parseInt(contentLengthHeader);
        }
        return 0;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public Optional<String> getCookie() {
        return Optional.ofNullable(headerMap.get("Cookie"));
    }
}

