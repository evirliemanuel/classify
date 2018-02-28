package com.lieverandiver.thesisproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.lieverandiver.thesisproject.helper.TeacherHelper;
import com.remswork.project.alice.model.Teacher;

public class SliderSettingFragment extends Fragment {

    private TextView textName;
    private TextView textNameLabel;
    private ProgressBar progressBar;
    private LinearLayout layout;
    private LinearLayout viewLogout;
    private TeacherHelper teacherHelper;
    private Handler handler;
    private LinearLayout linearLayoutprofile;
    private OnProfileClickListener onProfileClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        handler = new Handler(getActivity().getMainLooper());
        View view = inflater.inflate(R.layout.home_fragment_slidebar_logs, container, false);
        textName = (TextView) view.findViewById(R.id.text_full_name);
        textNameLabel = (TextView) view.findViewById(R.id.text_full_name_label);
        progressBar = (ProgressBar) view.findViewById(R.id.profile_progressbar);
        layout = (LinearLayout) view.findViewById(R.id.profile_layout);
        viewLogout = (LinearLayout) view.findViewById(R.id.btn_logout);
        linearLayoutprofile = (LinearLayout) view.findViewById(R.id.btn_profile);

        linearLayoutprofile.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileClickListener.viewProfile(teacherHelper.loadUser().get());
            }
        });
        viewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherHelper.removeUser();
                getActivity().finish();
            }
        });
        layout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        displayEvenDelay();
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileClickListener) {
            onProfileClickListener = (OnProfileClickListener) context;
        }
    }

    public void displayEvenDelay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                teacherHelper = new TeacherHelper(getContext());
                final Teacher teacher = teacherHelper.loadUser().get();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textName.setText(teacher.getLastName() + " " + teacher.getFirstName() + " "
                                + teacher.getMiddleName().toCharArray()[0] + ".");
                        textNameLabel.setText("Tap here to view");
                        layout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        }).start();
    }

    public interface OnProfileClickListener {
        public void viewProfile(Teacher teacher);
    }

}

