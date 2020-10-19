package com.sevensenders.codingchallenge.infrastructure.rest.boundaries.xml;

import static com.sevensenders.codingchallenge.infrastructure.rest.boundaries.xml.XmlComicsMapper.map;
import static org.assertj.core.api.Assertions.assertThat;

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

import org.junit.jupiter.api.Test;


class XmlComicsMapperTest {

    @Test
    void returnComic() throws ParseException {

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

        // when
        List<Comic> domain = map(channel);

        // then
        assertThat(domain)
            .isNotEmpty();
        assertThat(domain.size())
            .isEqualTo(1);

        assertThat(domain.get(0).getPictureUrl())
            .isEqualTo("http://img.png");

        assertThat(domain.get(0).getTitle())
            .isEqualTo("Title");

        assertThat(domain.get(0).getWebUrl())
            .isEqualTo("http://example.com");

        assertThat(domain.get(0).getPublishingDate())
            .isEqualTo(LocalDate.of(2020, 12, 1));
    }

    @Test
    void returnEmptyListWhenChannelIsNull() throws ParseException {

        // given
        Channel channel = null;

        List<Comic> domain = map(channel);

        // then
        assertThat(domain)
            .isEmpty();
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
