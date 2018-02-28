package com.lieverandiver.thesisproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.ActivityResult;
import com.remswork.project.alice.model.ExamResult;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.service.ActivityService;
import com.remswork.project.alice.service.ExamService;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;
import com.remswork.project.alice.service.impl.ExamServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ExamResultAdapter extends RecyclerView
        .Adapter<ExamResultAdapter.SimpleExamViewHolder> {

    private LayoutInflater layoutInflater;
    private List<ExamResult> resultList;
    private Context context;
    private ExamAdapter.OnClickListener onClickListener;
    private int totalItem;

    public ExamResultAdapter(Context context, List<ExamResult> resultList, int totalItem) {
        layoutInflater = LayoutInflater.from(context);
        this.resultList = resultList;
        this.context = context;
        this.totalItem = totalItem;
    }

    @Override
    public SimpleExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_all_result_cardview, parent,false);
        return new SimpleExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleExamViewHolder holder, int position) {
        holder.setView(resultList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class SimpleExamViewHolder extends RecyclerView.ViewHolder {
        private TextView txName;
        private TextView txScore;
        private TextView txInit;
        private Button btnSave;
        private Button btnDelete;
        private Button btnCancel;
        LinearLayout laOptionPane;
        ImageView imgThree;
        private ExamResult result;
        private int position;


        SimpleExamViewHolder(View itemView) {
            super(itemView);
            txName = (TextView) itemView.findViewById(R.id.result_cardview_name);
            txScore = (TextView) itemView.findViewById(R.id.result_cardview_score);
            txInit = (TextView) itemView.findViewById(R.id.result_cardview_init);
            laOptionPane = (LinearLayout) itemView.findViewById(R.id.result_linearoption);
            imgThree = (ImageView) itemView.findViewById(R.id.result_option);
            btnSave = (Button) itemView.findViewById(R.id.result_cardview_save);
            btnDelete = (Button) itemView.findViewById(R.id.result_cardview_delete);
            btnCancel = (Button) itemView.findViewById(R.id.result_cardview_cancel);

        }

        void setView(final ExamResult result, final int position) {
            this.result = result;
            this.position = position;
            if(result != null) {
                Student student = result.getStudent();
                String name = String.format("%s, %s %s.",
                        student.getLastName(),
                        student.getFirstName(),
                        student.getMiddleName().substring(0, 1)
                        );
                final String score = String.valueOf(result.getScore());
                String init = student.getLastName().substring(0, 1);

                txName.setText(name);
                txScore.setText(score);
                txInit.setText(init);

                txScore.addTextChangedListener(textWatcher);
                imgThree.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        laOptionPane.setVisibility(View.VISIBLE);
                        txScore.setEnabled(true);
                    }
                });

                btnCancel.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        laOptionPane.setVisibility(View.GONE);
                        txScore.setEnabled(false);
                        txScore.setText(score);
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       getDialog().show();
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            ExamService examService = new ExamServiceImpl();
                            examService.updateExamResultByExamAndStudentId(
                                    Integer.parseInt(txScore.getText().toString()),
                                    result.getExam().getId(), result.getStudent().getId());
                            resultList.get(position).setScore(Integer.parseInt(txScore.getText().toString()));
                            laOptionPane.setVisibility(View.GONE);
                            txScore.setEnabled(false);
                            notifyDataSetChanged();
                        }catch (GradingFactorException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        private AlertDialog getDialog()
        {
            AlertDialog dialog = new AlertDialog.Builder(context)

                    .setTitle("Delete")
                    .setMessage("Do you want to Delete")
                    .setIcon(R.drawable.cancel_icon)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            try{
                                ExamService examService = new ExamServiceImpl();
                                examService.deleteExamResultByExamAndStudentId(
                                        result.getExam().getId(), result.getStudent().getId());
                                List<ExamResult> cResultList = new ArrayList<>();
                                resultList.remove(position);
                                notifyDataSetChanged();
                                for(int i=0;i<resultList.size();i++) {
                                    cResultList.add(resultList.get(i));
                                }
                                resultList = cResultList;
                            }catch (GradingFactorException e){
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }

                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            return dialog;

        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Integer.parseInt(txScore.getText().toString().trim().equals("") ? "0" : txScore.getText().toString().trim()) <= totalItem) {
                    btnSave.setEnabled(true);
                }else
                    btnSave.setEnabled(false);
            }
        };
    }
}
