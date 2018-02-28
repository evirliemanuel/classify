package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.AttendanceResult;
import com.remswork.project.alice.model.Student;

import java.util.List;

public class AttendanceResultAdapter extends RecyclerView
        .Adapter<AttendanceResultAdapter.SimpleattendanceViewHolder> {

    private LayoutInflater layoutInflater;
    private List<AttendanceResult> resultList;
    private Context context;

    public AttendanceResultAdapter(Context context, List<AttendanceResult> resultList) {
        layoutInflater = LayoutInflater.from(context);
        this.resultList = resultList;
    }

    @Override
    public SimpleattendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_attendance_input_adapter, parent, false);
        return new SimpleattendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleattendanceViewHolder holder, int position) {
        holder.setView(resultList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class SimpleattendanceViewHolder extends RecyclerView.ViewHolder {
        private TextView txName;
        private TextView txStatus;
        private TextView txInit;


        SimpleattendanceViewHolder(View itemView) {
            super(itemView);
            txName = (TextView) itemView.findViewById(R.id.caia_name);
            txStatus = (TextView) itemView.findViewById(R.id.caia_status);
            txInit = (TextView) itemView.findViewById(R.id.caia_init);
        }

        void setView(final AttendanceResult result, final int position) {
            if (result != null) {
                Student student = result.getStudent();
                String name = String.format("%s, %s %s.",
                        student.getLastName(),
                        student.getFirstName(),
                        student.getMiddleName().substring(0, 1)
                );
                String status = "";
                String init = student.getFirstName().substring(0, 1);

                switch (result.getStatus()) {
                    case 1:
                        status = "present";
                        break;
                    case 2:
                        status = "absent";
                        break;
                    default:
                        status = "notset";
                }
                txName.setText(name);
                txStatus.setText(status);
                txInit.setText(init);
            }
        }
    }
}
