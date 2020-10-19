package com.sevensenders.codingchallenge.domain.daos;

import com.sevensenders.codingchallenge.domain.models.Comic;

import java.util.Collections;
import java.util.List;


public interface ComicsDao {

    List<Comic> get();

    // fallback for circuit breaker
    default List<Comic> getDefaultComics(Exception ex) {
        return Collections.emptyList();
    }
}
