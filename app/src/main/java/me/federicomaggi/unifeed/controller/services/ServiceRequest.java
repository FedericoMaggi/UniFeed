package me.federicomaggi.unifeed.controller.services;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.Helpers;
import me.federicomaggi.unifeed.controller.interfaces.ServiceCallback;

/**
 * Created by federicomaggi on 25/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class ServiceRequest extends AsyncTask<Void,Void,JSONObject> {

    private String requestUri;
    private ServiceCallback serviceCallback;

    public ServiceRequest(String requestUri, ServiceCallback serviceCallback) {
        this.requestUri = requestUri;
        this.serviceCallback = serviceCallback;
    }

    @Override
    protected void onPreExecute(){
        // Do nothing
    }

    @Override
    protected JSONObject doInBackground(Void... v) {
        JSONObject response;
        StringBuilder builder = new StringBuilder();
        String aux = "";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(requestUri).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader bfr = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((aux = bfr.readLine()) != null){
                builder.append(aux);
            }

            String responseString = builder.toString();

            Log.d(Helpers.getString(R.string.log_debug), responseString);
            response = (JSONObject) new JSONTokener(responseString).nextValue();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        serviceCallback.callback(result);
    }
}
