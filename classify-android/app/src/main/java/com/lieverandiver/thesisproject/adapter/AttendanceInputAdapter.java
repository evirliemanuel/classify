package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lieverandiver.thesisproject.AttendanceInputActivity;
import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Student;

import java.util.List;

public class AttendanceInputAdapter extends RecyclerView.Adapter<AttendanceInputAdapter
        .AttendanceViewHolder> implements AttendanceInputActivity.AttendanceInputListener {

    private LayoutInflater layoutInflater;
    private List<Student> studentList;
    private Context context;
    private final int status[];
    private boolean isSelectAll;
    private boolean doValidate;
    private int errorCount;

    public AttendanceInputAdapter(Context context, List<Student> studentList) {
        layoutInflater = LayoutInflater.from(context);
        this.studentList = studentList;
        this.context = context;
        status = new int[studentList.size()];
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_data_student_input_attendance, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.setView(student, position);
        if (doValidate) {
            if (holder.getStatus() < 1)
                holder.isSuccess(false);
            else
                holder.isSuccess(true);
        } else
            holder.markAllAsPresent(isSelectAll);

        if (position == studentList.size() - 1)
            doValidate = false;
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public int getStatus(int position) {
        return status[position];
    }

    @Override
    public void onValidate() {
        errorCount = 0;
        doValidate = true;
        notifyDataSetChanged();
    }

    public int getErrorCount() {
        int errorCount = 0;
        for(int i=0;i < status.length; i++) {
            if (status[i] == 0) errorCount += 1;
        }
        return errorCount;
    }

    @Override
    public void onSelect(boolean isSelected) {
        doValidate = false;
        isSelectAll = isSelected;
        notifyDataSetChanged();
    }

    class AttendanceViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout frameLayoutAbsent;
        private ToggleButton toggleButtonAbsent;
        private ToggleButton toggleButtonPresent;
        private TextView txName;
        private LinearLayout laStatus;
        private Student student;

        AttendanceViewHolder(View itemView) {
            super(itemView);
            txName = (TextView) itemView.findViewById(R.id.attendance_input_studentname);
            laStatus = (LinearLayout) itemView.findViewById(R.id.layout_attendance_la);

            frameLayoutAbsent = (FrameLayout) itemView.findViewById(R.id.absent_option);
            toggleButtonAbsent = (ToggleButton) itemView.findViewById(R.id.btn_absent);
            toggleButtonPresent = (ToggleButton) itemView.findViewById(R.id.btn_present);
        }

        public void setView(final Student student, final int position) {
            this.student = student;
            String name = student.getLastName() + " " + student.getFirstName() +
                    "," + student.getMiddleName().substring(0, 1) + ".";
            txName.setText(name);

            frameLayoutAbsent.setVisibility(View.GONE);

            toggleButtonAbsent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        frameLayoutAbsent.setVisibility(View.VISIBLE);
                        toggleButtonPresent.setChecked(false);
                        status[position] = 2;
                    } else {
                        frameLayoutAbsent.setVisibility(View.GONE);
                    }
                }
            });

            toggleButtonPresent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggleButtonAbsent.setChecked(false);
                        status[position] = 1;
                    }
                }
            });
        }

        void markAllAsPresent(boolean isTrue) {
            toggleButtonPresent.setChecked(isTrue);
        }

        int getStatus() {
            return toggleButtonPresent.isChecked() ? 1 : toggleButtonAbsent.isChecked() ? 2 : 0;
        }

        void isSuccess(boolean isSuccess) {
            if (isSuccess)
                laStatus.setBackgroundColor(context.getResources().getColor(R.color.colorLightSuccess));
            else
                laStatus.setBackgroundColor(context.getResources().getColor(R.color.colorLightDanger));
        }
    }

}