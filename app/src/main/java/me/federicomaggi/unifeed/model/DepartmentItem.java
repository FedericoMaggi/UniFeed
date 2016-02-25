package me.federicomaggi.unifeed.model;

/**
 * Created by federicomaggi on 25/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class DepartmentItem {

    private String name;
    private String url;
    private String acronym;

    public DepartmentItem(String name, String url, String acronym){
        this.name = name;
        this.url = url;
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getAcronym() {
        return acronym;
    }
}
