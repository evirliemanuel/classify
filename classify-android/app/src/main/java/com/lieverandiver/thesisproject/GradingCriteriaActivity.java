package com.lieverandiver.thesisproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.TextView;

import static com.lieverandiver.thesisproject.R.id.total_percent;

public class GradingCriteriaActivity extends AppCompatActivity {

    private CardView cvAdd;
    private TextView txTotalPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_grading_criteria);
        cvAdd = (CardView) findViewById(R.id.fragment_slidebar_subject_add_subject);
        txTotalPercent = (TextView) findViewById(R.id.total_percent);

        Log.i("TAGGGGGMO", txTotalPercent.getText().toString().split("%")[0]);
    }
}
