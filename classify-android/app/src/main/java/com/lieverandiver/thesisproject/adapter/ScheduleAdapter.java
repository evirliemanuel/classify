package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.lieverandiver.thesisproject.helper.ScheduleHelper;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context context;
    private List<Schedule> scheduleList;
    private LayoutInflater layoutInflater;

    public ScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.scheduleList = scheduleList;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_data_schedule_slidebar, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        holder.setView(schedule, position);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }


    class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewSubject;
        private TextView textViewSchedule;
        private Schedule schedule;
        private RecyclerView button_click_schedule;

        class ScheduleViewThread extends Thread {
            @Override
            public void run() {
                try {
                    final ScheduleHelper scheduleHelper = new ScheduleHelper();
                    final Subject subject = new SubjectServiceImpl()
                            .getSubjectByScheduleId(schedule.getId());
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageResource(scheduleHelper.imageDisplay(
                                    schedule.getDay()));
                            textViewSubject.setText((subject != null ? subject.getName() : "None"));
                            textViewSchedule.setText(scheduleHelper.display(schedule.getRoom(),
                                    schedule.getTime(), schedule.getPeriod()));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        ScheduleViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_schedule_imageview);
            textViewSubject = (TextView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_schedule_text_subject);
            textViewSchedule = (TextView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_schedule_text_schedule);





        }

        void setView(final Schedule _schedule, final int _position) {
            schedule = _schedule;
            new ScheduleViewThread().start();
        }
    }
}
