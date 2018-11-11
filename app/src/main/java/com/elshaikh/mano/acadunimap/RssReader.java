package com.elshaikh.mano.acadunimap;

/**
 * Created by Mano on 1/30/2018.
 */

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.List;

/**
 * Class reads RSS data.
 *
 * @author ITCuties
 *
 */
public class RssReader {

    private String rssUrl;

    /**
     * Constructor
     *
     * @param rssUrl
     */
    public RssReader(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    /**
     * Get RSS items.
     *
     * @return
     */
    public List<RssItem> getItems() throws Exception {
        // SAX parse RSS data
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        RssParser2 handler = new RssParser2();

        saxParser.parse(rssUrl, handler);

        return handler.getItems();

    }

}

