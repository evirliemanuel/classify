package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lieverandiver.thesisproject.helper.TeacherHelper;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.FormulaService;
import com.remswork.project.alice.service.SubjectService;
import com.remswork.project.alice.service.impl.FormulaServiceImpl;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;

import java.util.ArrayList;

import static android.R.attr.action;
import static android.R.attr.button;
import static com.lieverandiver.thesisproject.R.id.action_container;
import static com.lieverandiver.thesisproject.R.id.activity_seekbarm;
import static com.lieverandiver.thesisproject.R.id.activity_switch_redm;
import static com.lieverandiver.thesisproject.R.id.add_add1;
import static com.lieverandiver.thesisproject.R.id.add_back1;
import static com.lieverandiver.thesisproject.R.id.assignment_seekbarm;
import static com.lieverandiver.thesisproject.R.id.assignment_switch_redm;
import static com.lieverandiver.thesisproject.R.id.attendance_seekbarm;
import static com.lieverandiver.thesisproject.R.id.attendance_switch_redm;
import static com.lieverandiver.thesisproject.R.id.criteria_failed;
import static com.lieverandiver.thesisproject.R.id.criteria_succes;
import static com.lieverandiver.thesisproject.R.id.exam_seekbarm;
import static com.lieverandiver.thesisproject.R.id.exam_switch_redm;
import static com.lieverandiver.thesisproject.R.id.input_back1;
import static com.lieverandiver.thesisproject.R.id.input_ok1;
import static com.lieverandiver.thesisproject.R.id.input_tryagain1;
import static com.lieverandiver.thesisproject.R.id.input_tryagainemp1;
import static com.lieverandiver.thesisproject.R.id.project_seekbarm;
import static com.lieverandiver.thesisproject.R.id.project_switch_redm;
import static com.lieverandiver.thesisproject.R.id.quiz_seekbarm;
import static com.lieverandiver.thesisproject.R.id.quiz_switch_redm;
import static com.lieverandiver.thesisproject.R.id.toggleButton1m;
import static com.lieverandiver.thesisproject.R.id.toggleButton2m;
import static com.lieverandiver.thesisproject.R.id.toggleButton3m;
import static com.lieverandiver.thesisproject.R.id.toggleButton4m;
import static com.lieverandiver.thesisproject.R.id.toggleButton5m;
import static com.lieverandiver.thesisproject.R.id.toggleButton6m;

public class CriteriaMidtermInputActivity extends AppCompatActivity implements RecognitionListener,
        View.OnClickListener, SeekBar.OnSeekBarChangeListener, CompoundButton
                .OnCheckedChangeListener {

    private TextView txtActivityPercent;
    private TextView txtAssignmentPercent;
    private TextView txtAttendancePercent;
    private TextView txtExamPercent;
    private TextView txtProjectPercent;
    private TextView txtQuizPercent;

    private SeekBar sbActivity;
    private SeekBar sbAssignment;
    private SeekBar sbAttendance;
    private SeekBar sbExam;
    private SeekBar sbProject;
    private SeekBar sbQuiz;

    private Switch swActivity;
    private Switch swAssignment;
    private Switch swAttendance;
    private Switch swExam;
    private Switch swProject;
    private Switch swQuiz;

    private LinearLayout laActivity;
    private LinearLayout laAssignment;
    private LinearLayout laAttendance;
    private LinearLayout laExam;
    private LinearLayout laProject;
    private LinearLayout laQuiz;

    private TextView txtSubjectName;
    private TextView txTermTitle;
    private TextView txtTotalPercent;
    private LinearLayout laSave;
    private boolean isExist;
    private Spinner spinnerm;
    private int activeListener;

    private CardView cardViewFailed;

    private int percent[] = new int[6];
    private String[] values = new String[]{
            "10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"
    };

    private final SubjectService subjectService = new SubjectServiceImpl();
    private final FormulaService formulaService = new FormulaServiceImpl();
    private Subject subject;
    private Teacher teacher;
    private Formula formula;

    private ToggleButton tbActivity;
    private ToggleButton tbAssignment;
    private ToggleButton tbAttendance;
    private ToggleButton tbExam;
    private ToggleButton tbProject;
    private ToggleButton tbQuiz;

    private ProgressBar pbActivity;
    private ProgressBar pbAssignment;
    private ProgressBar pbAttendance;
    private ProgressBar pbExam;
    private ProgressBar pbProject;
    private ProgressBar pbQuiz;

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognition";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private void init() {

        cardViewFailed = (CardView) findViewById(R.id.criteria_failed);
        laSave = (LinearLayout) findViewById(R.id.midterm_save);
        txtSubjectName = (TextView) findViewById(R.id.midterm_subjectname);
        txtTotalPercent = (TextView) findViewById(R.id.total_percentm);
        txTermTitle = (TextView) findViewById(R.id.term_type_cri);
        spinnerm = (Spinner) findViewById(R.id.midterm_spinner_percent);

        sbActivity = (SeekBar) findViewById(activity_seekbarm);
        sbAssignment = (SeekBar) findViewById(R.id.assignment_seekbarm);
        sbAttendance = (SeekBar) findViewById(R.id.attendance_seekbarm);
        sbExam = (SeekBar) findViewById(exam_seekbarm);
        sbProject = (SeekBar) findViewById(project_seekbarm);
        sbQuiz = (SeekBar) findViewById(quiz_seekbarm);

        txtActivityPercent = (TextView) findViewById(R.id.activity_percent_textm);
        txtAssignmentPercent = (TextView) findViewById(R.id.assignment_percent_textm);
        txtAttendancePercent = (TextView) findViewById(R.id.attendance_percent_textm);
        txtExamPercent = (TextView) findViewById(R.id.exam_percent_textm);
        txtProjectPercent = (TextView) findViewById(R.id.project_percent_textm);
        txtQuizPercent = (TextView) findViewById(R.id.quiz_percent_textm);

        swActivity = (Switch) findViewById(activity_switch_redm);
        swAssignment = (Switch) findViewById(R.id.assignment_switch_redm);
        swAttendance = (Switch) findViewById(R.id.attendance_switch_redm);
        swExam = (Switch) findViewById(R.id.exam_switch_redm);
        swProject = (Switch) findViewById(project_switch_redm);
        swQuiz = (Switch) findViewById(quiz_switch_redm);

        sbActivity.setMax(100);
        sbAssignment.setMax(100);
        sbAttendance.setMax(100);
        sbExam.setMax(100);
        sbProject.setMax(100);
        sbQuiz.setMax(100);

        laActivity = (LinearLayout) findViewById(R.id.activty_linearm);
        laAssignment = (LinearLayout) findViewById(R.id.assignment_linearm);
        laAttendance = (LinearLayout) findViewById(R.id.attendance_linearm);
        laExam = (LinearLayout) findViewById(R.id.exam_linearm);
        laProject = (LinearLayout) findViewById(R.id.project_linearm);
        laQuiz = (LinearLayout) findViewById(R.id.quiz_linearm);

        pbActivity = (ProgressBar) findViewById(R.id.progressBar1m);
        pbAssignment = (ProgressBar) findViewById(R.id.progressBar2m);
        pbAttendance = (ProgressBar) findViewById(R.id.progressBar3m);
        pbExam = (ProgressBar) findViewById(R.id.progressBar4m);
        pbProject = (ProgressBar) findViewById(R.id.progressBar5m);
        pbQuiz = (ProgressBar) findViewById(R.id.progressBar6m);

        tbActivity = (ToggleButton) findViewById(R.id.toggleButton1m);
        tbAssignment = (ToggleButton) findViewById(toggleButton2m);
        tbAttendance = (ToggleButton) findViewById(toggleButton3m);
        tbExam = (ToggleButton) findViewById(toggleButton4m);
        tbProject = (ToggleButton) findViewById(toggleButton5m);
        tbQuiz = (ToggleButton) findViewById(toggleButton6m);

        laActivity.setVisibility(View.GONE);
        laAssignment.setVisibility(View.GONE);
        laAttendance.setVisibility(View.GONE);
        laExam.setVisibility(View.GONE);
        laProject.setVisibility(View.GONE);
        laQuiz.setVisibility(View.GONE);

        laSave.setOnClickListener(this);

        sbActivity.setOnSeekBarChangeListener(this);
        sbAssignment.setOnSeekBarChangeListener(this);
        sbAttendance.setOnSeekBarChangeListener(this);
        sbExam.setOnSeekBarChangeListener(this);
        sbProject.setOnSeekBarChangeListener(this);
        sbQuiz.setOnSeekBarChangeListener(this);

        swActivity.setOnCheckedChangeListener(this);
        swAssignment.setOnCheckedChangeListener(this);
        swAttendance.setOnCheckedChangeListener(this);
        swExam.setOnCheckedChangeListener(this);
        swProject.setOnCheckedChangeListener(this);
        swQuiz.setOnCheckedChangeListener(this);

        txtSubjectName.setText(subject.getName());
        txTermTitle.setText("Midterm");

        if (isExist) {
            if (formula.getActivityPercentage() > 0)
                swActivity.setChecked(true);
            if (formula.getAssignmentPercentage() > 0)
                swAssignment.setChecked(true);
            if (formula.getAttendancePercentage() > 0)
                swAttendance.setChecked(true);
            if (formula.getExamPercentage() > 0)
                swExam.setChecked(true);
            if (formula.getProjectPercentage() > 0)
                swProject.setChecked(true);
            if (formula.getQuizPercentage() > 0)
                swQuiz.setChecked(true);

            sbActivity.setProgress(formula.getActivityPercentage());
            sbAssignment.setProgress(formula.getAssignmentPercentage());
            sbAttendance.setProgress(formula.getAttendancePercentage());
            sbExam.setProgress(formula.getExamPercentage());
            sbProject.setProgress(formula.getProjectPercentage());
            sbQuiz.setProgress(formula.getQuizPercentage());
        } else {
            sbActivity.setProgress(0);
            sbAssignment.setProgress(0);
            sbAttendance.setProgress(0);
            sbExam.setProgress(0);
            sbProject.setProgress(0);
            sbQuiz.setProgress(0);
        }

        pbActivity.setVisibility(View.GONE);
        pbAssignment.setVisibility(View.GONE);
        pbAttendance.setVisibility(View.GONE);
        pbExam.setVisibility(View.GONE);
        pbProject.setVisibility(View.GONE);
        pbQuiz.setVisibility(View.GONE);

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        tbActivity.setOnCheckedChangeListener(this);
        tbAssignment.setOnCheckedChangeListener(this);
        tbAttendance.setOnCheckedChangeListener(this);
        tbExam.setOnCheckedChangeListener(this);
        tbProject.setOnCheckedChangeListener(this);
        tbQuiz.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_gradingfactor_activity_midterm);
        try {
            subject = subjectService.getSubjectById(getIntent().getExtras().getLong("subjectId"));
            formula = formulaService.getFormulaById(getIntent().getExtras().getLong("formulaId"));
            teacher = new TeacherHelper(this).loadUser().get();
            isExist = getIntent().getExtras().getBoolean("isExist");

            init();

            ArrayAdapter<String> spinnerAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, values);
            spinnerm.setAdapter(spinnerAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        Formula _formula = new Formula();
        _formula.setActivityPercentage(percent[0]);
        _formula.setAssignmentPercentage(percent[1]);
        _formula.setAttendancePercentage(percent[2]);
        _formula.setExamPercentage(percent[3]);
        _formula.setProjectPercentage(percent[4]);
        _formula.setQuizPercentage(percent[5]);

        try {
            /**TODO */
            if (Integer.parseInt(txtTotalPercent.getText().toString().split("%")[0]) != 100) {
                cardViewFailed.setVisibility(View.VISIBLE);
                return;
            }
            if (!isExist)
                _formula = new FormulaServiceImpl().addFormula(_formula, subject.getId(),
                        teacher.getId(), 1);
            else
                _formula = new FormulaServiceImpl().updateFormulaById(formula.getId(), _formula,
                        0, 0, 0);
        } catch (GradingFactorException e) {
            e.printStackTrace();
        }
        if (_formula != null) {
            getIntent().setClass(this, SubjectViewActivity.class);
            startActivity(getIntent());
            this.finish();
        } else
            Toast.makeText(CriteriaMidtermInputActivity.this, "Please try again",
                    Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case activity_seekbarm:

                int x = 0;
                for (int count = 0; count < percent.length; count++) {
                    if (count == 0)
                        continue;
                    x += percent[count];
                }
                if ((x + progress) <= 100) {
                    percent[0] = progress;
                    txtActivityPercent.setText(String.valueOf(progress));
                    ;
                    txtTotalPercent.setText(x + percent[0] + "%");
                } else if ((x + progress) > 100) {
                    percent[0] = 100 - x;
                    txtActivityPercent.setText(String.valueOf(percent[0]));
                    ;
                    txtTotalPercent.setText(String.valueOf(x + percent[0]));
                    seekBar.setProgress(percent[0]);
                } else
                    percent[0] = 0;

                break;
            case assignment_seekbarm:

                x = 0;
                for (int count = 0; count < percent.length; count++) {
                    if (count == 1)
                        continue;
                    x += percent[count];
                }
                if ((x + progress) <= 100) {
                    percent[1] = progress;
                    txtAssignmentPercent.setText(progress + "");
                    ;
                    txtTotalPercent.setText(x + percent[1] + "%");
                } else if ((x + progress) > 100) {
                    percent[1] = 100 - x;
                    txtAssignmentPercent.setText(percent[1] + "");
                    ;
                    txtTotalPercent.setText((x + percent[1]) + "%");
                    seekBar.setProgress(percent[1]);
                } else
                    percent[1] = 0;

                break;
            case attendance_seekbarm:

                x = 0;
                for (int count = 0; count < percent.length; count++) {
                    if (count == 2)
                        continue;
                    x += percent[count];
                }
                if ((x + progress) <= 100) {
                    percent[2] = progress;
                    txtAttendancePercent.setText(progress + "");
                    ;
                    txtTotalPercent.setText(x + percent[2] + "%");
                } else if ((x + progress) > 100) {
                    percent[2] = 100 - x;
                    txtAttendancePercent.setText(percent[2] + "");
                    ;
                    txtTotalPercent.setText((x + percent[2]) + "%");
                    seekBar.setProgress(percent[2]);
                } else
                    percent[2] = 0;

                break;
            case exam_seekbarm:

                x = 0;
                for (int count = 0; count < percent.length; count++) {
                    if (count == 3)
                        continue;
                    x += percent[count];
                }
                if ((x + progress) <= 100) {
                    percent[3] = progress;
                    txtExamPercent.setText(progress + "");
                    ;
                    txtTotalPercent.setText(x + percent[3] + "%");
                } else if ((x + progress) > 100) {
                    percent[3] = 100 - x;
                    txtExamPercent.setText(percent[3] + "");
                    ;
                    txtTotalPercent.setText((x + percent[3]) + "%");
                    seekBar.setProgress(percent[3]);
                } else
                    percent[3] = 0;

                break;
            case project_seekbarm:

                x = 0;
                for (int count = 0; count < percent.length; count++) {
                    if (count == 4)
                        continue;
                    x += percent[count];
                }
                if ((x + progress) <= 100) {
                    percent[4] = progress;
                    txtProjectPercent.setText(progress + "");
                    ;
                    txtTotalPercent.setText(x + percent[4] + "%");
                } else if ((x + progress) > 100) {
                    percent[4] = 100 - x;
                    txtProjectPercent.setText(percent[4] + "");
                    ;
                    txtTotalPercent.setText((x + percent[4]) + "%");
                    seekBar.setProgress(percent[4]);
                } else
                    percent[4] = 0;

                break;
            case quiz_seekbarm:

                x = 0;
                for (int count = 0; count < percent.length; count++) {
                    if (count == 5)
                        continue;
                    x += percent[count];
                }
                if ((x + progress) <= 100) {
                    percent[5] = progress;
                    txtQuizPercent.setText(progress + "");
                    ;
                    txtTotalPercent.setText(x + percent[5] + "%");
                } else if ((x + progress) > 100) {
                    percent[5] = 100 - x;
                    txtQuizPercent.setText(percent[5] + "");
                    ;
                    txtTotalPercent.setText((x + percent[5]) + "%");
                    seekBar.setProgress(percent[5]);
                } else
                    percent[5] = 0;

                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case activity_switch_redm:
                if (isChecked)
                    laActivity.setVisibility(View.VISIBLE);
                else
                    laActivity.setVisibility(View.GONE);

                int x = 0;
                percent[0] = 0;
                for (int i = 0; i < percent.length; i++)
                    x += percent[i];
                txtTotalPercent.setText(x + "%");
                sbActivity.setProgress(0);
                break;
            case assignment_switch_redm:
                if (isChecked)
                    laAssignment.setVisibility(View.VISIBLE);
                else
                    laAssignment.setVisibility(View.GONE);

                x = 0;
                percent[1] = 0;
                for (int i = 0; i < percent.length; i++)
                    x += percent[i];
                txtTotalPercent.setText(x + "%");
                sbAssignment.setProgress(0);
                break;
            case attendance_switch_redm:
                if (isChecked)
                    laAttendance.setVisibility(View.VISIBLE);
                else
                    laAttendance.setVisibility(View.GONE);

                x = 0;
                percent[2] = 0;
                for (int i = 0; i < percent.length; i++)
                    x += percent[i];
                txtTotalPercent.setText(x + "%");
                sbAttendance.setProgress(0);
                break;
            case exam_switch_redm:
                if (isChecked)
                    laExam.setVisibility(View.VISIBLE);
                else
                    laExam.setVisibility(View.GONE);

                x = 0;
                percent[3] = 0;
                for (int i = 0; i < percent.length; i++)
                    x += percent[i];
                txtTotalPercent.setText(x + "%");
                sbExam.setProgress(0);
                break;
            case project_switch_redm:
                if (isChecked)
                    laProject.setVisibility(View.VISIBLE);
                else
                    laProject.setVisibility(View.GONE);

                x = 0;
                percent[4] = 0;
                for (int i = 0; i < percent.length; i++)
                    x += percent[i];
                txtTotalPercent.setText(x + "%");
                sbProject.setProgress(0);
                break;
            case quiz_switch_redm:
                if (isChecked)
                    laQuiz.setVisibility(View.VISIBLE);
                else
                    laQuiz.setVisibility(View.GONE);

                x = 0;
                percent[5] = 0;
                for (int i = 0; i < percent.length; i++)
                    x += percent[i];
                txtTotalPercent.setText(x + "%");
                sbQuiz.setProgress(0);
                break;
            case toggleButton1m:
                if (isChecked && activeListener == 0) {
                    pbActivity.setVisibility(View.VISIBLE);
                    pbActivity.setIndeterminate(true);
                    swActivity.setChecked(true);
                    speech.startListening(recognizerIntent);
                    activeListener = 1;
                } else {
                    tbActivity.setChecked(false);
                    pbActivity.setIndeterminate(false);
                    pbActivity.setVisibility(View.GONE);
                    speech.stopListening();
                }
                break;
            case toggleButton2m:
                if (isChecked && activeListener == 0) {
                    pbAssignment.setVisibility(View.VISIBLE);
                    pbAssignment.setIndeterminate(true);
                    swAssignment.setChecked(true);
                    speech.startListening(recognizerIntent);
                    activeListener = 2;
                } else {
                    tbAssignment.setChecked(false);
                    pbAssignment.setIndeterminate(false);
                    pbAssignment.setVisibility(View.GONE);
                    speech.stopListening();
                }
                break;
            case toggleButton3m:
                if (isChecked && activeListener == 0) {
                    pbAttendance.setVisibility(View.VISIBLE);
                    pbAttendance.setIndeterminate(true);
                    swAttendance.setChecked(true);
                    speech.startListening(recognizerIntent);
                    activeListener = 3;
                } else {
                    tbAttendance.setChecked(false);
                    pbAttendance.setIndeterminate(false);
                    pbAttendance.setVisibility(View.GONE);
                    speech.stopListening();
                }
                break;
            case toggleButton4m:
                if (isChecked && activeListener == 0) {
                    pbExam.setVisibility(View.VISIBLE);
                    pbExam.setIndeterminate(true);
                    swExam.setChecked(true);
                    speech.startListening(recognizerIntent);
                    activeListener = 4;
                } else {
                    tbExam.setChecked(false);
                    pbExam.setIndeterminate(false);
                    pbExam.setVisibility(View.GONE);
                    speech.stopListening();
                }
                break;
            case toggleButton5m:
                if (isChecked && activeListener == 0) {
                    pbProject.setVisibility(View.VISIBLE);
                    pbProject.setIndeterminate(true);
                    swProject.setChecked(true);
                    speech.startListening(recognizerIntent);
                    activeListener = 5;
                } else {
                    tbProject.setChecked(false);
                    pbProject.setIndeterminate(false);
                    pbProject.setVisibility(View.GONE);
                    speech.stopListening();
                }
                break;
            case toggleButton6m:
                if (isChecked && activeListener == 0) {
                    pbQuiz.setVisibility(View.VISIBLE);
                    pbQuiz.setIndeterminate(true);
                    swQuiz.setChecked(true);
                    speech.startListening(recognizerIntent);
                    activeListener = 6;
                } else {
                    tbQuiz.setChecked(false);
                    pbQuiz.setIndeterminate(false);
                    pbQuiz.setVisibility(View.GONE);
                    speech.stopListening();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");

        if (activeListener == 1) {
            pbActivity.setIndeterminate(false);
            pbActivity.setMax(10);
        } else if (activeListener == 2) {
            pbAssignment.setIndeterminate(false);
            pbAssignment.setMax(10);
        } else if (activeListener == 3) {
            pbAttendance.setIndeterminate(false);
            pbAttendance.setMax(10);
        } else if (activeListener == 4) {
            pbExam.setIndeterminate(false);
            pbExam.setMax(10);
        } else if (activeListener == 5) {
            pbProject.setIndeterminate(false);
            pbProject.setMax(10);
        } else if (activeListener == 6) {
            pbQuiz.setIndeterminate(false);
            pbQuiz.setMax(10);
        }
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        if (activeListener == 1) {
            pbActivity.setIndeterminate(true);
            tbActivity.setChecked(false);
        } else if (activeListener == 2) {
            pbAssignment.setIndeterminate(true);
            tbAssignment.setChecked(false);
        } else if (activeListener == 3) {
            pbAttendance.setIndeterminate(true);
            tbAttendance.setChecked(false);
        } else if (activeListener == 4) {
            pbExam.setIndeterminate(true);
            tbExam.setChecked(false);
        } else if (activeListener == 5) {
            pbProject.setIndeterminate(true);
            tbProject.setChecked(false);
        } else if (activeListener == 6) {
            pbQuiz.setIndeterminate(true);
            tbQuiz.setChecked(false);
        }
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);

        if (activeListener == 1) {
            sbActivity.setProgress(0);
            tbActivity.setChecked(false);
        } else if (activeListener == 2) {
            sbAssignment.setProgress(0);
            tbAssignment.setChecked(false);
        } else if (activeListener == 3) {
            sbAttendance.setProgress(0);
            tbAttendance.setChecked(false);
        } else if (activeListener == 4) {
            sbExam.setProgress(0);
            tbExam.setChecked(false);
        } else if (activeListener == 5) {
            sbProject.setProgress(0);
            tbProject.setChecked(false);
        } else if (activeListener == 6) {
            sbQuiz.setProgress(0);
            tbQuiz.setChecked(false);
        }
        activeListener = 0;
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        int number = 0;
        for (String result : matches) {
            try {
                number = Integer.parseInt(result);
                break;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                number = 0;
            }
        }

        if (activeListener == 1) {
            sbActivity.setProgress(number);
        } else if (activeListener == 2) {
            sbAssignment.setProgress(number);
        } else if (activeListener == 3) {
            sbAttendance.setProgress(number);
        } else if (activeListener == 4) {
            sbExam.setProgress(number);
        } else if (activeListener == 5) {
            sbProject.setProgress(number);
        } else if (activeListener == 6) {
            sbQuiz.setProgress(number);
        }
        activeListener = 0;
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);

        if (activeListener == 1) {
            pbActivity.setProgress((int) rmsdB);
        } else if (activeListener == 2) {
            pbAssignment.setProgress((int) rmsdB);
        } else if (activeListener == 3) {
            pbAttendance.setProgress((int) rmsdB);
        } else if (activeListener == 4) {
            pbExam.setProgress((int) rmsdB);
        } else if (activeListener == 5) {
            pbProject.setProgress((int) rmsdB);
        } else if (activeListener == 6) {
            pbQuiz.setProgress((int) rmsdB);
        }
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CriteriaMidtermInput Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
