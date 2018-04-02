package com.ipf.automaticcarsgame.filter;

import com.google.common.util.concurrent.RateLimiter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ThrottleFilter implements Filter {
    private final String regexPath;
    private Pattern pattern;
    final Map<String, RateLimiter> carRateLimitMap = new ConcurrentHashMap<>(); //todo change to expringMap, check urlencoded car name
    private final Integer rateLimit;

    public ThrottleFilter(Integer rateLimit, String regexPath) {
        this.rateLimit = rateLimit;
        this.regexPath = regexPath;
        this.pattern = Pattern.compile(this.regexPath);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final boolean carMove = httpServletRequest.getServletPath().matches(this.regexPath);
        if (carMove) {
            final Optional<String> carNameOpt = findCarName(httpServletRequest);
            if (carNameOpt.isPresent()) {
                this.carRateLimitMap.putIfAbsent(carNameOpt.get(), RateLimiter.create(rateLimit));
                final RateLimiter rateLimiter = this.carRateLimitMap.get(carNameOpt.get());
                rateLimiter.acquire();
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private Optional<String> findCarName(HttpServletRequest httpServletRequest) {
        String carName = null;
        final Matcher m = pattern.matcher(httpServletRequest.getServletPath());
        if (m.matches()) {
            carName = m.group(1);
        }
        return Optional.ofNullable(carName);
    }

    @Override
    public void destroy() {

    }
}
