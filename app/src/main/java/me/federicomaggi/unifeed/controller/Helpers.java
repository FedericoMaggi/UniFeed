package me.federicomaggi.unifeed.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.http.HeaderElement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.interfaces.RequestCallback;
import me.federicomaggi.unifeed.model.DepartmentItem;

/**
 * Created by federicomaggi on 25/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class Helpers {

    // Constants
    public static final String DEPARMENT_FILE = "departments_file";
    public static final String DEPARMENT_KEY = "departments_key";

    // Helper Singleton instance
    private static Helpers instance;

    // App utilities
    private static Context appContext;
    private static JSONObject alerts;
    private ArrayList<DepartmentItem> mDeparmentList;


    // Communication and services handlers
    public CommunicationHandler communicationHandler;
    private ProgressDialog mProgressDialog;



    public static Helpers shared(){
        if (instance == null)
            instance = new Helpers();
        return instance;
    }

    private Helpers(){

        communicationHandler = new CommunicationHandler();
        mDeparmentList = new ArrayList<>();

        try{
            alerts = new JSONObject()
                    .put("0", new JSONObject().put("title",getString(R.string.error)).put("message",getString(R.string.nointernet)).put("cancel",getString(R.string.ok)));

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static String getString(int resId) {

        return appContext.getString(resId);
    }

    public Context getAppContext(){
        return appContext;
    }

    public static void setAppContext(Context newContext){
         appContext = newContext;
    }

    public void setProgressDialog(String theMessage) {
        if( mProgressDialog == null )
            mProgressDialog = new ProgressDialog(getAppContext());

        mProgressDialog.setTitle(getString(R.string.loading));

        if (theMessage == null)
            theMessage = getString(R.string.defaultloadingmessage);

        mProgressDialog.setMessage(theMessage);
        mProgressDialog.show();
    }

    public void removeProgressDialog() {
        if( mProgressDialog == null )
            return;

        if( mProgressDialog.isShowing() ) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public static AlertDialog showAlert(int id) {
        JSONObject alert = alerts.optJSONObject(id + "");
        if( alert == null ) {
            try {
                alert = new JSONObject().put("title", "Impossibile collegarsi al server").put("message", "Ritenta più tardi").put("cancel", "Ok");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        assert alert != null;
        return new AlertDialog.Builder(appContext).setTitle(alert.optString("title")).setMessage(alert.optString("message")).show();
    }

    public ArrayList<DepartmentItem> getDepartmentList(){
        return mDeparmentList;
    }

    public void retrieveDepartmentList(final RequestCallback finalCallback){

        Log.i(getString(R.string.log_info), "Started departements retrieval");

        // Already set -> return it.
        if (mDeparmentList != null){
            Log.d(getString(R.string.log_debug), "Department List:" + mDeparmentList.toString());
            finalCallback.callback(true);
        }

        // Not set -> get it from SharedPreferecens
    /*
        JSONObject savedObj = getSavedObj(DEPARMENT_KEY, DEPARMENT_FILE);
        if (savedObj != null){
            // decode obj in list
            Log.d(getString(R.string.log_debug), "Department List:" + mDeparmentList.toString());
            finalCallback.callback(true);
        }
    */

        Log.i(getString(R.string.log_info), "Started departements download");

        // Not in SharedPreferences -> Download it
        communicationHandler.downloadDeparments(new RequestCallback() {

            @Override
            public void callback(Boolean success) {

                Log.i(getString(R.string.log_info), "Download completed: " + success.toString());
                finalCallback.callback(success);
            }
        });
    }

    public void setDepartmentListFromJSON(JSONObject departments){
        instance.saveObj(Helpers.DEPARMENT_KEY, departments, Helpers.DEPARMENT_FILE);

        JSONArray depsArray = new JSONArray();
        JSONObject temp = new JSONObject();

        try {
            depsArray = departments.getJSONArray("departments");

            for(int i = 0; i < depsArray.length(); i++){
                temp = depsArray.getJSONObject(i);

                mDeparmentList.add(new DepartmentItem(temp.getString("name"),temp.getString("url"),temp.getString("acronym")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void saveObj(String key, JSONObject obj, String file) {
        SharedPreferences sp = appContext.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, obj.toString());
        editor.apply();
    }

    public JSONObject getSavedObj(String key, String file) {
        SharedPreferences sp = appContext.getSharedPreferences(file, Context.MODE_PRIVATE);
        if(!keyExist(key, file))
            return null;
        try {
            return (JSONObject) new JSONTokener(sp.getString(key,null)).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(getString(R.string.log_info), "Error loading saved object " + e.toString());
            return null;
        }
    }

    public boolean keyExist(String key, String file) {
        SharedPreferences sp = appContext.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sp.contains(key);
    }
}
