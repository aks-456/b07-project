package com.example.b07project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DTC_RecyclerViewAdapter extends RecyclerView.Adapter<DTC_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<DisplayTakenCourse> takenCourseList;


    public DTC_RecyclerViewAdapter(Context context, ArrayList<DisplayTakenCourse> takenCourseList) {
        this.context = context;
        this.takenCourseList = takenCourseList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this is where we inflate the layout( giving a look to our row)
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.taken_course_recycler_view_row, parent, false);
        return new DTC_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //assigning values to the views we created in the recycler_view layout file
        // based on the position of the recycler view
        holder.courseCode.setText(takenCourseList.get(position).getCourseCode());
        holder.courseName.setText(takenCourseList.get(position).getCourseName());


    }


    @Override
    public int getItemCount() {
        //how many item do you have in total
        //so that this is going to help the onbingding process
        return takenCourseList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //grabbing the views from our recycler_view_row layout file
        //kinda like in the oncreate method

        ImageView imageview;
        TextView courseCode;
        TextView courseName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageview = itemView.findViewById(R.id.TTCR_image);
            courseCode = itemView.findViewById(R.id.TTCR_crsCode);
            courseName = itemView.findViewById(R.id.TTCR_crsName);

        }
    }
}