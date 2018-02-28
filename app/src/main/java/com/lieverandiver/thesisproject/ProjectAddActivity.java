package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.lieverandiver.thesisproject.adapter.ProjectAdapter;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.service.ProjectService;
import com.remswork.project.alice.service.impl.ProjectServiceImpl;
import java.util.List;
import static com.lieverandiver.thesisproject.R.id.add_add5;
import static com.lieverandiver.thesisproject.R.id.add_back5;
import static com.lieverandiver.thesisproject.R.id.add_grade4;
import static com.lieverandiver.thesisproject.R.id.add_grade5;


public class ProjectAddActivity extends AppCompatActivity implements ProjectAdapter.OnClickListener,
        View.OnClickListener {

    private static final String TAG = ProjectAddActivity.class.getSimpleName();

    final ProjectService projectService = new ProjectServiceImpl();
    private ImageView imageView;
    private Button btnBackButton;
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutActivity;
    private long classId;
    private long termId;
    private LinearLayout linearLayoutGrades;

    private class ProjectAddThread extends Thread {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<Project> projectList = projectService.getProjectListByClassId(classId, termId);
                        ProjectAdapter projectAdapter = new ProjectAdapter(ProjectAddActivity.this, projectList);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(ProjectAddActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recyclerView.setAdapter(projectAdapter);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    }catch (GradingFactorException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void init() {
        linearLayoutGrades = (LinearLayout)findViewById(add_grade5);
        linearLayoutActivity = (LinearLayout) findViewById(R.id.add_add5);
        recyclerView = (RecyclerView) findViewById(R.id.add_recycler5);
        btnBackButton = (Button) findViewById(R.id.add_back5);
        linearLayoutGrades.setOnClickListener(this);

        linearLayoutActivity.setOnClickListener(this);
        btnBackButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_z_add_project);
        try {
            classId = getIntent().getExtras().getLong("classId");
            termId = getIntent().getExtras().getLong("termId");

            init();
            new ProjectAddThread().start();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(Project project, long projectId) {
        Intent intent = getIntent();
        intent.putExtra("projectId", projectId);
        intent.setClass(this, ProjectResultActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case add_add5 :
                Intent intent = getIntent().setClass(this, ProjectInputActivity.class);
                startActivity(intent);
                break;
            case add_back5 :
                finish();
                break;

            case add_grade5 :
                intent = getIntent().setClass(this, ActivityGradeProject.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}
