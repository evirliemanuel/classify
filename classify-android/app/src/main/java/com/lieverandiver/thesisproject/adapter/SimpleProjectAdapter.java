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

public class SimpleProjectAdapter extends RecyclerView
        .Adapter<SimpleProjectAdapter.SimpleProjectHolder> {

    private LayoutInflater layoutInflater;
    private List<ActivityResult> resultList;
    private Context context;
    private ActivityAdapter.OnClickListener onClickListener;

    public SimpleProjectAdapter(Context context, List<ActivityResult> resultList) {
        layoutInflater = LayoutInflater.from(context);
        this.resultList = resultList;
    }

    @Override
    public SimpleProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activitystudent_viewprojectscore_cardview, parent,false);
        return new SimpleProjectHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleProjectHolder holder, int position) {
        holder.setView(resultList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class SimpleProjectHolder extends RecyclerView.ViewHolder {
        private TextView txName;
        private TextView txScore;
        private TextView txInit;


        SimpleProjectHolder(View itemView) {
            super(itemView);
            txName = (TextView) itemView.findViewById(R.id.newid_studentname5);
            txScore = (TextView) itemView.findViewById(R.id.newid_studentscore5);
            txInit = (TextView) itemView.findViewById(R.id.newid_initname5);
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
                txScore.setText(score);
                txInit.setText(init);
            }
        }
    }
}