package me.federicomaggi.unifeed.model;

/**
 * Created by federicomaggi on 26/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class FeedItem {

    private String title;
    private String link;
    private String description;
    private String date;

    public FeedItem(){

    }

    public FeedItem(String title, String link, String description, String date) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.date = date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
