package me.federicomaggi.unifeed.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.controller.Helpers;
import me.federicomaggi.unifeed.controller.interfaces.RequestCallback;
import me.federicomaggi.unifeed.model.DepartmentItem;
import me.federicomaggi.unifeed.ui.adapter.DepartmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DepartmentListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DepartmentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
    // TODO: Rename and change types and number of parameters
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

                if (success) {
                    ArrayList<DepartmentItem> list = Helpers.shared().getDepartmentList();
                    DepartmentAdapter adapter = new DepartmentAdapter(getActivity(), R.layout.department_list_item, list);
                    mListView.setAdapter(adapter);
                    return;
                }

                Helpers.showAlert(0);
            }
        });

        return mRootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
