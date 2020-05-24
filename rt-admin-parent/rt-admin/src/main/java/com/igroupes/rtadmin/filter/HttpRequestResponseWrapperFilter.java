package com.igroupes.rtadmin.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 装饰request,方便获取body
 */
@Order(1)
@WebFilter(urlPatterns = "/*",filterName = "httpRequestWrapperFilter")
public class HttpRequestResponseWrapperFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = new HttpRequestWrapper((HttpServletRequest) request);
        HttpResponseWrapper responseWrapper = new HttpResponseWrapper((HttpServletResponse)response);
        chain.doFilter(requestWrapper, responseWrapper);
        byte[] content = responseWrapper.getContent();
        if (content.length > 0) {
            ServletOutputStream out = response.getOutputStream();
            out.write(content);
            out.flush();
        }
    }

}