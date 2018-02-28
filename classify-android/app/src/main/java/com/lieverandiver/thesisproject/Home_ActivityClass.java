package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Verlie on 8/30/2017.
 */

@Deprecated
public class Home_ActivityClass extends AppCompatActivity{

   private TextView txtViewSubjectName;
    private TextView txtViewSectionName;
    private TextView txtViewDepName;
    private TextView txtViewSemName;
    private CardView viewStudent;
    private CardView viewSchedule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activty_view_class);

        viewSchedule = (CardView)findViewById(R.id.view_schedule);
        viewSchedule = (CardView)findViewById(R.id.view_student);
        txtViewSubjectName=(TextView)findViewById(R.id.txtv_subjectname);
        txtViewSectionName=(TextView)findViewById(R.id.txtv_section);
        txtViewDepName=(TextView)findViewById(R.id.txtv_dept);
        txtViewSemName=(TextView)findViewById(R.id.txtv_sem);

    }
}
