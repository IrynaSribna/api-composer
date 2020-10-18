package com.sevensenders.codingchallenge.domain.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@AllArgsConstructor
@Builder
public class Comic {

    private LocalDate publishingDate;
    private String title;
    private String pictureUrl;
    private String webUrl;

}
