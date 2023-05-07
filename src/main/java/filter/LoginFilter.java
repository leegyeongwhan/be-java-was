package filter;

import http.request.HttpRequest;
import http.response.HttpResponse;
import view.ViewResolver;

public class LoginFilter implements Filter {
    private static final String LOGIN_PAGE_PATH = "/login.html";
    private static final String LOGIN_PROCESSING_PATH = "/login";
    @Override
    public void doFilter(HttpRequest request, HttpResponse response, FilterChain chain) throws Exception {
        String requestUri = request.getRequestUri();

        // 로그인 페이지에 접근하는 경우, 다음 필터 또는 서블릿으로 넘기지 않고 해당 페이지를 보여줍니다.
        if (requestUri.equals(LOGIN_PAGE_PATH)) {
            ViewResolver.run(LOGIN_PAGE_PATH, response);
            return;
        }

        // 로그인 정보를 처리하는 경우, 다음 필터 또는 서블릿으로 넘기지 않고 로그인을 처리합니다.
        if (requestUri.equals(LOGIN_PROCESSING_PATH)) {
            String id = request.getParameter("id");
            String password = request.getParameter("password");

            // id와 password를 검증하는 로직이 있다면 이를 추가하시면 됩니다.

            // 검증에 실패한 경우, 로그인 페이지로 다시 이동합니다.

            // 검증에 성공한 경우, 세션에 로그인 정보를 저장하고 메인 페이지로 이동합니다.
            return;
        }

        // 로그인 페이지나 로그인 처리를 하는 경우가 아닌 경우, 다음 필터 또는 서블릿으로 넘깁니다.
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
