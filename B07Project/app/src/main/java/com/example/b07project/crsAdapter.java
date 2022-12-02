package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;


public class crsAdapter  extends ArrayAdapter<Course> {
    private int resource;

    private static class ViewHolder{
        TextView code;
        TextView sessions;
    }
    public crsAdapter(Context context, int resource, List<Course> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course crs = getItem(position);
        ViewHolder viewHolder;
        //initialize viewholder if convertview is invoke for the first time

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent,false);

            viewHolder = new ViewHolder();
            viewHolder.code =(TextView)convertView.findViewById(R.id.crscode);
            viewHolder.sessions =(TextView)convertView.findViewById(R.id.crsession);
            //cache the viewholder object
            convertView.setTag(convertView);}
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.code.setText(crs.code);
       // viewHolder.sessions.setText(crs.sessions);

        return convertView;

    }

}
