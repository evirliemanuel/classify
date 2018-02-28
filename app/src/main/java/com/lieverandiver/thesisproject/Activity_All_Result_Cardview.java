//package com.lieverandiver.thesisproject;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//
//import android.widget.CompoundButton;
//import android.widget.LinearLayout;
//import android.widget.ToggleButton;
//
//import static com.lieverandiver.thesisproject.R.id.result_cardview_cancel;
//import static com.lieverandiver.thesisproject.R.id.result_cardview_delete;
//import static com.lieverandiver.thesisproject.R.id.result_cardview_save;
//
///**
// * Created by Verlie on 9/5/2017.
// */
//
//public class Activity_All_Result_Cardview extends AppCompatActivity implements View.OnClickListener {
//    private LinearLayout linearLayoutOption;
//    private ToggleButton btnOption;
//    private Button btnSave;
//    private Button btnCancel;
//    private Button btnDelete;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_result_cardview);
//
//        init();
//    }
//    public void init(){
//        linearLayoutOption = (LinearLayout) findViewById(R.id.result_linearoption);
//        btnOption = (ToggleButton) findViewById(R.id.result_option);
//        btnSave = (Button)findViewById(result_cardview_save);
//        btnCancel = (Button)findViewById(result_cardview_cancel);
//        btnDelete = (Button)findViewById(result_cardview_delete);
//
//        linearLayoutOption.setVisibility(View.GONE);
//        btnSave.setOnClickListener(this);
//        btnCancel.setOnClickListener(this);
//        btnDelete.setOnClickListener(this);
//
//
//        btnOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//                    linearLayoutOption.setVisibility(View.VISIBLE);
//                } else {
//                    linearLayoutOption.setVisibility(View.GONE);
//                }
//            }
//        });
//
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case result_cardview_save:
//                linearLayoutOption.setVisibility(View.GONE);
//                break;
//            case result_cardview_cancel:
//                linearLayoutOption.setVisibility(View.GONE);
//
//                break;
//            case result_cardview_delete:
//
//
//                break;
//        }
//    }
//}
