package com.lieverandiver.thesisproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityChangePassword extends AppCompatActivity implements View.OnClickListener {

    private EditText text1;
    private EditText text2;

    private Button button;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_changepassword);

        text1 = findViewById(R.id.activity_changepassword_text1);
        text2 = findViewById(R.id.activity_changepassword_text2);
        button = findViewById(R.id.activityt_changepassword_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(text1.getText())) {
            text1.setError("Enter new password");
            return;
        }
        if (TextUtils.isEmpty(text2.getText())) {
            text2.setError("Re-enter new password");
            return;
        }
        if (!text1.getText().toString().equals(text2.getText().toString())) {
            text2.setError("Not match");
            return;
        }
        Log.i("OOOOOOO", "Works");
    }
}
