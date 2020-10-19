package com.sevensenders.codingchallenge.infrastructure.rest.controllers;

import static org.springframework.http.HttpStatus.OK;

import com.sevensenders.codingchallenge.domain.ComicsProvider;
import com.sevensenders.codingchallenge.domain.models.Comic;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/comics-service/", produces = "application/json")
public class ComicsController {

    private ComicsProvider comicsProvider;

    @GetMapping(value = "comics")
    public ResponseEntity<Collection<Comic>> getComics() {

        return new ResponseEntity<>(comicsProvider.provide(), OK);
    }
}