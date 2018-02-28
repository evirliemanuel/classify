package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * Created by Verlie on 8/31/2017.
 */

public class Activity_Class_View_Assignment extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewDate;
    private TextView textViewTotal;
    private RecyclerView recyclerViewAssignment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view_assignment);


        textViewName = (TextView)findViewById(R.id.name_assignment);
        textViewDate = (TextView)findViewById(R.id.date_assignment);
        textViewTotal = (TextView)findViewById(R.id.totalscore_assignment);
        recyclerViewAssignment = (RecyclerView)findViewById(R.id.recyclerview_assignment);
    }
}
