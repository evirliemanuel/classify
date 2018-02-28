package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lieverandiver.thesisproject.ActivityInputActivity;
import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.attr.progressBarStyleHorizontal;


public class ActivityInputAdapter extends RecyclerView.Adapter<ActivityInputAdapter.StudentAdapterViewHolder>
    implements ActivityInputActivity.InputListener {

    private List<Student> studentList;
    private Context context;
    private LayoutInflater layoutInflater;
    private int score[];
    private int totalScore;
    private boolean doValidate;
    private int count;
    private int error[];
    private volatile boolean isActivated;

    public ActivityInputAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
        score = new int[studentList.size()];
        error = new int[studentList.size()];
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public StudentAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_all_input_cardview, parent, false);
        return new StudentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentAdapterViewHolder holder, int position) {
        count += 1;
        Student student = studentList.get(position);
        holder.setView(student, position);
        Log.i("TESSSSSSSST", "COUNT :" + count + " VALIDATE : " + doValidate );
        Log.i("TESSSSSSSST", "SCORE :" + score[position] + "POSITION :" + position + "STUDENT :" + student.getFirstName());
        if(doValidate) {
               if(score[position] > totalScore || score[position] < 0) {
                   holder.setStatus(false);
               }else {
                   holder.setStatus(true);
               }
        }if(count == studentList.size()) {
            doValidate = false;
            count = 0;
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    @Override
    public void onValidate(boolean doValidate) {
        this.totalScore = totalScore;
        this.doValidate = doValidate;
        notifyDataSetChanged();
    }

    @Override
    public boolean isNoError() {
        int errorNumber = 0;
        for(int i : error) {
            errorNumber += i;
            Log.i("ERROR" , i + "");
        }
        return errorNumber < 1;
    }

    @Override
    public int getScore(int index) {
        return score[index];
    }

    @Override
    public void setTotalItem(int totalScore) {
        this.totalScore = totalScore;
        for(int i=0; i<studentList.size();i++){
            if(score[i] > totalScore)
                error[i] = 1;
            else
                error[i] = 0;
        }
    }

    public class StudentAdapterViewHolder extends RecyclerView.ViewHolder
            implements CompoundButton.OnCheckedChangeListener, RecognitionListener {

        private ImageView studentImage;
        private TextView studentDetail;
        private EditText editText;
        private Student student;
        private int position;
        private LinearLayout layout;
        private  TextView txInit;
        private ToggleButton btnMic;
        private ProgressBar progressBar;

        private SpeechRecognizer speech = null;
        private Intent recognizerIntent;

        StudentAdapterViewHolder(View itemView) {
            super(itemView);
            studentImage = (ImageView) itemView.findViewById(R.id.f_data_student_profile);
            studentDetail = (TextView) itemView.findViewById(R.id.input_cardview_name);
            editText = (EditText) itemView.findViewById(R.id.input_cardview_score);
            layout = (LinearLayout) itemView.findViewById(R.id.input_cardview_layout);
            txInit = (TextView) itemView.findViewById(R.id.input_cardview_init);
            btnMic = (ToggleButton) itemView.findViewById(R.id.input_cardview_mic);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBarney);
            btnMic.setOnCheckedChangeListener(this);

            speech = SpeechRecognizer.createSpeechRecognizer(context);
            speech.setRecognitionListener(this);
            recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        }

        void setView(final Student student, final int position) {
            this.student = student;
            this.position = position;
            String display = String.format(Locale.ENGLISH, "%s, %s %s",
                    student.getLastName(),
                    student.getFirstName(),
                    student.getMiddleName().substring(0, 1));
            studentDetail.setText(display);
            editText.setText(score[position] + "");
            editText.addTextChangedListener(textWatcher);
            String init = student.getLastName().substring(0, 1);
            txInit.setText(init);
        }

        public Student getStudent() {
            return student;
        }

        public int getScore() {
            return Integer.parseInt(!editText.getText().toString().equals("") ? editText.getText().toString() : "0");
        }

        public void setStatus(boolean isSuccess) {
            if(isSuccess)
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorTweeterBlue));
            else
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorLightDanger));
            studentDetail.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }

        private TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                score[position] = getScore();
                if(score[position] > totalScore)
                    error[position] = 1;
                else
                    error[position] = 0;
            }
        };

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
                if (isChecked && !isActivated) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    speech.startListening(recognizerIntent);
                    isActivated = true;
                } else {
                    btnMic.setChecked(false);
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.GONE);
                    speech.stopListening();
                }
            }catch (Exception e) {
                e.printStackTrace();
                speech.stopListening();
            }
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
           ;
        }

        @Override
        public void onBeginningOfSpeech() {
            progressBar.setIndeterminate(false);
            progressBar.setMax(10);
            isActivated = true;
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            progressBar.setProgress((int) rmsdB);
        }

        @Override
        public void onBufferReceived(byte[] buffer) {

        }

        @Override
        public void onEndOfSpeech() {
            progressBar.setIndeterminate(true);
            btnMic.setChecked(false);
        }

        @Override
        public void onError(int error) {
            progressBar.setProgress(0);
            btnMic.setChecked(false);
            isActivated = false;
        }

        @Override
        public void onResults(Bundle results) {
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
            editText.setText(number + "");
            isActivated = false;
        }

        @Override
        public void onPartialResults(Bundle partialResults) {

        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }
}
