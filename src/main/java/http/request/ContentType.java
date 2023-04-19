package http.request;

import java.util.Arrays;

public enum ContentType {
    JS("application/javascript", "/*", "/static"),
    CSS("text/css;charset=utf-8", "/css", "/static"),
    FONT("font/woff", "/woff", "/static"),
    HTML("text/html;charset=utf-8", "/html", "/templates"),
    PNG("image/png", "/png", "/static"),
    ICO("image/avif", "/ico", "/templates");

    private final String contentTypeHeader;
    private final String extension;
    private final String typePath;

    public String getContentTypeHeader() {
        return contentTypeHeader;
    }

    public String getTypeDirectory() {
        String CLASS_PATH = "src/main/resources";
        return CLASS_PATH + typePath;
    }

    ContentType(String headValue, String pattern, String typePath) {
        this.contentTypeHeader = headValue;
        this.extension = pattern;
        this.typePath = typePath;
    }

    public String getTypePath() {
        return typePath;
    }

    public static ContentType of(String path) {
        return Arrays.stream(values())
                .filter(e -> path.endsWith(e.extension)).findAny()
                .orElse(HTML);
    }
}
