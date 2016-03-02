package me.federicomaggi.unifeed.controller;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Arrays;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.model.FeedItem;

/**
 * Created by federicomaggi on 26/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class RSSParser extends DefaultHandler{

    private static final String XML_ITEM_TAG = "item";

    private FeedItem currentFeedItem;
    private ArrayList<FeedItem> feedItemList;
    private StringBuilder stringBuilder;

    public RSSParser(){
        currentFeedItem = new FeedItem();
        feedItemList = new ArrayList<>();
        stringBuilder = new StringBuilder();
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        stringBuilder = new StringBuilder();
        Log.d(Helpers.getString(R.string.log_info), "Document parse started");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.d(Helpers.getString(R.string.log_info), "Document parse finished");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (localName.equalsIgnoreCase(XML_ITEM_TAG)){
            currentFeedItem = new FeedItem();
            feedItemList.add(currentFeedItem);
            return;
        }

        stringBuilder = new StringBuilder();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        for (int i = start; i < length; i++){
             stringBuilder.append(ch[i]);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        switch (localName.toLowerCase()){
            case "title":
                currentFeedItem.setTitle(stringBuilder.toString());
                break;

            case "link":
                currentFeedItem.setLink(stringBuilder.toString());
                break;

            case "pubdate":
                currentFeedItem.setDate(stringBuilder.toString().replaceAll("\\n\\r|\\n", ""));
                break;

            case "description":
                currentFeedItem.setDescription(stringBuilder.toString());
                break;

            case "item":
                break;
        }

    }

    public ArrayList<FeedItem> getFeedItemList(){
        return this.feedItemList;
    }
}
