package me.federicomaggi.unifeed.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.Helpers;
import me.federicomaggi.unifeed.model.DepartmentItem;
import me.federicomaggi.unifeed.model.FeedItem;
import me.federicomaggi.unifeed.ui.fragment.FeedListFragment;
import me.federicomaggi.unifeed.ui.fragment.FeedViewerFragment;

public class FeedActivity extends AppCompatActivity implements
        FeedListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        try {
            Intent intent = getIntent();

            DepartmentItem departmentItem = DepartmentItem.unparse(new JSONObject(intent.getExtras().getString(Helpers.DEPARMENT_KEY)));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.feed_fragment_container, FeedListFragment.newInstance(departmentItem))
                    .commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFeedFragmentInteraction(FeedItem feedItem) {

        FeedViewerFragment feedViewerFragment = FeedViewerFragment.newInstance(feedItem);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.feed_fragment_container, feedViewerFragment)
                .addToBackStack(FeedViewerFragment.class.getName())
                .commit();
    }
}
