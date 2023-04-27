package view.templateengine;

import application.model.User;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

public class MustacheTemplateEngine implements TemplateEngine {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    public static final String START_TAG = "{{#";
    public static final String LOGOUT_START_TAG = "{{^";

    public static final String END_TAG = "{{/";
    public static final String CLOSE_TAG = "}}";

    @Override
    public byte[] compile(byte[] html, ModelAndView modelAndView) throws IOException {
        StringBuilder sb = new StringBuilder(new String(html));
        //index페이지일 경우 로그인 여부를 체크해서 logout, login 을 구분하여 화면을 렌더링한다.
        //세션있으면 loginId 있어야한다

        Collection<User> userCollection = (Collection<User>) modelAndView.getModel().get("users");
        if (userCollection != null) {
            sb = handleUserListStatus(modelAndView, sb);
        } else {
            sb = handleLoginStatus(modelAndView, sb);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(sb.toString().getBytes());
        return outputStream.toByteArray();
    }

    private StringBuilder handleUserListStatus(ModelAndView modelAndView, StringBuilder sb) {
        int start = sb.indexOf(START_TAG);
        int end = sb.indexOf(END_TAG, start + START_TAG.length());
        StringBuilder userHtml = new StringBuilder();
        Collection<User> userCollection = (Collection<User>) modelAndView.getModel().get("users");

        for (User user : userCollection) {
            //TODO 태그와 일치하는지 찾는다
            String index = Integer.toString(userCollection.size());
            String userId = user.getUserId();
            String name = user.getName();
            String email = user.getEmail();

            String userHtmlFragment = sb.substring(start + START_TAG.length(), end)
                    .replaceAll("\\{\\{user\\.getUserId\\}\\}", userId)
                    .replaceAll("\\{\\{user\\.getName\\}\\}", name)
                    .replaceAll("\\{\\{user\\.getEmail\\}\\}", email)
                    .replaceAll("\\{\\{user\\.index\\}\\}", index);

            userHtml.append(userHtmlFragment);
        }

        //파싱한 문자를 시작지점부터 끝 테그까지 대체한다.
        String usersHtmlFragment = userHtml.toString();
        sb.replace(start, end + END_TAG.length(), usersHtmlFragment);
        return sb;
    }

    private StringBuilder handleLoginStatus(ModelAndView modelAndView, StringBuilder sb) throws IOException {
        int start = sb.indexOf(START_TAG);
        int end = sb.indexOf(END_TAG, start + START_TAG.length());

        Object loginId = modelAndView.getModelAttribute("loginId");
        boolean isLoggedIn = false;

        if (loginId != null) {
            log.debug("handleLoginStatus loginId", loginId);
            isLoggedIn = true;
        }

        if (isLoggedIn) {
            start = sb.indexOf("{{^logout}}") + "{{^logout}}".length();
            end = sb.indexOf("{{/^logout}}", start);
            //{{^logout}}"과 "{{/^logout}}"으로 둘러싸인 부분 문자열이 제거
            sb.replace(start - "{{^logout}}".length(), end + "{{/^logout}}".length(), "");
            sb = new StringBuilder(sb.toString().replace("{{#login}}", "").replace("{{/login}}", ""));

        } else {
            log.debug("handleLoginStatus isLoggedIn 로그인 상태 체크", isLoggedIn);
            // "{{#login}}" 문자열 다음에 오는 문자열의 시작
            start = sb.indexOf("{{#login}}") + "{{#login}}".length();
            //"{{#login}}"과 "{{/login}}" 사이의 부분 문자열을 추출하기 위해 사용
            end = sb.indexOf("{{/login}}", start);
            sb.replace(start - "{{#login}}".length(), end + "{{/login}}".length(), "");
            sb = new StringBuilder(sb.toString().replace("{{^logout}}", "").replace("{{/^logout}}", ""));
        }
        System.out.println("sb = " + sb);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(sb.toString().getBytes());
        return sb;
    }
}
