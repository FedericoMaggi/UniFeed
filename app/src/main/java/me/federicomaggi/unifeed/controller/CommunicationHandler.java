package me.federicomaggi.unifeed.controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.interfaces.RequestCallback;
import me.federicomaggi.unifeed.controller.interfaces.ServiceCallback;
import me.federicomaggi.unifeed.controller.services.ServiceRequest;

/**
 * Created by federicomaggi on 25/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class CommunicationHandler {

    public CommunicationHandler() {}

    public void downloadDeparments( final RequestCallback requestCallback){
        Helpers.shared().setProgressDialog(null);
        new ServiceRequest(Helpers.getString(R.string.department_list_url), new ServiceCallback() {
            @Override
            public void callback(JSONObject response) {
                Helpers.shared().removeProgressDialog();

                if (response == null){
                    Log.i(Helpers.getString(R.string.log_info),"NULL RESPONSE");
                    requestCallback.callback(false);
                    return;
                }

                Log.i(Helpers.getString(R.string.log_info), response != null ? response.toString() : null);
                Helpers.shared().setDepartmentListFromJSON(response);
                requestCallback.callback(true);
            }
        }).execute();

    }
}
