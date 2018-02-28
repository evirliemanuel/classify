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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.lieverandiver.thesisproject.adapter.ScheduleAdapter;
import com.lieverandiver.thesisproject.helper.ScheduleHelper;
import com.lieverandiver.thesisproject.helper.TeacherHelper;
import com.remswork.project.alice.exception.ScheduleException;
import com.remswork.project.alice.model.Schedule;
import com.remswork.project.alice.service.impl.ScheduleServiceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.lieverandiver.thesisproject.R.id.schedule_all_rb;
import static com.lieverandiver.thesisproject.R.id.schedule_today_rb;
import static com.lieverandiver.thesisproject.R.id.schedule_tomorrow_rb;

public class SliderScheduleFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = SliderScheduleFragment.class.getSimpleName();

    final List<Schedule> scheduleList = new ArrayList<>();
    private ScheduleAdapter scheduleAdapter;
    private TextView txtMsgNoContent;
    private RecyclerView scheduleRecyclerView;
    private View customView;
    private Handler handler;
    private RelativeLayout progressBar;
    private RadioButton radioButtonToday;
    private RadioButton radioButtonTomorrow;
    private RadioButton radioButtonAll;
    private RadioGroup radioGroup;
    private int rbSelectedId = schedule_today_rb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        customView = inflater.inflate(R.layout.home_fragment_slidebar_schedule, container, false);
        progressBar = (RelativeLayout) customView.findViewById(R.id.progressbar_schedule);
        scheduleRecyclerView = (RecyclerView)
                customView.findViewById(R.id.shedule_recyclerview);
        radioButtonToday = (RadioButton) customView.findViewById(schedule_today_rb);
        radioButtonTomorrow = (RadioButton) customView.findViewById(schedule_tomorrow_rb);
        radioButtonAll = (RadioButton) customView.findViewById(schedule_all_rb);
        radioGroup = (RadioGroup) customView.findViewById(R.id.radio_group_schedule);
        txtMsgNoContent = (TextView) customView.findViewById(R.id.message_label_sc);
        handler = new Handler(getActivity().getMainLooper());

        radioGroup.setOnCheckedChangeListener(this);
        if(schedule_all_rb == rbSelectedId)
            radioButtonAll.setChecked(true);
        if(schedule_today_rb == rbSelectedId)
            radioButtonToday.setChecked(true);
        if(schedule_tomorrow_rb == rbSelectedId)
            radioButtonTomorrow.setChecked(true);


        progressBar.setVisibility(View.VISIBLE);
        txtMsgNoContent.setVisibility(View.INVISIBLE);
        scheduleRecyclerView.setVisibility(View.INVISIBLE);

        init();
        return customView;
    }

    public void init() {
        Collections.sort(scheduleList, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule o1, Schedule o2) {
                return (o1.getTime().compareTo(o2.getTime()));
            }
        });

        scheduleRecyclerView = (RecyclerView)
                customView.findViewById(R.id.shedule_recyclerview);
        scheduleAdapter = new ScheduleAdapter(getContext(), scheduleList);
        scheduleRecyclerView.setAdapter(scheduleAdapter);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        scheduleRecyclerView.setLayoutManager(layoutManager);
        scheduleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scheduleRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView");
        super.onDestroyView();

        customView = null;
        progressBar = null;
        scheduleRecyclerView = null;
        radioButtonToday = null;
        radioButtonTomorrow = null;
        radioButtonAll = null;
        radioGroup = null;
        txtMsgNoContent = null;
        handler = null;

        int size = scheduleList.size();
        for (int i = 0; i < size; i++) {
            if (scheduleList.size() > 0) {
                scheduleList.remove(0);
                scheduleAdapter.notifyItemRemoved(0);
            }
        }
    }

    @Override
    public synchronized void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.i(TAG, "onCheckedChanged");
        setSchedule(checkedId);
        rbSelectedId = checkedId;
    }

    public void setSchedule(final int id) {
        switch (id) {
            case schedule_today_rb:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                                txtMsgNoContent.setVisibility(View.INVISIBLE);
                            }
                        });
                        try {
                            final List<Schedule> newSchedules = new ArrayList<>();
                            final TeacherHelper teacherHelper = new TeacherHelper(getContext());
                            if (teacherHelper.loadUser().get() != null) {
                                try {
                                    for (Schedule schedule : new ScheduleServiceImpl()
                                            .getScheduleListByTeacherId(teacherHelper.loadUser()
                                                    .get().getId()))
                                        newSchedules.add(schedule);
                                } catch (ScheduleException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int size = scheduleAdapter.getItemCount();
                                    for (int i = 0; i < size; i++) {
                                        if (scheduleList.size() > 0) {
                                            scheduleList.remove(0);
                                            scheduleAdapter.notifyItemRemoved(0);
                                        }
                                    }

                                    ScheduleHelper scheduleHelper = new ScheduleHelper();
                                    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                                    for (Schedule schedule : newSchedules) {
                                        if (scheduleHelper.dayInNumber(schedule.getDay()) == day) {
                                            scheduleList.add(schedule);
                                            scheduleAdapter.notifyItemRangeInserted(0,
                                                    scheduleList.size());
                                        }
                                    }
                                    progressBar.setVisibility(View.INVISIBLE);
                                    if (scheduleList.size() < 1)
                                        txtMsgNoContent.setVisibility(View.VISIBLE);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case schedule_tomorrow_rb:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                                txtMsgNoContent.setVisibility(View.INVISIBLE);
                            }
                        });
                        try {
                            final List<Schedule> newSchedules = new ArrayList<>();
                            final TeacherHelper teacherHelper = new TeacherHelper(getContext());
                            if (teacherHelper.loadUser().get() != null) {
                                try {
                                    for (Schedule schedule : new ScheduleServiceImpl()
                                            .getScheduleListByTeacherId(teacherHelper.loadUser().get().getId()))
                                        newSchedules.add(schedule);
                                } catch (ScheduleException e) {
                                    e.printStackTrace();
                                }
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int size = scheduleAdapter.getItemCount();
                                    for (int i = 0; i < size; i++) {
                                        if (scheduleList.size() > 0) {
                                            scheduleList.remove(0);
                                            scheduleAdapter.notifyItemRemoved(0);
                                        }
                                    }
                                    ScheduleHelper scheduleHelper = new ScheduleHelper();
                                    int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 1;
                                    for (Schedule schedule : newSchedules) {
                                        if (scheduleHelper.dayInNumber(schedule.getDay()) == day) {
                                            scheduleList.add(schedule);
                                            scheduleAdapter.notifyItemRangeInserted(0,
                                                    scheduleList.size());
                                        }
                                    }
                                    progressBar.setVisibility(View.INVISIBLE);
                                    if (scheduleList.size() < 1)
                                        txtMsgNoContent.setVisibility(View.VISIBLE);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            default:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                                txtMsgNoContent.setVisibility(View.INVISIBLE);
                            }
                        });
                        try {
                            final List<Schedule> newSchedules = new ArrayList<>();
                            final TeacherHelper teacherHelper = new TeacherHelper(getContext());
                            if (teacherHelper.loadUser().get() != null) {
                                try {
                                    for (Schedule schedule : new ScheduleServiceImpl()
                                            .getScheduleListByTeacherId(teacherHelper.loadUser()
                                                    .get().getId()))
                                        newSchedules.add(schedule);
                                } catch (ScheduleException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int size = scheduleAdapter.getItemCount();
                                    for (int i = 0; i < size; i++) {
                                        if (scheduleList.size() > 0) {
                                            scheduleList.remove(0);
                                            scheduleAdapter.notifyItemRemoved(0);
                                        }
                                    }
                                    for (Schedule schedule : newSchedules) {
                                        scheduleList.add(schedule);
                                        scheduleAdapter.notifyItemRangeInserted(0,
                                                scheduleList.size());
                                    }
                                    progressBar.setVisibility(View.INVISIBLE);
                                    if (scheduleList.size() < 1)
                                        txtMsgNoContent.setVisibility(View.VISIBLE);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
}
