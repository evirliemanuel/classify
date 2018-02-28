package com.lieverandiver.thesisproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Exam;
import com.remswork.project.alice.model.ExamResult;
import com.remswork.project.alice.service.ExamService;
import com.remswork.project.alice.service.impl.ExamServiceImpl;

import java.nio.InvalidMarkException;
import java.util.ArrayList;
import java.util.List;

import static com.lieverandiver.thesisproject.R.id.textView;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Exam> examList;
    private Context context;
    private OnClickListener onClickListener;

    public ExamAdapter(Context context, List<Exam> examList) {
        layoutInflater = LayoutInflater.from(context);
        this.examList = examList;
        if(context instanceof OnClickListener)
            onClickListener = (OnClickListener) context;
        this.context = context;
    }

    @Override
    public ExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_all_add_cardview, parent, false);
        ExamViewHolder holder = new ExamViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ExamViewHolder holder, int position) {
        Exam exam = examList.get(position);
        holder.setView(exam, position);
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public class ExamViewHolder extends RecyclerView.ViewHolder {

        private EditText textViewTitle;
        private TextView textViewDate;
        private TextView textViewTotal;
        private CardView cardView;
        private Button btnSave;
        private Button btnCancel;
        private Button btnDelete;
        private LinearLayout linearLayoutOption;
        private ImageView btnOption;
        private Exam exam;
        private int position;

        ExamViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (EditText) itemView.findViewById(R.id.add_cardview_title);
            textViewDate = (TextView) itemView.findViewById(R.id.add_cardview_date);
            textViewTotal = (TextView) itemView.findViewById(R.id.add_cardview_total);
            cardView = (CardView) itemView.findViewById(R.id.add_cardview_cardview);

            btnSave = (Button) itemView.findViewById(R.id.add_cardview_save);
            btnCancel = (Button) itemView.findViewById(R.id.add_cardview_cancel);
            btnDelete = (Button) itemView.findViewById(R.id.add_cardview_delete);
            linearLayoutOption = (LinearLayout) itemView.findViewById(R.id.add_linearoption);
            btnOption = (ImageView) itemView.findViewById(R.id.add_option);
        }

        public void setView(final Exam exam,final int position) {
            this.exam = exam;
            this.position = position;

            final String title = String.valueOf(exam.getTitle());


            String date = exam.getDate();
            String total = String.valueOf(exam.getItemTotal());

            textViewTitle.setText(title);
            textViewDate.setText(date);
            textViewTotal.setText(total);
            textViewTitle.addTextChangedListener(textWatcher);

            btnOption.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    linearLayoutOption.setVisibility(View.VISIBLE);
                    textViewTitle.setEnabled(true);
                }
            });

            btnCancel.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    linearLayoutOption.setVisibility(View.GONE);
                    textViewTitle.setEnabled(false);
                    textViewTitle.setText(title);
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        exam.setTitle(textViewTitle.getText().toString().trim());
                        ExamService examService = new ExamServiceImpl();
                        examService.updateExamById(exam.getId(), exam, 0);

                        examList.get(position).setTitle(textViewTitle.getText().toString());
                        linearLayoutOption.setVisibility(View.GONE);
                        textViewTitle.setEnabled(false);
                        notifyDataSetChanged();
                    }catch (GradingFactorException e){
                        e.printStackTrace();
                    }
                }
            });

            cardView.setOnClickListener(new Button.OnClickListener(){

                @Override
                public void onClick(View v) {
                    onClickListener.onClick(exam, exam.getId());
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().show();
                }
            });
        }

        private AlertDialog getDialog()
        {
            AlertDialog dialog = new AlertDialog.Builder(context)

                    .setTitle("Delete")
                    .setMessage("Do you want to Delete")
                    .setIcon(R.drawable.delete)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            try{
                                ExamService examService = new ExamServiceImpl();
                                examService.deleteExamById(exam.getId());
                                List<Exam> cExamList = new ArrayList<>();
                                examList.remove(position);
                                notifyDataSetChanged();
                                for(int i=0;i<examList.size();i++) {
                                    cExamList.add(examList.get(i));
                                }
                                examList = cExamList;
                                linearLayoutOption.setVisibility(View.GONE);
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

                if (textViewTitle.getText().toString().trim().equals("")) {
                    btnSave.setEnabled(false);
                }else{
                    btnSave.setEnabled(true);
                }

            }
        };
    }

    public interface OnClickListener {
        void onClick(Exam exam, long examId);
    }
}
