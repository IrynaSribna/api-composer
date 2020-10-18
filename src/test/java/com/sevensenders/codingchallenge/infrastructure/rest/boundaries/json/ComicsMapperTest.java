package com.sevensenders.codingchallenge.infrastructure.rest.boundaries.json;

import static com.sevensenders.codingchallenge.infrastructure.rest.boundaries.json.ComicsMapper.map;
import static org.assertj.core.api.Assertions.assertThat;

import com.sevensenders.codingchallenge.domain.models.Comic;
import com.sevensenders.codingchallenge.infrastructure.rest.models.XkcdItemDTO;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;


class ComicsMapperTest {

    @Test
    public void returnComic() {

        // given
        XkcdItemDTO xkcdItemDTO = XkcdItemDTO.builder()
            .day("01")
            .month("12")
            .year("2020")
            .link("http://example.com")
            .img("http://img.png")
            .num(23)
            .title("Title")
            .build();

        // when
        Comic domain = map(xkcdItemDTO);

        // then
        assertThat(domain.getPictureUrl())
            .isEqualTo("http://img.png");

        assertThat(domain.getTitle())
            .isEqualTo("Title");

        assertThat(domain.getWebUrl())
            .isEqualTo("http://example.com");

        assertThat(domain.getPublishingDate())
            .isEqualTo(LocalDate.of(2020, 12, 1));
    }
}