package me.federicomaggi.unifeed.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.model.FeedItem;

/**
 * Created by federicomaggi on 26/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class FeedViewerFragment extends Fragment{

    private static final String FEED_TITLE_ARG = "feed_title_arg";
    private static final String FEED_PUBDATE_ARG = "feed_pubdate_arg";
    private static final String FEED_DESCRIPTION_ARG = "feed_description_arg";
    private static final String FEED_LINK_ARG = "feed_link_arg";

    private String mFeedTitle;
    private String mFeedPubDate;
    private String mFeedDescription;
    private String mFeedLink;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param feedItem The feed item to show
     * @return A new instance of fragment FeedViewerFragment.
     */
    public static FeedViewerFragment newInstance(FeedItem feedItem) {
        FeedViewerFragment feedViewerFragment = new FeedViewerFragment();

        Bundle bundleArgs = new Bundle();
        bundleArgs.putString(FEED_TITLE_ARG, feedItem.getTitle());
        bundleArgs.putString(FEED_DESCRIPTION_ARG, feedItem.getDescription());
        bundleArgs.putString(FEED_LINK_ARG, feedItem.getLink());
        bundleArgs.putString(FEED_PUBDATE_ARG, feedItem.getDate());

        feedViewerFragment.setArguments(bundleArgs);

        return feedViewerFragment;
    }

    public FeedViewerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFeedTitle = getArguments().getString(FEED_TITLE_ARG);
            mFeedPubDate = getArguments().getString(FEED_PUBDATE_ARG);
            mFeedDescription = getArguments().getString(FEED_DESCRIPTION_ARG);
            mFeedLink = getArguments().getString(FEED_LINK_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mRootView = inflater.inflate(R.layout.fragment_viewer, container, false);

        TextView feedTitleTextView = (TextView) mRootView.findViewById(R.id.feed_title);
        TextView feedDateTextView = (TextView) mRootView.findViewById(R.id.feed_date);
        TextView feedLinkTextView = (TextView) mRootView.findViewById(R.id.feed_link);
        TextView feedContentTextView = (TextView) mRootView.findViewById(R.id.feed_content);

        feedTitleTextView.setText(mFeedTitle);
        feedDateTextView.setText(mFeedPubDate);
        feedLinkTextView.setText(mFeedLink);
        feedContentTextView.setText(Html.fromHtml(mFeedDescription));

        return mRootView;
    }
}
