package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import static request.ContentType.*;


public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    //start line
    private String method;
    private String url;
    private String version;

    private ContentType contentType;
    private Map<String, String> requestData;
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
            log.debug("get or post = {} ", readLine);
            readStartLine(readLine);
        }
        //TODO Header 부분을 분리한다.
        if (readLine.contains(":")) {
            readHeaderLine(readLine);
        }
        //TODO Body 부분을 분리한다.

//        parseStartLine(readLine);
//        parseHeader(readLine);
    }

    private void readHeaderLine(String readLine) {
        //TODO header부분을 파싱한다.
        //requestHeader.put(headerName, value);
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getRequestBody() {
        return requestBody;
    }

    private void readStartLine(String readLine) {
        String[] tokens = readLine.split(" ");
        this.method = tokens[0];
        this.url = tokens[1].split("\\?")[0];
        this.version = tokens[2];
        this.contentType = of(tokens[1].split("\\?")[0]);

        log.debug("tokens : {}", Arrays.toString(tokens));
        log.debug("contentType : {}", contentType);

        if (tokens[1].contains("?")) {
            requestData.put("parameter", tokens[1].split("\\?")[1]); // name=learner
            log.debug("requestData : {}", requestData);
        }
    }

    public String getTypeDirectory() {
        return contentType.getTypeDirectory();
    }

    public String getContentTypeHeader() {
        return contentType.getContentTypeHeader();
    }
}
