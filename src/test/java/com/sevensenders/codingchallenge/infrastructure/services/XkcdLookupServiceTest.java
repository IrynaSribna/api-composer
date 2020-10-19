package com.sevensenders.codingchallenge.infrastructure.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.sevensenders.codingchallenge.domain.models.Comic;
import com.sevensenders.codingchallenge.infrastructure.rest.models.XkcdItemDTO;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;


@RunWith(MockitoJUnitRunner.class)
public class XkcdLookupServiceTest {

    @Mock
    private RestOperations rest;

    private XkcdLookupService xkcdLookupService;

    @Before
    public void setup() {
        xkcdLookupService = new XkcdLookupService(
            "http://latest",
            "http://older",
            rest
        );
    }

    @Test
    public void returnComics() {
        // given
        XkcdItemDTO xkcdItemDTO = XkcdItemDTO.builder()
            .title("Title")
            .num(5)
            .img("img")
            .day("03")
            .month("11")
            .year("2020")
            .link("Test link")
            .build();

        doReturn(
            ResponseEntity.ok(xkcdItemDTO)
        ).when(rest).exchange(any(RequestEntity.class), any(Class.class));

        // when
        List<Comic> result = xkcdLookupService.get();

        // then
        assertThat(result.size())
            .isEqualTo(10);
        assertThat(result.get(0).getPublishingDate())
            .isEqualTo(LocalDate.of(2020, 11, 3));
        assertThat(result.get(0).getWebUrl())
            .isEqualTo("Test link");
        assertThat(result.get(0).getTitle())
            .isEqualTo("Title");
        assertThat(result.get(0).getPictureUrl())
            .isEqualTo("img");
    }
}
