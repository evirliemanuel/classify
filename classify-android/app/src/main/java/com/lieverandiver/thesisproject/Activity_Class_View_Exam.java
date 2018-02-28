package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * Created by Verlie on 8/31/2017.
 */

public class Activity_Class_View_Exam extends AppCompatActivity {
    private TextView textViewName;
    private TextView textViewDate;
    private TextView textViewTotal;
    private RecyclerView recyclerViewExam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_view_exam);


        textViewName = (TextView)findViewById(R.id.btn_backaddexam);
        textViewDate = (TextView)findViewById(R.id.date_exam);
        textViewTotal = (TextView)findViewById(R.id.totalscore_exam);
        recyclerViewExam = (RecyclerView)findViewById(R.id.recyclerview_exam);
    }

}
