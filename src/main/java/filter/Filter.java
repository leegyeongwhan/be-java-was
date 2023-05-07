package filter;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Filter {

    default void init() throws ServletException {
        // 필터 초기화
    }

    void doFilter(HttpRequest request, HttpResponse response,
                  FilterChain chain) throws Exception;

    default void destroy() {
        //필터종료
    }
}
