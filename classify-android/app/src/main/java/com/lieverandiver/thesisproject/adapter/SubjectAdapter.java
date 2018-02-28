package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Subject;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private Context context;
    private List<Subject> subjectList;
    private LayoutInflater layoutInflater;
    private OnViewClickListener onViewClickListener;

    public SubjectAdapter(Context context, List<Subject> subjectList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.subjectList = subjectList;
        onViewClickListener = (OnViewClickListener) context;
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.teacher_cardview_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.setView(subject, position);
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }


    class SubjectViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView textViewSubject;
        private TextView textViewDescription;
        private LinearLayout cardView;
        private Subject subject;

        SubjectViewHolder(View itemView) {
            super(itemView);
            textViewSubject = (TextView) itemView.findViewById(
                    R.id.adapter_subject_name);
            textViewDescription = (TextView) itemView.findViewById(
                    R.id.adapter_subject_desc);
            cardView = (LinearLayout) itemView.findViewById(R.id.f_data_cardview_subject_name_1);
            cardView.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.i("vlivk" ,"adadad");
                    onViewClickListener.viewSubject(subject);
                }
            });
        }

        void setView(final Subject subject, final int position) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        SubjectViewHolder.this.subject = subject;
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                textViewSubject.setText(subject.getName());
                                textViewDescription.setText(subject.getDescription());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }

    public interface OnViewClickListener {
        void viewSubject(Subject subject);
    }
}
