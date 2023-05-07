package filter;

import http.request.HttpRequest;
import http.response.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {

    private List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void doFilter(HttpRequest request, HttpResponse response) throws Exception {
        if (filters.isEmpty()) {
            return;
        }

        Filter filter = filters.get(0);
        List<Filter> nextFilters = filters.subList(1, filters.size());
        FilterChain nextFilterChain = new FilterChain();
        nextFilterChain.filters = nextFilters;

        filter.doFilter(request, response, nextFilterChain);
    }
}
