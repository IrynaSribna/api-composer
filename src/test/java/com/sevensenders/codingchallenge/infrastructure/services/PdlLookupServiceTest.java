package com.sevensenders.codingchallenge.infrastructure.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Item;
import com.sevensenders.codingchallenge.domain.models.Comic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestOperations;

@RunWith(MockitoJUnitRunner.class)
public class PdlLookupServiceTest {

    private final static String URL = "http://url";

    @Mock
    private RestOperations rest;

    private PdlLookupService pdlLookupService;

    @Before
    public void setup() {
        pdlLookupService = new PdlLookupService(
            URL,
            rest
        );
    }

    @Test
    public void returnComics() throws ParseException {
        // given
        Channel channel = new Channel();

        Content content = new Content();
        content.setValue(getValue());
        Item item = new Item();
        item.setUri("http://example.com");
        item.setTitle("Title");

        Date date = getDate();
        item.setPubDate(date);

        item.setContent(content);
        channel.setItems(Collections.singletonList(item));

        when(rest.getForObject(eq(URL), any(Class.class)))
            .thenReturn(channel);

        // when
        List<Comic> result = pdlLookupService.get();

        // then
        assertThat(result.size())
            .isEqualTo(1);
        assertThat(result.get(0).getPublishingDate())
            .isEqualTo(LocalDate.of(2020, 12, 1));
        assertThat(result.get(0).getWebUrl())
            .isEqualTo("http://example.com");
        assertThat(result.get(0).getTitle())
            .isEqualTo("Title");
        assertThat(result.get(0).getPictureUrl())
            .isEqualTo("http://img.png");
    }

    private String getValue() {
        return "<img loading=\"lazy\" src=\"http://img.png\" " +
            "alt=\"\" class=\"wp-image-7791\" width=\"810\" height=\"719\" srcset=\"" +
            "sizes=\"(max-width: 810px) 100vw, 810px\" /></figure></div>\n" +
            "<div class=\"feedflare\">";
    }

    private Date getDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "1-Dec-2020";
        return formatter.parse(dateInString);
    }
}
