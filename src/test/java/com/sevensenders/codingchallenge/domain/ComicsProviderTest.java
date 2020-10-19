package com.sevensenders.codingchallenge.domain;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

import com.sevensenders.codingchallenge.domain.daos.ComicsDao;
import com.sevensenders.codingchallenge.domain.models.Comic;
import com.sevensenders.codingchallenge.infrastructure.services.PdlLookupService;
import com.sevensenders.codingchallenge.infrastructure.services.XkcdLookupService;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ComicsProviderTest {

    @Mock
    private XkcdLookupService xkcdLookupService;

    @Mock
    private PdlLookupService pdlLookupService;

    private ComicsProvider provider;

    @Before
    public void setup() {
        List<ComicsDao> daos = new LinkedList<>();
        daos.add(xkcdLookupService);
        daos.add(pdlLookupService);
        provider = new ComicsProvider(daos);
    }

    @Test
    public void returnSortedComics() {
        // given
        List<Comic> xkcdComics = new LinkedList<>();
        xkcdComics.add(Comic.builder().publishingDate(LocalDate.of(2020, 9, 9)).build());
        when(xkcdLookupService.get()).thenReturn(xkcdComics);

        List<Comic> pdlComics = new LinkedList<>();
        xkcdComics.add(Comic.builder().publishingDate(LocalDate.of(2020, 10, 10)).build());
        xkcdComics.add(Comic.builder().publishingDate(LocalDate.of(2020, 5, 5)).build());
        when(pdlLookupService.get()).thenReturn(pdlComics);

        List<Comic> expected = new LinkedList<>();
        expected.add(Comic.builder().publishingDate(LocalDate.of(2020, 10, 10)).build());
        expected.add(Comic.builder().publishingDate(LocalDate.of(2020, 9, 9)).build());
        expected.add(Comic.builder().publishingDate(LocalDate.of(2020, 5, 5)).build());

        // when
        List<Comic> result =  provider.provide();

        // then
        assertThat(result)
            .isNotEmpty();
        assertThat(result)
            .isEqualTo(expected);
    }
}
