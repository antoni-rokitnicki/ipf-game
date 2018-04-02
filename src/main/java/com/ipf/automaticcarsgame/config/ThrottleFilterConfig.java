package com.ipf.automaticcarsgame.config;

import com.ipf.automaticcarsgame.filter.ThrottleFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ThrottleFilterConfig {

    @Autowired
    private Environment env;

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(createThrottleFilter());
        registration.setOrder(1);
        return registration;
    }

    private ThrottleFilter createThrottleFilter() {
        return new ThrottleFilter(env.getProperty("throttle.filter.rate-limit", Integer.class), env.getProperty("throttle.filter.path", String.class));
    }


}
