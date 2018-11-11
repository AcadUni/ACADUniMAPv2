package com.elshaikh.mano.acadunimap;

/**
 * Created by Mano on 1/30/2018.
 */

public class RssItem {

    // item title
    private String title;
    // item link
    private String link;
    private String Fragment_name = "rssitem";


    public String getTitle() {
        return title;
    }

    public String getFragment_name() {
        return Fragment_name;
    }

    public void setFragment_name(String fragment_name) {
        Fragment_name = fragment_name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return title;
    }

}
