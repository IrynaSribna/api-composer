package com.sevensenders.codingchallenge.infrastructure.rest.boundaries.json;

import com.sevensenders.codingchallenge.domain.models.Comic;
import com.sevensenders.codingchallenge.infrastructure.rest.models.XkcdItemDTO;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonComicsMapper {

    public static Comic map(@NonNull XkcdItemDTO entity) {

        return Comic.builder()
            .pictureUrl(entity.getImg())
            .title(entity.getTitle())
            .webUrl(entity.getLink())
            .publishingDate(LocalDate.of(
                Integer.parseInt(entity.getYear()),
                Integer.parseInt(entity.getMonth()),
                Integer.parseInt(entity.getDay())))
            .build();
    }

}
