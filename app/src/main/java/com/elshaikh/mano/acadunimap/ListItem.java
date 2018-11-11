package com.elshaikh.mano.acadunimap;

/**
 * Created by Mano on 1/21/2018.
 */

public class ListItem {
    // item title
    private String title;
    // item link
    private String link;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
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