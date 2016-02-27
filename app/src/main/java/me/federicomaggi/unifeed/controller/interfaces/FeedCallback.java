package me.federicomaggi.unifeed.controller.interfaces;

import java.util.ArrayList;

import me.federicomaggi.unifeed.model.FeedItem;

/**
 * Created by federicomaggi on 26/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public interface FeedCallback {

    void callback(ArrayList<FeedItem> feedItemArrayList);
}
