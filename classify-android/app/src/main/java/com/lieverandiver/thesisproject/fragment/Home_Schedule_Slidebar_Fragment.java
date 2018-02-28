package com.lieverandiver.thesisproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lieverandiver.thesisproject.R;
import com.lieverandiver.thesisproject.ScheduleAdapter;
import com.lieverandiver.thesisproject.helper.TeacherHelper;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.service.impl.ClassServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Verlie on 8/30/2017.
 */

@Deprecated
public class Home_Schedule_Slidebar_Fragment extends Fragment{

    private RecyclerView scheduleRecyclerView;
    private View customView;
    private Handler handler;

    public Home_Schedule_Slidebar_Fragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        customView = inflater.inflate(R.layout.home_fragment_slidebar_schedule, container, false);
        handler = new Handler(getActivity().getMainLooper());
        init();
        return customView;
    }

    public void init() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<Schedule> scheduleList = new ArrayList<>();
                    TeacherHelper teacherHelper = new TeacherHelper(getContext());
                    ClassServiceImpl classService = new ClassServiceImpl();
                    Class _class = null;
                    for(Class c  : classService.getClassList()) {
                        if(c.getTeacher() == null)
                            continue;
                        if(c.getTeacher().getId() == teacherHelper.loadUser().get().getId()) {
                            _class = c;
                            Log.i("myTAG","id : " + c.getId());
                            break;
                        }
                    }

                    //test
                    _class = classService.getClassById(40);
                    if(_class != null) {
                        for (Schedule schedule : classService.getScheduleList(_class.getId()))
                            scheduleList.add(schedule);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            scheduleRecyclerView = (RecyclerView)
                                    customView.findViewById(R.id.shedule_recyclerview);
                            ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(), scheduleList);
                            scheduleRecyclerView.setAdapter(scheduleAdapter);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                            scheduleRecyclerView.setLayoutManager(layoutManager);
                            scheduleRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        }
                    });

                } catch (ClassException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
