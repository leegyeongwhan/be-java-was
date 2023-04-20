package view.templateengine;

import application.model.User;
import view.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

public class MustacheTemplateEngine implements TemplateEngine {
    public static final String START_TAG = "{{#";
    public static final String END_TAG = "{{/";
    public static final String CLOSE_TAG = "}}";

    @Override
    public byte[] compile(byte[] html, ModelAndView modelAndView) throws IOException {
        StringBuilder sb = new StringBuilder(new String(html));

        int start = sb.indexOf(START_TAG);
        int end = sb.indexOf(END_TAG, start + START_TAG.length());

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

            String usersHtmlFragment = START_TAG + "users" + CLOSE_TAG
                    + userHtml.toString()
                    + END_TAG + "users" + CLOSE_TAG;
            sb.replace(start, end + END_TAG.length(), usersHtmlFragment);

            // 다음 반복문 시작 위치 찾기
            start = sb.indexOf(START_TAG, end + usersHtmlFragment.length());
            end = sb.indexOf(END_TAG, start + START_TAG.length());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(sb.toString().getBytes());
        return outputStream.toByteArray();
    }

}
