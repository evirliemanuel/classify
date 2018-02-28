package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lieverandiver.thesisproject.ActivityInputActivity;
import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Student;

import java.util.List;
import java.util.Locale;


public class StudentAdapter2 extends RecyclerView.Adapter<StudentAdapter2.StudentAdapterViewHolder>
    implements ActivityInputActivity.InputListener{

    private List<Student> studentList;
    private Context context;
    private LayoutInflater layoutInflater;
    private int score[];
    private int totalScore;
    private boolean doValidate;
    private int count;
    private int error[];

    public StudentAdapter2(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        score = new int[studentList.size()];
        error = new int[studentList.size()];
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public StudentAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_z_input_activity_cardview, parent, false);
        return new StudentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentAdapterViewHolder holder, int position) {
        count += 1;
        Student student = studentList.get(position);
        holder.setView(student, position);

        if(doValidate) {
               if(holder.getScore() > totalScore || holder.getScore() < 0) {
                   holder.setStatus(false);
               }else {
                   holder.setStatus(true);
               }
        }if(count == studentList.size()) {
            doValidate = false;
            count = 0;
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    @Override
    public void onValidate(boolean doValidate) {
        this.totalScore = totalScore;
        this.doValidate = doValidate;
        notifyDataSetChanged();
    }

    @Override
    public boolean isNoError() {
        int errorNumber = 0;
        for(int i : error) {
            errorNumber += i;
            Log.i("ERROR" , i + "");
        }
        return errorNumber < 1;
    }

    @Override
    public int getScore(int index) {
        return score[index];
    }

    @Override
    public void setTotalItem(int totalScore) {
        this.totalScore = totalScore;
        for(int i=0; i<studentList.size();i++){
            if(score[i] > totalScore)
                error[i] = 1;
            else
                error[i] = 0;
        }
    }

    public class StudentAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView studentImage;
        private TextView studentDetail;
        private EditText editText;
        private Student student;
        private int position;
        private LinearLayout layout;

        StudentAdapterViewHolder(View itemView) {
            super(itemView);
            studentImage = (ImageView) itemView.findViewById(R.id.f_data_student_profile);
            studentDetail = (TextView) itemView.findViewById(R.id.input_cardview_name1);
            editText = (EditText) itemView.findViewById(R.id.input_cardview_score1);
            layout = (LinearLayout) itemView.findViewById(R.id.input_cardview_layout1);
        }

        void setView(final Student student, final int position) {
            this.student = student;
            this.position = position;
            String display = String.format(Locale.ENGLISH, "%s, %s %s",
                    student.getLastName(),
                    student.getFirstName(),
                    student.getMiddleName().substring(0, 1));
            studentDetail.setText(display);
            editText.setText(score[position] + "");
            editText.addTextChangedListener(textWatcher);
        }

        public Student getStudent() {
            return student;
        }

        public int getScore() {
            return Integer.parseInt(!editText.getText().toString().equals("") ? editText.getText().toString() : "0");
        }

        public void setStatus(boolean isSuccess) {
            if(isSuccess)
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorLightSuccess));
            else
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorLightDanger));
            studentDetail.setTextColor(context.getResources().getColor(R.color.colorMoca2));
        }

        private TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                score[position] = getScore();
                if(score[position] > totalScore)
                    error[position] = 1;
                else
                    error[position] = 0;
            }
        };
    }
}
