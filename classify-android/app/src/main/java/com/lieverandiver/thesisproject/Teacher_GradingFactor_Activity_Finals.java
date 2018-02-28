package com.lieverandiver.thesisproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.lieverandiver.thesisproject.helper.TeacherHelper;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.model.Subject;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.service.impl.FormulaServiceImpl;
import com.remswork.project.alice.service.impl.SubjectServiceImpl;

/**
 * Created by Verlie on 9/1/2017.
 */

@Deprecated
public class Teacher_GradingFactor_Activity_Finals extends AppCompatActivity{

    private TextView textView1 ;
    private TextView textView2 ;
    private TextView textView3 ;
    private TextView textView4 ;
    private TextView textView5 ;
    private TextView textView6 ;

    private SeekBar seekBar1 ;
    private SeekBar seekBar2 ;
    private SeekBar seekBar3 ;
    private SeekBar seekBar4 ;
    private SeekBar seekBar5 ;
    private SeekBar seekBar6 ;

    int progress = 0;
    private int total;

    private Switch aSwitch1 ;
    private Switch aSwitch2 ;
    private Switch aSwitch3 ;
    private Switch aSwitch4 ;
    private Switch aSwitch5 ;
    private Switch aSwitch6 ;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private LinearLayout linearLayout5;
    private LinearLayout linearLayout6;

    private TextView textViewm1;
    private TextView textViewm2;
    private Spinner spinnerm;
    private  TextView textViewm3;
    private LinearLayout linearLayoutm;
    private int percent[] = new int[6];
    private String[] values = new String[]{
            "10%","20%","30%","40%","50%","60%","70%","80%","90%","100%"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_gradingfactor_activity_finals);

        final Subject subject;
        final Teacher teacher;

        try {
            subject = new SubjectServiceImpl().getSubjectById(getIntent()
                    .getExtras().getLong("subjectId"));
            teacher = new TeacherHelper(this).loadUser().get();

            linearLayoutm = (LinearLayout) findViewById(R.id.finals_save);

            textViewm1 = (TextView) findViewById(R.id.finals_subjectname);
            textViewm2 = (TextView) findViewById(R.id.txtv_final_percent);
//            spinnerm = (Spinner) findViewById(R.id.midterm_spinner_percent);
            textViewm3 = (TextView) findViewById(R.id.total_percentf);

            textViewm1.setText(subject.getName());


            seekBar1 = (SeekBar) findViewById(R.id.activity_seekbarf);
            seekBar1.setMax(100);
            seekBar1.setProgress(progress);

            seekBar2 = (SeekBar) findViewById(R.id.assignment_seekbarf);
            seekBar2.setMax(100);
            seekBar2.setProgress(progress);


            seekBar3 = (SeekBar) findViewById(R.id.attendance_seekbarf);
            seekBar3.setMax(100);
            seekBar3.setProgress(progress);

            seekBar4 = (SeekBar) findViewById(R.id.exam_seekbarf);
            seekBar4.setMax(100);
            seekBar4.setProgress(progress);

            seekBar5 = (SeekBar) findViewById(R.id.project_seekbarf);
            seekBar5.setMax(100);
            seekBar5.setProgress(progress);

            seekBar6 = (SeekBar) findViewById(R.id.quiz_seekbarf);
            seekBar6.setMax(100);
            seekBar6.setProgress(progress);



            textView1 = (TextView) findViewById(R.id.activity_percent_textf);
            textView2 = (TextView) findViewById(R.id.assignment_percent_textf);
            textView3 = (TextView) findViewById(R.id.attendance_percent_textf);
            textView4 = (TextView) findViewById(R.id.exam_percent_textf);
            textView5 = (TextView) findViewById(R.id.project_percent_textf);
            textView6 = (TextView) findViewById(R.id.quiz_percent_textf);



            aSwitch1 = (Switch) findViewById(R.id.activity_switch_redf);
            aSwitch2 = (Switch) findViewById(R.id.assignment_switch_redf);
            aSwitch3 = (Switch) findViewById(R.id.attendance_switch_redf);
            aSwitch4 = (Switch) findViewById(R.id.exam_switch_redf);
            aSwitch5 = (Switch) findViewById(R.id.project_switch_redf);
            aSwitch6 = (Switch) findViewById(R.id.quiz_switch_redf);

            linearLayout1 = (LinearLayout)findViewById(R.id.activty_linearf);
            linearLayout1.setVisibility(View.GONE);

            linearLayout2 = (LinearLayout)findViewById(R.id.assignment_linearf);
            linearLayout2.setVisibility(View.GONE);

            linearLayout3 = (LinearLayout)findViewById(R.id.attendance_linearf);
            linearLayout3.setVisibility(View.GONE);

            linearLayout4 = (LinearLayout)findViewById(R.id.exam_linearf);
            linearLayout4.setVisibility(View.GONE);

            linearLayout5 = (LinearLayout)findViewById(R.id.project_linearf);
            linearLayout5.setVisibility(View.GONE);

            linearLayout6 = (LinearLayout)findViewById(R.id.quiz_linearf);
            linearLayout6.setVisibility(View.GONE);


            seekBar1.setKeyProgressIncrement(5);
            seekBar2.setKeyProgressIncrement(5);
            seekBar3.setKeyProgressIncrement(5);
            seekBar4.setKeyProgressIncrement(5);
            seekBar5.setKeyProgressIncrement(5);
            seekBar6.setKeyProgressIncrement(5);

            linearLayoutm.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Formula formula = new Formula();
                    formula.setActivityPercentage(percent[0]);
                    formula.setAssignmentPercentage(percent[1]);
                    formula.setAttendancePercentage(percent[2]);
                    formula.setExamPercentage(percent[3]);
                    formula.setProjectPercentage(percent[4]);
                    formula.setQuizPercentage(percent[5]);
                    try {
                        new FormulaServiceImpl().addFormula(formula, subject.getId(), teacher.getId());
                    } catch (GradingFactorException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            });

            ArrayAdapter<String> spinnerAdapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, values);
            spinnerm.setAdapter(spinnerAdapter);

            aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // do something when check is selected
                        linearLayout1.setVisibility(View.VISIBLE);
                    } else {
                        //do something when unchecked
                        linearLayout1.setVisibility(View.GONE);
                    }
                    int x = 0;
                    percent[0] = 0;
                    for(int i=0; i < percent.length; i++)
                        x += percent[i];
                    textViewm3.setText(x + "%");
                    seekBar1.setProgress(0);
                }
            });

            aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // do something when check is selected
                        linearLayout2.setVisibility(View.VISIBLE);
                    } else {
                        //do something when unchecked
                        linearLayout2.setVisibility(View.GONE);
                    }
                    int x = 0;
                    percent[1] = 0;
                    for(int i=0; i < percent.length; i++)
                        x += percent[i];
                    textViewm3.setText(x + "%");
                    seekBar2.setProgress(0);
                }
            });

            aSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // do something when check is selected
                        linearLayout3.setVisibility(View.VISIBLE);
                    } else {
                        //do something when unchecked
                        linearLayout3.setVisibility(View.GONE);
                    }
                    int x = 0;
                    percent[2] = 0;
                    for(int i=0; i < percent.length; i++)
                        x += percent[i];
                    textViewm3.setText(x + "%");
                    seekBar3.setProgress(0);
                }
            });

            aSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // do something when check is selected
                        linearLayout4.setVisibility(View.VISIBLE);
                    } else {
                        //do something when unchecked
                        linearLayout4.setVisibility(View.GONE);
                    }
                    int x = 0;
                    percent[3] = 0;
                    for(int i=0; i < percent.length; i++)
                        x += percent[i];
                    textViewm3.setText(x + "%");
                    seekBar4.setProgress(0);
                }
            });

            aSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // do something when check is selected
                        linearLayout5.setVisibility(View.VISIBLE);
                    } else {
                        //do something when unchecked
                        linearLayout5.setVisibility(View.GONE);
                    }
                    int x = 0;
                    percent[4] = 0;
                    for(int i=0; i < percent.length; i++)
                        x += percent[i];
                    textViewm3.setText(x + "%");
                    seekBar5.setProgress(0);
                }
            });

            aSwitch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // do something when check is selected
                        linearLayout6.setVisibility(View.VISIBLE);
                    } else {
                        //do something when unchecked
                        linearLayout6.setVisibility(View.GONE);
                    }
                    int x = 0;
                    percent[5] = 0;
                    for(int i=0; i < percent.length; i++)
                        x += percent[i];
                    textViewm3.setText(x + "%");
                    seekBar6.setProgress(0);
                }
            });


            seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int x = 0;
                    for(int count=0; count < percent.length; count++) {
                        if(count == 0)
                            continue;
                        x += percent[count];
                    }

                    if((x + i) <= 100) {
                        percent[0] = i;
                        textView1.setText(i + "");;
                        textViewm3.setText(x + percent[0] + "%");
                    }else if((x + i) > 100) {
                        percent[0] = 100-x;
                        textView1.setText(percent[0] + "");;
                        textViewm3.setText((x + percent[0]) + "%");
                        seekBar.setProgress(percent[0]);
                    }else
                        percent[0] = 0;
                }

                @Override
                public void onStartTrackingTouch(SeekBar bar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar bar) {
                }


            });

            seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int x = 0;
                    for(int count=0; count < percent.length; count++) {
                        if(count == 1)
                            continue;
                        x += percent[count];
                    }

                    if((x + i) <= 100) {
                        percent[1] = i;
                        textView2.setText(i + "");;
                        textViewm3.setText(x + percent[1] + "%");
                    }else if((x + i) > 100) {
                        percent[1] = 100-x;
                        textView2.setText(percent[1] + "");;
                        textViewm3.setText((x + percent[1]) + "%");
                        seekBar.setProgress(percent[1]);
                    }else
                        percent[1] = 0;
                }

                @Override
                public void onStartTrackingTouch(SeekBar bar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar bar) {
                }


            });

            seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int x = 0;
                    for(int count=0; count < percent.length; count++) {
                        if(count == 2)
                            continue;
                        x += percent[count];
                    }

                    if((x + i) <= 100) {
                        percent[2] = i;
                        textView3.setText(i + "");;
                        textViewm3.setText(x + percent[2] + "%");
                    }else if((x + i) > 100) {
                        percent[2] = 100-x;
                        textView3.setText(percent[2] + "");;
                        textViewm3.setText((x + percent[2]) + "%");
                        seekBar.setProgress(percent[2]);
                    }else
                        percent[2] = 0;
                }

                @Override
                public void onStartTrackingTouch(SeekBar bar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar bar) {
                }

            });

            seekBar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int x = 0;
                    for(int count=0; count < percent.length; count++) {
                        if(count == 3)
                            continue;
                        x += percent[count];
                    }

                    if((x + i) <= 100) {
                        percent[3] = i;
                        textView4.setText(i + "");;
                        textViewm3.setText(x + percent[3] + "%");
                    }else if((x + i) > 100) {
                        percent[3] = 100-x;
                        textView4.setText(percent[3] + "");;
                        textViewm3.setText((x + percent[3]) + "%");
                        seekBar.setProgress(percent[3]);
                    }else
                        percent[3] = 0;
                }

                @Override
                public void onStartTrackingTouch(SeekBar bar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar bar) {
                }


            });

            seekBar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int x = 0;
                    for(int count=0; count < percent.length; count++) {
                        if(count == 4)
                            continue;
                        x += percent[count];
                    }

                    if((x + i) <= 100) {
                        percent[4] = i;
                        textView5.setText(i + "");;
                        textViewm3.setText(x + percent[4] + "%");
                    }else if((x + i) > 100) {
                        percent[4] = 100-x;
                        textView5.setText(percent[4] + "");;
                        textViewm3.setText((x + percent[4]) + "%");
                        seekBar.setProgress(percent[4]);
                    }else
                        percent[4] = 0;
                }

                @Override
                public void onStartTrackingTouch(SeekBar bar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar bar) {
                }


            });

            seekBar6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    int x = 0;
                    for(int count=0; count < percent.length; count++) {
                        if(count == 5)
                            continue;
                        x += percent[count];
                    }

                    if((x + i) <= 100) {
                        percent[5] = i;
                        textView6.setText(i + "");;
                        textViewm3.setText(x + percent[5] + "%");
                    }else if((x + i) > 100) {
                        percent[5] = 100-x;
                        textView6.setText(percent[5] + "");;
                        textViewm3.setText((x + percent[5]) + "%");
                        seekBar.setProgress(percent[5]);
                    }else
                        percent[5] = 0;
                }

                @Override
                public void onStartTrackingTouch(SeekBar bar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar bar) {
                }


            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
