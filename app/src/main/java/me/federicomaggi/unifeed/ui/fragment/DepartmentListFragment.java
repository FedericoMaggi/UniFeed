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

import java.util.ArrayList;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.Helpers;
import me.federicomaggi.unifeed.controller.interfaces.RequestCallback;
import me.federicomaggi.unifeed.model.DepartmentItem;
import me.federicomaggi.unifeed.ui.adapter.DepartmentAdapter;


public class DepartmentListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private View mRootView;
    private ListView mListView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DepartmentListFragment.
     */
    public static DepartmentListFragment newInstance() {
        return new DepartmentListFragment();
    }

    public DepartmentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_department_list, container, false);
        mListView = (ListView) mRootView.findViewById(R.id.department_list);

        Helpers.shared().retrieveDepartmentList(new RequestCallback() {

            @Override
            public void callback(Boolean success) {

                Log.i(Helpers.getString(R.string.log_info),"Back in Fragment");

                if (!success) {
                    Helpers.showAlert(0);
                    return;
                }

                ArrayList<DepartmentItem> list = Helpers.shared().getDepartmentList();
                DepartmentAdapter adapter = new DepartmentAdapter(getActivity(), R.layout.item_department_list, list);
                mListView.setAdapter(adapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        DepartmentItem departmentItem = (DepartmentItem) mListView.getItemAtPosition(position);
                        mListener.onDepartmentFragmentInteraction(departmentItem);
                    }
                });
            }
        });

        return mRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.i(Helpers.getString(R.string.log_info), "DepartmentListFragment Attached");
        try {
            mListener = (OnFragmentInteractionListener) activity;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onDepartmentFragmentInteraction(DepartmentItem departmentItem);
    }
}
