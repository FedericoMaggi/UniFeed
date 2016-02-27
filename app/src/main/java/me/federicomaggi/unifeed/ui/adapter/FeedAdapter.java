package me.federicomaggi.unifeed.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.model.FeedItem;

/**
 * Created by federicomaggi on 26/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class FeedAdapter extends ArrayAdapter<FeedItem> {

        public FeedAdapter(Context context, int resource, ArrayList<FeedItem> departments) {
            super(context, resource, departments);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            View theView = convertView;

            if (theView == null){
                LayoutInflater layoutInflater;
                layoutInflater = LayoutInflater.from(getContext());
                theView = layoutInflater.inflate(R.layout.item_feed_list, null);
            }

            FeedItem feedItem = getItem(position);

            if (feedItem != null) {
                TextView feedItemTitleTextView = (TextView) theView.findViewById(R.id.feed_title);
                TextView feedItemDateTextView = (TextView) theView.findViewById(R.id.feed_date);

                feedItemTitleTextView.setText(feedItem.getTitle());
                feedItemDateTextView.setText(feedItem.getDate());
            }

            return theView;
        }
}
