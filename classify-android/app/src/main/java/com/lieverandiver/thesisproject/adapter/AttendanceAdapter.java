package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Attendance;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Attendance> attendanceList;
    private Context context;
    private OnClickListener onClickListener;

    public AttendanceAdapter(Context context, List<Attendance> attendanceList) {
        layoutInflater = LayoutInflater.from(context);
        this.attendanceList = attendanceList;
        this.context = context;
        if(context instanceof OnClickListener)
            onClickListener = (OnClickListener) context;
    }

    @Override
    public AttendanceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_z_add_attendance_cardview, parent, false);
        AttendanceViewHolder holder = new AttendanceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AttendanceViewHolder holder, int position) {
        Attendance attendance = attendanceList.get(position);
        holder.setView(attendance, position);
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewDate;
        private CardView cardView;

        AttendanceViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.add_cardview_title3);
            textViewDate = (TextView) itemView.findViewById(R.id.add_cardview_date3);
            cardView = (CardView) itemView.findViewById(R.id.add_cardview_cardview3);
        }

        public void setView(final Attendance attendance, int position) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            String title = attendance.getTitle();
                            String date = attendance.getDate();

                            textViewTitle.setText(title);
                            textViewDate.setText(date);
                            cardView.setOnClickListener(new Button.OnClickListener(){

                                @Override
                                public void onClick(View v) {
                                    onClickListener.onClick(attendance, attendance.getId());
                                }
                            });
                        }
                    });
                }
            }).start();
        }
    }

    public interface OnClickListener {
        void onClick(Attendance attendance, long attendanceId);
    }
}