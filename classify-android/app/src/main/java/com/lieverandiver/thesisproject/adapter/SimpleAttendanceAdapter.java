package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.ActivityResult;
import com.remswork.project.alice.model.Student;

import java.util.List;

/**
 * Created by Verlie on 9/7/2017.
 */
public class SimpleAttendanceAdapter extends RecyclerView
        .Adapter<SimpleAttendanceAdapter.SimpleAttendanceHolder> {

    private LayoutInflater layoutInflater;
    private List<ActivityResult> resultList;
    private Context context;
    private ActivityAdapter.OnClickListener onClickListener;

    public SimpleAttendanceAdapter(Context context, List<ActivityResult> resultList) {
        layoutInflater = LayoutInflater.from(context);
        this.resultList = resultList;
    }

    @Override
    public SimpleAttendanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activitystudent_viewattendance_cardview, parent,false);
        return new SimpleAttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleAttendanceHolder holder, int position) {
        holder.setView(resultList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class SimpleAttendanceHolder extends RecyclerView.ViewHolder {
        private TextView txName;
        private TextView txInit;


        SimpleAttendanceHolder(View itemView) {
            super(itemView);
            txName = (TextView) itemView.findViewById(R.id.newid_studentname3);
            txInit = (TextView) itemView.findViewById(R.id.newid_initname3);
        }

        void setView(final ActivityResult result, final int position) {
            if(result != null) {
                Student student = result.getStudent();
                String name = String.format("%s %s. %s",
                        student.getFirstName(),
                        student.getMiddleName().substring(0, 1),
                        student.getLastName());
                String score = String.valueOf(result.getScore());
                String init = student.getFirstName().substring(0, 1);

                txName.setText(name);
                txInit.setText(init);
            }
        }
    }
}