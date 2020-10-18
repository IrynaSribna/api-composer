package com.sevensenders.codingchallenge.infrastructure.rest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XkcdItemDTO {

    private String month;
    private int num;
    private String link;
    private String year;
    private String title;
    private String day;
    private String img;

}
