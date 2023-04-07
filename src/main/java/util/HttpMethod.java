package util;

public enum HttpMethod {
    GET,
    POST;

    public boolean equals(String methodname) {
        return name().equals(methodname);
    }
}
