package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;

import java.util.List;

/**
 * Created by Rem-sama on 9/3/2017.
 */

@Deprecated
public class CriteriaAdapter extends RecyclerView.Adapter<CriteriaAdapter.CriteriaViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> dataList;

    public CriteriaAdapter(Context context, List<String> dataList) {
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public CriteriaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.criteria_layout_view, parent, false);
        CriteriaViewHolder holder = new CriteriaViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(CriteriaAdapter.CriteriaViewHolder holder, int position) {
        String key = dataList.get(position);
        holder.setView(key, position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CriteriaViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textTitle;
        private TextView textContent;


        public CriteriaViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.criteria_imageview_u1);
            textTitle = (TextView) itemView.findViewById(R.id.criteria_name_u1);
            textContent = (TextView) itemView.findViewById(R.id.criteria_per_u1);
        }

        public void setView(final String key, final int position) {
            if(position == 0) {
                imageView.setImageResource(R.drawable.icon_activity);
                textTitle.setText("Activity");
                textContent.setText(key);
            }else if(position == 1) {
                imageView.setImageResource(R.drawable.icon_assignment);
                textTitle.setText("Assignment");
                textContent.setText(key);
            }else if(position == 2) {
                imageView.setImageResource(R.drawable.icon_attendance);
                textTitle.setText("Attendance");
                textContent.setText(key);
            }else if(position == 3) {
                imageView.setImageResource(R.drawable.icon_exam);
                textTitle.setText("Exam");
                textContent.setText(key);
            }else if(position == 4) {
                imageView.setImageResource(R.drawable.icon_project);
                textTitle.setText("Project");
                textContent.setText(key);
            }else {
                imageView.setImageResource(R.drawable.icon_quiz);
                textTitle.setText("Quiz");
                textContent.setText(key);
            }
        }
    }
}
