package com.elshaikh.mano.acadunimap;

/**
 * Created by Mano on 1/30/2018.
 */

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * SAX tag handler
 *
 * @author ITCuties
 *
 */
public class RssParser2 extends DefaultHandler {

    private List<RssItem> rssItems;

    // Used to reference item while parsing
    private RssItem currentItem;

    // Parsing title indicator
    private boolean parsingTitle;
    // A buffer used to build current title being parsed
    private StringBuffer currentTitleSb;

    // Parsing link indicator
    private boolean parsingLink;

    public RssParser2() {
        rssItems = new ArrayList<RssItem>();
    }

    public List<RssItem> getItems() {
        return rssItems;
    }
    boolean bTitle = false;
    boolean bLink = false;
    boolean bItem = false;


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("item")) {
            //create a new Employee and put it in Map
            //String id = attributes.getValue("id");
            //initialize Employee object and set id attribute
            currentItem = new RssItem();
            bItem = true;
            //emp.setId(Integer.parseInt(id));
            //initialize list
            if (rssItems == null)
                rssItems = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("title") && bItem) {
            //set boolean values for fields, will be used in setting Employee variables
            bTitle = true;
        } else if (qName.equalsIgnoreCase("link")  && bItem) {
            bLink = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("item")) {
            //add Employee object to list
            rssItems.add(currentItem);
            bItem = false;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (bTitle) {
            //age element, set Employee age
            currentItem.setTitle(new String(ch, start, length));
            bTitle = false;
        } else if (bLink) {
            currentItem.setLink(new String(ch, start, length));
            bLink = false;
        }
    }

}
