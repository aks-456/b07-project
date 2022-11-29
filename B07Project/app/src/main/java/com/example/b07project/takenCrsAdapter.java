package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class takenCrsAdapter extends ArrayAdapter<TakenCrs> {
    private int resource;

    private static class ViewHolder{
        TextView crscode;
        TextView crsname;
    }
    public takenCrsAdapter(Context context, int resource, List<TakenCrs> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TakenCrs crs = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent);
        }

        TextView crscode =(TextView)convertView.findViewById(R.id.crsCode);
        TextView crsname =(TextView)convertView.findViewById(R.id.crsName);

        crscode.setText(crs.crscode);
        crsname.setText(crs.crsname);

        return convertView;

    }

}
