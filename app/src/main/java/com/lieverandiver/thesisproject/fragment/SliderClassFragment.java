package com.lieverandiver.thesisproject.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lieverandiver.thesisproject.R;
import com.lieverandiver.thesisproject.adapter.ClassAdapter;
import com.lieverandiver.thesisproject.helper.TeacherHelper;
import com.remswork.project.alice.exception.ClassException;
import com.remswork.project.alice.model.Class;
import com.remswork.project.alice.service.impl.ClassServiceImpl;

import java.util.List;

public class SliderClassFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Handler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_slidebar_class, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.class_recyclerview);
        progressBar = (ProgressBar) view.findViewById(R.id.class_progressbar);
        handler = new Handler(getActivity().getMainLooper());
        init();
        return view;
    }

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Class> classList = new ClassServiceImpl().getClassListByTeacherId(
                            new TeacherHelper(getContext()).loadUser().get().getId());
                    final ClassAdapter classAdapter = new ClassAdapter(getContext(), classList);
                    final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(classAdapter);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                } catch (ClassException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
