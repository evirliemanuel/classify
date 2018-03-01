package io.inteliedoit.student_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ClassDetailActivity extends AppCompatActivity {

    private Button btn_class_back;
    private TextView txt_class_teachername;
    private  TextView txt_class_subjectname;
    private TextView txt_class_description;
    private TextView txt_class_sched1;
    private TextView txt_class_sched2;
    private ImageView img_class_sched1;
    private ImageView img_class_sched2;
    private RecyclerView recyclerview_class_gradingcriteria;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classdetail);
    }
}
