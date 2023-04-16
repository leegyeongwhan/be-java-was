package http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpMethod;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestStartLine {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private HttpMethod method;
    private String url;
    private String version;

    private Map<String, String> requestData;


    /**
     * @param HttpRequest StartLine 분리 메서드     메서드 ,  url , 버전 으로분리
     * @return
     */
    public static HttpRequestStartLine of(BufferedReader br) throws IOException {
        //request 에서 startLine 분리한다
        String line = br.readLine();
        while (br.equals("") || br == null) {
            //TODO 예외처리 해주자
            break;
        }

        String[] tokens = line.split(" ");
        log.debug("HttpRequestStartLine tokens : {}", Arrays.toString(tokens));

        HttpMethod method = HttpMethod.valueOf(tokens[0]);
        String url = tokens[1].split("\\?")[0];
        String version = tokens[2];

        Map<String, String> requestData = new HashMap<>();

        //쿼리파라미터로 데이터를 넘길경우
        if (tokens[1].contains("?")) {
            requestData.put("parameter", tokens[1].split("\\?")[1]); // name=learner
            log.debug("requestData : {}", requestData);
        }
        return new HttpRequestStartLine(method, url, version, requestData);
    }

    public HttpRequestStartLine(HttpMethod method, String url, String version, Map<String, String> requestData) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.requestData = requestData;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getRequestData() {
        return requestData;
    }
}
