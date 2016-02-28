package me.federicomaggi.unifeed.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.Helpers;
import me.federicomaggi.unifeed.model.DepartmentItem;
import me.federicomaggi.unifeed.ui.fragment.DepartmentListFragment;
import me.federicomaggi.unifeed.ui.fragment.NavigationDrawerFragment;


public class MainActivity extends AppCompatActivity implements
        DepartmentListFragment.OnFragmentInteractionListener,
        NavigationDrawerFragment.NavigationDrawerCallbacks{

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Helpers.setAppContext(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, DepartmentListFragment.newInstance())
                .commit();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onDepartmentFragmentInteraction(DepartmentItem departmentItem) {

        try {

            Intent intent = new Intent(this, FeedActivity.class);
            intent.putExtra(Helpers.DEPARMENT_KEY, departmentItem.parse());
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();

    }
}
