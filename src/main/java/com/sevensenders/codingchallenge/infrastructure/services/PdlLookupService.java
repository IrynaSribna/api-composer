package com.sevensenders.codingchallenge.infrastructure.services;

import static com.sevensenders.codingchallenge.infrastructure.rest.boundaries.xml.XmlComicsMapper.map;

import com.rometools.rome.feed.rss.Channel;
import com.sevensenders.codingchallenge.domain.daos.ComicsDao;
import com.sevensenders.codingchallenge.domain.models.Comic;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PdlLookupService implements ComicsDao {

    private final String url;
    private final RestOperations rest;

    public PdlLookupService(@Value("${poorly-drawn-lines.url}") String url,
                            RestOperations rest) {
        this.url = url;
        this.rest = rest;
    }

    @Override
    @CircuitBreaker(name = "pdlLookupService", fallbackMethod = "getDefaultComics")
    public List<Comic> get() {
        Channel channel = rest.getForObject(url, Channel.class);

        return map(channel);
    }
}
