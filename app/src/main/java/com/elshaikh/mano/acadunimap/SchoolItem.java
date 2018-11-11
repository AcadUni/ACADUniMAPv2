package com.elshaikh.mano.acadunimap;

/**
 * Created by Mano on 1/22/2018.
 */


public class SchoolItem {
    String name;
    String link;
    String tag;
    int icon_id;

    public SchoolItem(String name, String link, String tag) {
        this.name = name;
        this.link = link;
        this.tag = tag;
    }
    public SchoolItem(String name, String link, String tag, int ic) {
        this.name = name;
        this.link = link;
        this.tag = tag;
        this.icon_id = ic;
    }

    @Override
    public String toString() {
        return name;
    }

}