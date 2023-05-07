package view.templateengine;

import application.db.Database;
import application.model.User;
import org.junit.jupiter.api.Test;
import session.SessionManager;
import view.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MustacheTemplateEngineTest {
    public static final String START_TAG = "{{#";
    public static final String LOGOUT_START_TAG = "{{^";

    public static final String END_TAG = "{{/";
    public static final String CLOSE_TAG = "}}";


    @Test
    void compile() throws IOException {
        StringBuilder sb = new StringBuilder(new String("<div class=\"container\" id=\"main\">\n" +
                "    <div class=\"col-md-10 col-md-offset-1\">\n" +
                "        <div class=\"panel panel-default\">\n" +
                "            <table class=\"table table-hover\">\n" +
                "                <thead>\n" +
                "                {{#users}}\n" +
                "                <tr>\n" +
                "                    <th scope=\"row\">{{user.index}}</th>\n" +
                "                    <td>{{user.getUserId}}</td>\n" +
                "                    <td>{{user.getName}}</td>\n" +
                "                    <td>{{user.getEmail}}</td>\n" +
                "                    <td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>\n" +
                "                </tr>\n" +
                "                {{/users}}\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n"));

        int start = sb.indexOf(START_TAG);
        int end = sb.indexOf(END_TAG, start + START_TAG.length());
        ModelAndView modelAndView = new ModelAndView();
        User users = new User("감자", "1", "1", "aaa@aaa");
        Database.addUser(users);
        Collection<User> all = Database.findAll();
        modelAndView.setModelAttribute("users", all);

        while (start != -1 && end != -1) {

            // 각 user에 대한 html 구성
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
            String usersHtmlFragment = userHtml.toString();
            sb.replace(start, end + END_TAG.length(), usersHtmlFragment);

            // 다음 반복문 시작 위치 찾기
            start = sb.indexOf(START_TAG, end + usersHtmlFragment.length());
            end = sb.indexOf(END_TAG, start + START_TAG.length());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(sb.toString().getBytes());
        String output = sb.toString().replace("users}}", "");
        System.out.println(output);
    }

    @Test
    void loginMustacheTest() throws IOException {
        StringBuilder sb = new StringBuilder(new String("       {{#login}}\n" +
                "                <li><a href=\"user/login.html\" role=\"button\">{{user.getName}}</a></li>\n" +
                "                <li><a href=\"/user/logout\" role=\"button\">로그아웃</a></li>\n" +
                "                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>\n" +
                "                {{/login}}\n" +
                "                {{^logout}}\n" +
                "                <li><a href=\"user/login.html\" role=\"button\">로그인</a></li>\n" +
                "                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>\n" +
                "                {{/^logout}}"));
        // TODO: 로그인 체크 여부
        ModelAndView modelAndView = new ModelAndView();
        User users = new User("감자", "1", "1", "aaa@aaa");
        modelAndView.setModelAttribute("loginId", users.getName());

        Object loginId = modelAndView.getModelAttribute("loginId");
        StringBuilder userHtml = new StringBuilder();
        boolean isLoggedIn = true;

        if (loginId != null) {
            isLoggedIn = true;
        }

        String userHtmlFragment = "";
        int start;
        int end;

        if (isLoggedIn) {
            start = sb.indexOf("{{#login}}") + "{{#login}}".length();
            end = sb.indexOf("{{/login}}", start);
        } else {
            start = sb.indexOf("{{^logout}}") + "{{^logout}}".length();
            end = sb.indexOf("{{/^logout}}", start);
        }
        userHtmlFragment = sb.substring(start, end);

        String s = userHtml.append(userHtmlFragment).toString();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(sb.toString().getBytes());
        System.out.println("s = " + s);
    }
}