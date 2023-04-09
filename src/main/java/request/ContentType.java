package request;

import java.util.Arrays;

public enum ContentType {
    JS("application/javascript", ".js", "/static"),
    CSS("text/css;charset=utf-8", ".css", "/static"),
    FONT("font/woff", ".woff", "/static"),
    HTML("text/html;charset=utf-8", ".html", "/templates");


    private String contentTypeHeader;
    private String extension;
    private String typePath;
    private final String CLASS_PATH = "src/main/resources";

    public String getContentTypeHeader() {
        return contentTypeHeader;
    }

    public String getTypeDirectory() {
        return CLASS_PATH + typePath;
    }

    ContentType(String headValue, String pattern, String typePath) {
        this.contentTypeHeader = headValue;
        this.extension = pattern;
        this.typePath = typePath;
    }

    public static ContentType of(String path) {
        return Arrays.stream(values())
                .filter(e -> path.endsWith(e.extension)).findAny()
                .orElse(HTML);
    }
}
