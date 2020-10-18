package com.sevensenders.codingchallenge.domain;

import com.sevensenders.codingchallenge.domain.daos.ComicsDao;
import com.sevensenders.codingchallenge.domain.models.Comic;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class ComicsProvider {

    private List<ComicsDao> picturesDaos;

    public List<Comic> provide() {
        List<Comic> comics = new LinkedList<>();

        for (ComicsDao dao : picturesDaos) {
            comics.addAll(dao.get());
        }

        // sort comics by publishing date from recent to older
        comics.sort(getComicComparator());

        return Collections.unmodifiableList(comics);
    }

    private Comparator<Comic> getComicComparator() {
        return Comparator.comparing(Comic::getPublishingDate)
            .reversed();
    }
}
