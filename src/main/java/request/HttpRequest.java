package request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;
import webserver.RequestHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static request.ContentType.*;


public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    //start line
    private HttpMethod method;
    private String url;
    private String version;

    private ContentType contentType;
    private final Map<String, String> requestData = new HashMap<>();
    private HttpRequestHeader httpRequestHeader;
    private HttpRequestBody httpRequestBody;

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

        String[] tokens = readLine.split(" ");
        log.debug("tokens : {}", Arrays.toString(tokens));

        if (readLine.startsWith("GET") || readLine.startsWith("POST")) {
            log.debug("get or post = {} ", readLine);
            readStartLine(tokens);
        }
        //TODO Header 부분을 분리한다.
        if (readLine.contains(": ")) {
            readHeaderLine(readLine);
        }
        //TODO Body 부분을 분리한다.

    }

    /**
     * @param HttpRequest StartLine 분리 메서드     메서드 ,  url , 버전 으로분리
     * @return
     */
    private void readStartLine(String[] tokens) {
        //request 에서 startLine 분리한다
        this.method = HttpMethod.valueOf(tokens[0]);
        this.url = tokens[1].split("\\?")[0];
        this.version = tokens[2];
        this.contentType = of(tokens[1].split("\\?")[0]);
        log.debug("contentType : {}", contentType);
        //쿼리파라미터로 데이터를 넘길경우
        if (tokens[1].contains("?")) {
            requestData.put("parameter", tokens[1].split("\\?")[1]); // name=learner
            log.debug("requestData : {}", requestData);
        }
    }

    private void readHeaderLine(String line) {
        //TODO header부분을 파싱한다.
        //requestHeader.put(headerName, value);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }


    public String getTypeDirectory() {
        return contentType.getTypeDirectory();
    }

    public String getContentTypeHeader() {
        return contentType.getContentTypeHeader();
    }
}
