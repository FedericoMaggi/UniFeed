package me.federicomaggi.unifeed.controller;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.interfaces.RequestCallback;
import me.federicomaggi.unifeed.controller.interfaces.ServiceCallback;
import me.federicomaggi.unifeed.controller.interfaces.FeedCallback;
import me.federicomaggi.unifeed.controller.services.FeedRequest;
import me.federicomaggi.unifeed.controller.services.ServiceRequest;
import me.federicomaggi.unifeed.model.FeedItem;

/**
 * Created by federicomaggi on 25/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class CommunicationHandler {

    public CommunicationHandler() {}

    public void downloadDepartments(final RequestCallback requestCallback) {

        Helpers.shared().setProgressDialog(null);
        new ServiceRequest(Helpers.getString(R.string.department_list_url), new ServiceCallback() {
            @Override
            public void callback(JSONObject response) {

                Helpers.shared().removeProgressDialog();

                if (response == null) {
                    Log.i(Helpers.getString(R.string.log_info), "NULL RESPONSE");
                    requestCallback.callback(false);
                    return;
                }

                Log.i(Helpers.getString(R.string.log_info), response.toString());
                Helpers.shared().setDepartmentListFromJSON(response);
                requestCallback.callback(true);

            }
        }).execute();
    }


    public void downloadFeed(String feedUrl, final String feedAcronym, final RequestCallback requestCallback){

        Helpers.shared().setProgressDialog(Helpers.getString(R.string.loading_feed));

        new FeedRequest(feedUrl, new FeedCallback() {
            @Override
            public void callback(ArrayList<FeedItem> feedItemArrayList) {

                Log.i(Helpers.getString(R.string.log_info), "Feed retrieval service finished");
                Helpers.shared().removeProgressDialog();

                if (feedItemArrayList == null){
                    requestCallback.callback(false);
                }

                Helpers.shared().setFeedList(feedItemArrayList, feedAcronym);
                requestCallback.callback(true);

            }

        }).execute();
    }
}
