package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Verlie on 9/14/2017.
 */

public class StudentAccountActivity_Profile extends AppCompatActivity{

    private TextView textViewName;
    private TextView textViewStudentNumber;
    private RecyclerView recyclerViewSemester;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_account_activity_profile);

        textViewName = (TextView)findViewById(R.id.kios_studentname);
        textViewStudentNumber = (TextView)findViewById(R.id.kios_studentnumber);
        recyclerViewSemester = (RecyclerView)findViewById(R.id.kios_recyclerview_term);

    }
}
