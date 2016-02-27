package me.federicomaggi.unifeed.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.apache.http.message.HeaderValueParser;

import java.util.ArrayList;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.Helpers;
import me.federicomaggi.unifeed.controller.interfaces.RequestCallback;
import me.federicomaggi.unifeed.model.DepartmentItem;
import me.federicomaggi.unifeed.model.FeedItem;
import me.federicomaggi.unifeed.ui.adapter.FeedAdapter;

/**
 * Created by federicomaggi on 26/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class FeedListFragment extends Fragment {

    private OnFeedListFragmentInteractionListener mListener;

    private static final String DEPARTMENT_NAME_ARG = "dep_name_arg";
    private static final String DEPARTMENT_ACRONYM_ARG = "dep_acronym_arg";
    private static final String DEPARTMENT_URL_ARG = "dep_url_arg";

    private View mRootView;
    private ListView mListView;

    private String mFeedUrl;
    private String mFeedName;
    private String mFeedAcronym;

    private ArrayList<FeedItem> mFeedList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param department The department used to download feeds
     * @return A new instance of fragment DepartmentListFragment.
     */
    public static FeedListFragment newInstance(DepartmentItem department) {
        FeedListFragment feedListFragment = new FeedListFragment();

        Bundle bundleArgs = new Bundle();
        bundleArgs.putString(DEPARTMENT_NAME_ARG, department.getName());
        bundleArgs.putString(DEPARTMENT_ACRONYM_ARG, department.getAcronym());
        bundleArgs.putString(DEPARTMENT_URL_ARG, department.getUrl());
        feedListFragment.setArguments(bundleArgs);

        return feedListFragment;
    }

    public FeedListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFeedUrl = getArguments().getString(DEPARTMENT_URL_ARG);
            mFeedName = getArguments().getString(DEPARTMENT_NAME_ARG);
            mFeedAcronym = getArguments().getString(DEPARTMENT_ACRONYM_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_feed_list, container, false);
        mListView = (ListView) mRootView.findViewById(R.id.feed_list);

        mFeedList = Helpers.shared().getFeedItemList(mFeedAcronym);

        if (mFeedList != null) {
            fillListView();
            return mRootView;
        }

        Helpers.shared().communicationHandler.downloadFeed(mFeedUrl, mFeedAcronym, new RequestCallback() {

            @Override
            public void callback(Boolean success) {

                if (!success) {
                    Helpers.showAlert(1);
                    return;
                }

                Log.d(Helpers.getString(R.string.log_debug), "FEED DOWNLOADED");
                mFeedList = Helpers.shared().getFeedItemList(mFeedAcronym);
                fillListView();
            }
        });

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.i(Helpers.getString(R.string.log_info), "FeedListFragment Attached");
        try {
            mListener = (OnFeedListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFeedListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void fillListView(){

        FeedAdapter feedAdapter = new FeedAdapter(getActivity(), R.layout.item_feed_list, mFeedList);
        mListView.setAdapter(feedAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FeedItem feedItem = (FeedItem) mListView.getItemAtPosition(position);
                mListener.onFeedFragmentInteraction(feedItem);
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFeedListFragmentInteractionListener {
        void onFeedFragmentInteraction(FeedItem feedItem);
    }
}