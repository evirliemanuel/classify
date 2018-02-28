package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.Subject;

import java.util.List;
import java.util.Locale;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentAdapterViewHolder> {

    private List<Student> studentList;
    private Context context;
    private LayoutInflater layoutInflater;

    public StudentAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public StudentAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_data_student_name, parent, false);
        return new StudentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentAdapterViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.setView(student, position);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView studentDetail;
        private  TextView txInit;

        StudentAdapterViewHolder(View itemView) {
            super(itemView);
            studentDetail = (TextView) itemView.findViewById(R.id.f_data_studentname_text);
            txInit = (TextView) itemView.findViewById(R.id.result_cardview_init);
        }

        void setView(final Student student, final int position) {
            String display = String.format(Locale.ENGLISH, "%s, %s %s",
                    student.getLastName(),
                    student.getFirstName(),
                    student.getMiddleName().substring(0, 1));
            studentDetail.setText(display);

            String init = student.getLastName().substring(0, 1);
            txInit.setText(init);
        }



    }
}
