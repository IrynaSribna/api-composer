package com.sevensenders.codingchallenge.infrastructure.services;

import static com.sevensenders.codingchallenge.infrastructure.rest.boundaries.json.JsonComicsMapper.map;

import com.sevensenders.codingchallenge.domain.daos.ComicsDao;
import com.sevensenders.codingchallenge.domain.models.Comic;
import com.sevensenders.codingchallenge.infrastructure.rest.models.XkcdItemDTO;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class XkcdLookupService implements ComicsDao {

    private static final int AMOUNT_OF_ITEMS = 9;

    private final String latestItemUrl;
    private final String itemByIdUrl;
    private final RestOperations rest;

    public XkcdLookupService(@Value("${xkcd.url.latest}") String latestItemUrl,
                             @Value("${xkcd.url.by-id}") String itemByIdUrl,
                             RestOperations rest) {
        this.latestItemUrl = latestItemUrl;
        this.itemByIdUrl = itemByIdUrl;
        this.rest = rest;
    }

    @Override
    public List<Comic> get() {
        List<Comic> comics = new LinkedList<>();

        // get latest comic
        URI uriForLatestItem = UriComponentsBuilder.fromUriString(latestItemUrl)
            .build()
            .toUri();

        ResponseEntity<XkcdItemDTO> latestItemResponse = getXkcdItemDTO(uriForLatestItem);

        XkcdItemDTO latestItem = latestItemResponse.getBody();
        int latestId = latestItem.getNum();
        Comic latestComic = map(latestItemResponse.getBody());
        comics.add(latestComic);

        // get older comics
        for (int i = 0; i < AMOUNT_OF_ITEMS; i++) {
            URI uri = UriComponentsBuilder.fromUriString(itemByIdUrl)
                .buildAndExpand(Collections.singletonMap("id", --latestId))
                .toUri();

            ResponseEntity<XkcdItemDTO> itemResponse = getXkcdItemDTO(uri);

            comics.add(map(itemResponse.getBody()));
        }

        return comics;
    }

    private ResponseEntity<XkcdItemDTO> getXkcdItemDTO(URI uri) {
        return rest.exchange(RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_JSON).build(),
            XkcdItemDTO.class
        );
    }
}
