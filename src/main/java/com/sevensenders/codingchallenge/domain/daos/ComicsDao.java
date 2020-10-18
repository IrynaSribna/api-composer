package com.sevensenders.codingchallenge.domain.daos;

import com.sevensenders.codingchallenge.domain.models.Comic;

import java.util.List;


public interface ComicsDao {

    List<Comic> get();

}
