package com.sevensenders.codingchallenge.infrastructure.rest.boundaries.xml;

import com.sevensenders.codingchallenge.domain.models.Comic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Element;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlComicsMapper {

    public static Comic map(Element element) {

        return Comic.builder()
            .pictureUrl(
                parseText(
                    element.getElementsByTagNameNS("*", "encoded").item(0).getFirstChild().getTextContent()
                )
            )
            .title(element.getElementsByTagName("title").item(0).getTextContent())
            .webUrl(element.getElementsByTagName("link").item(0).getTextContent())
            .publishingDate(
                parseLocalDate(
                    element.getElementsByTagName("pubDate").item(0).getTextContent()
                )
            )
            .build();
    }

    private static String parseText(String text) {
        String patternString = "img (loading=\"lazy\" )?src=\"([^\"]+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return null;
    }

    private static LocalDate parseLocalDate(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");

        return LocalDate.parse(text, formatter);
    }
}
