package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

/**
 * Created by Verlie on 9/13/2017.
 */
public class ActivityGradeAttendance extends AppCompatActivity {

    private RecyclerView recyclerViewLGrades;
    private Button buttonGrades;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_grades_factor);
        init();
    }

    public void init(){
        buttonGrades = (Button)findViewById(R.id.result_backa);
        recyclerViewLGrades = (RecyclerView)findViewById(R.id.result_recyclera);

    }


}


