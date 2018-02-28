package com.lieverandiver.thesisproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.lieverandiver.thesisproject.R.id.textView;
import static com.lieverandiver.thesisproject.R.id.view_schedule;

/**
 * Created by Verlie on 9/5/2017.
 */

public class Activity_Viewing extends AppCompatActivity {

    private TextView textViewName;
    private  TextView textViewDate;
    private TextView textViewTotal;
    private ImageView imageViewBackButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing);
        imageViewBackButton = (ImageView)findViewById(R.id.activity_viewing_backbutton);
        textViewName = (TextView)findViewById(R.id.activity_viewing_name);
        textViewDate = (TextView)findViewById(R.id.activity_viewing_date);
        textViewTotal = (TextView)findViewById(R.id.activity_viewing_total);

        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                        finish();

            }
        });
    }

}


