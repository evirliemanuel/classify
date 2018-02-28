package com.lieverandiver.thesisproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.service.impl.ClassServiceImpl;

import java.util.List;

/**
 * Created by Verlie on 8/30/2017.
 */

@Deprecated
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{

    private Context context;
    private List<Schedule> scheduleList;
    private LayoutInflater layoutInflater;

    public ScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.scheduleList = scheduleList;
    }

    @Override
    public ScheduleAdapter.ScheduleViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_data_schedule_slidebar, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter
                                             .ScheduleViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        holder.setView(schedule, position);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }


    protected class ScheduleViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView textViewSubject;
        private TextView textViewSchedule;

        public ScheduleViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_schedule_imageview);
            textViewSubject = (TextView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_schedule_text_subject);
            textViewSchedule = (TextView) itemView.findViewById(
                    R.id.fragment_slidebar_cardview_schedule_text_schedule);

        }

        public void setView(final Schedule schedule, final int position) {
            try {
                ClassServiceImpl classService = new ClassServiceImpl();
                Subject subject = new Subject();
                for(Class _class : classService.getClassList()) {
                    for(Schedule _schedule : _class.getScheduleList()) {
                        if(_schedule.getId() == schedule.getId()) {
                            subject = _class.getSubject();
                            break;
                        }
                    }
                }

                textViewSchedule.setText(subject.getName());
                textViewSchedule.setText(schedule.getDay());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
