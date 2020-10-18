package com.sevensenders.codingchallenge.infrastructure.rest.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sevensenders.codingchallenge.domain.ComicsProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class ComicsControllerTest {

    @Mock
    private ComicsProvider comicsProvider;

    @InjectMocks
    private ComicsController controller;

    @Test
    public void getComicsReturnsHttpStatus200() {
        // when
        ResponseEntity entity = controller.getComics();

        // then
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}