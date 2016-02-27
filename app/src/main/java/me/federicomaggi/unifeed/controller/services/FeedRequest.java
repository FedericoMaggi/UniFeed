package me.federicomaggi.unifeed.controller.services;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.Helpers;
import me.federicomaggi.unifeed.controller.RSSParser;
import me.federicomaggi.unifeed.controller.interfaces.FeedCallback;
import me.federicomaggi.unifeed.model.FeedItem;

/**
 * Created by federicomaggi on 26/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class FeedRequest extends AsyncTask<Void,Void,ArrayList<FeedItem>> {

    private static final String ENCODING = "ISO-8859-1";

    private String feedUrl;
    private FeedCallback feedCallback;
    private RSSParser mRssParser;

    public FeedRequest(String feedUrl, FeedCallback feedCallback){
        this.feedUrl = feedUrl;
        this.feedCallback = feedCallback;
    }

    @Override
    protected void onPreExecute(){

        mRssParser = new RSSParser();
    }

    @Override
    protected ArrayList<FeedItem> doInBackground(Void... params) {

        Log.i(Helpers.getString(R.string.log_info), "Started feed retrieval\n>> "+feedUrl);

        try{
            URL url = new URL(this.feedUrl);
            InputSource inputSource = new InputSource(url.openStream());
            inputSource.setEncoding(ENCODING);

            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(mRssParser);

            xmlReader.parse(inputSource);

        } catch (SAXException | ParserConfigurationException | IOException e){
            e.printStackTrace();
        }

        return mRssParser.getFeedItemList();
    }

    @Override
    protected void onPostExecute(ArrayList<FeedItem> feedItemsList){
        feedCallback.callback(feedItemsList);
    }
}
