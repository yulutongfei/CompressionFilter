package com.sunxu;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

/**
 * @author 孙许
 * @date 2018/03/02
 * @description
 */
public class RequestLogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Instant time = Instant.now();
        Long timeStart = null;
        Long timeEnd;
        try {
            timeStart = System.currentTimeMillis();
            chain.doFilter(request, response);
        } finally {
            timeEnd = System.currentTimeMillis();
            HttpServletRequest in = (HttpServletRequest) request;
            HttpServletResponse out = (HttpServletResponse) response;
            String length = out.getHeader("Content-Length");
            if (length == null || length.length() == 0) {
                length = "-";
            }
            System.out.println(in.getRemoteAddr() + " - - [" + time + "]" +
            " \"" + in.getMethod() + " " + in.getRequestURI() + " " +
            in.getProtocol() + "\" " + out.getStatus() + " " + length +
            " " + (timeEnd - timeStart));
        }
    }

    @Override
    public void destroy() {

    }
}
