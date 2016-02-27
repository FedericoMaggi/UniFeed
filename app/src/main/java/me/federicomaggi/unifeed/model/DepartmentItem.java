package me.federicomaggi.unifeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static DepartmentItem unparse(JSONObject obj){

        try {
            return new DepartmentItem(obj.getString("name"), obj.getString("url"), obj.getString("acronym"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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

    public String parse() throws JSONException {
        return new JSONObject()
                .put("name",this.getName())
                .put("url",this.getUrl())
                .put("acronym",this.getAcronym())
                .toString();
    }


}
