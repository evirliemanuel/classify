package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

/**
 * Created by Verlie on 9/5/2017.
 */

public class InputAttendance_Cardview extends AppCompatActivity {
    private FrameLayout frameLayoutAbsent;
    private ToggleButton toggleButtonAbsent;
    private ToggleButton toggleButtonPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview_data_student_input_attendance);

        init();
    }
    public void init(){
        frameLayoutAbsent = (FrameLayout)findViewById(R.id.absent_option);
        toggleButtonAbsent = (ToggleButton)findViewById(R.id.btn_absent);
        toggleButtonPresent = (ToggleButton)findViewById(R.id.btn_present);

        frameLayoutAbsent.setVisibility(View.GONE);

        toggleButtonAbsent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    frameLayoutAbsent.setVisibility(View.VISIBLE);
                } else {
                    frameLayoutAbsent.setVisibility(View.GONE);
                }
            }
        });

        toggleButtonPresent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                frameLayoutAbsent.setVisibility(View.GONE);
            }
            });


    }
    }
