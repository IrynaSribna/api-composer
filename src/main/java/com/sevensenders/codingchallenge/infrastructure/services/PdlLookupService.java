package com.sevensenders.codingchallenge.infrastructure.services;

import static com.sevensenders.codingchallenge.infrastructure.rest.boundaries.xml.XmlComicsMapper.map;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

import com.sevensenders.codingchallenge.domain.daos.ComicsDao;
import com.sevensenders.codingchallenge.domain.models.Comic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class PdlLookupService implements ComicsDao {

    private final String url;

    public PdlLookupService( @Value("${poorly-drawn-lines.url}") String url) {
        this.url = url;
    }

    @Override
    public List<Comic> get() {

        DocumentBuilderFactory factory = newInstance();
        DocumentBuilder builder;
        factory.setNamespaceAware(true);
        Document doc = null;
        try {
            builder = factory.newDocumentBuilder();
            doc = Objects.requireNonNull(builder).parse(url);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.error("Unable to parse xml feed: {}", e.getMessage());
        }

        // normalize XML response
        Objects.requireNonNull(doc).getDocumentElement().normalize();

        //read items list
        NodeList nodeList = doc.getElementsByTagName("item");

        //create an empty list for comics
        List<Comic> comics = new ArrayList<>();

        //loop all available item nodes
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Comic picture = map(element);
                comics.add(picture);
            }
        }

        return comics;
    }
}
