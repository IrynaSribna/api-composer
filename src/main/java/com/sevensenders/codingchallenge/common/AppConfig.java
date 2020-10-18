package com.sevensenders.codingchallenge.common;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;


@Configuration
public class AppConfig {

    @Bean(name = "restOperations")
    public RestOperations restOperations(RestTemplateBuilder builder) {
        return builder.build();
    }
}
