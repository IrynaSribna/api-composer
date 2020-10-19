package com.sevensenders.codingchallenge.infrastructure.rest.boundaries.xml;

import static java.util.stream.Collectors.toList;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import com.sevensenders.codingchallenge.domain.models.Comic;

import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlComicsMapper {

    public static List<Comic> map(Channel channel) {

        return channel != null
            ? channel.getItems()
                .stream()
                .map(x -> itemToComic.apply(x))
                .collect(toList())
            : Collections.emptyList();
    }

    private static Function<Item, Comic> itemToComic = item -> Comic.builder()
        .publishingDate(item.getPubDate()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        )
        .webUrl(item.getUri())
        .title(item.getTitle())
        .pictureUrl(parseText(
            item.getContent().getValue())
        )
        .build();

    private static String parseText(String text) {
        String patternString = "img (loading=\"lazy\" )?src=\"([^\"]+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "";
    }
}
