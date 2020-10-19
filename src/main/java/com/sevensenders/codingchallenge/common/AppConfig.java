package com.sevensenders.codingchallenge.common;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

    @Bean(name = "restOperations")
    public RestOperations restOperations() {
        RestTemplate restTemplate = new RestTemplate();
        RssChannelHttpMessageConverter rssChannelConverter = new RssChannelHttpMessageConverter();
        rssChannelConverter.setSupportedMediaTypes(
            Collections.singletonList(MediaType.TEXT_XML)
        );
        restTemplate.getMessageConverters().add(rssChannelConverter);
        return restTemplate;
    }
}
