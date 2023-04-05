package webserver;

import java.io.IOException;

public class RequestMapping {

    private String url = "";

    public void mapping(String url) {
        if (url.equals("/")) {
            this.url = "/index.html";
            return;
        }

        if (url.equals("/user/create")) {
            this.url = "redirect:/";
            return;
        }
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
