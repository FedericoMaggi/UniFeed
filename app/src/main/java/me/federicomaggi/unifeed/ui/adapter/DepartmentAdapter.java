package me.federicomaggi.unifeed.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.federicomaggi.unifeed.R;
import me.federicomaggi.unifeed.model.DepartmentItem;

/**
 * Created by federicomaggi on 25/02/16.
 * © 2016 Federico Maggi. All rights reserved
 */
public class DepartmentAdapter extends ArrayAdapter<DepartmentItem> {

    public DepartmentAdapter(Context context, int resource, ArrayList<DepartmentItem> departments) {
        super(context, resource, departments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View theView = convertView;

        if (theView == null){
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(getContext());
            theView = layoutInflater.inflate(R.layout.department_list_item, null);
        }

        DepartmentItem depItem = getItem(position);

        if (depItem != null) {
            TextView departmentNameTextView = (TextView) theView.findViewById(R.id.department_name);
            departmentNameTextView.setText(depItem.getName() + "["+depItem.getAcronym()+"]");
        }

        return theView;
    }
}
