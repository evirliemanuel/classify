package io.inteliedoit.student_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    private Button btn_user_back;
    private TextView txt_user_studentname;
    private TextView txt_user_studentnumber;
    private TextView txt_user_enrolled;
    private TextView txt_user_classcount;
    private RecyclerView recyclerview_user_classlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }
}
