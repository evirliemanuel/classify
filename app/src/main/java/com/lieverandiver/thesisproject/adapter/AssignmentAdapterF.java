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
import com.remswork.project.alice.model.Assignment;
import com.remswork.project.alice.model.AssignmentResult;
import com.remswork.project.alice.service.AssignmentService;
import com.remswork.project.alice.service.impl.AssignmentServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class AssignmentAdapterF extends RecyclerView.Adapter<AssignmentAdapterF.AssignmentViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Assignment> assignmentList;
    private Context context;
    private OnClickListener onClickListener;

    public AssignmentAdapterF(Context context, List<Assignment> assignmentList) {
        layoutInflater = LayoutInflater.from(context);
        this.assignmentList = assignmentList;
        if(context instanceof OnClickListener)
            onClickListener = (OnClickListener) context;
        this.context = context;
    }

    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_all_add_cardview, parent, false);
        AssignmentViewHolder holder = new AssignmentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AssignmentViewHolder holder, int position) {
        Assignment assignment = assignmentList.get(position);
        holder.setView(assignment, position);
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {

        private EditText textViewTitle;
        private TextView textViewDate;
        private TextView textViewTotal;
        private CardView cardView;
        private Button btnSave;
        private Button btnCancel;
        private Button btnDelete;
        private LinearLayout linearLayoutOption;
        private ImageView btnOption;
        private Assignment assignment;
        private int position;

        AssignmentViewHolder(View itemView) {
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

        public void setView(final Assignment assignment,final int position) {
            this.assignment = assignment;
            this.position = position;

            final String title = String.valueOf(assignment.getTitle());


            String date = assignment.getDate();
            String total = String.valueOf(assignment.getItemTotal());

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
                        assignment.setTitle(textViewTitle.getText().toString().trim());
                        AssignmentService assignmentService = new AssignmentServiceImpl();
                        assignmentService.updateAssignmentById(assignment.getId(), assignment, 0);

                        assignmentList.get(position).setTitle(textViewTitle.getText().toString());
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
                    onClickListener.onClick(assignment, assignment.getId());
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
                                AssignmentService assignmentService = new AssignmentServiceImpl();
                                assignmentService.deleteAssignmentById(assignment.getId());
                                List<Assignment> cAssignmentList = new ArrayList<>();
                                assignmentList.remove(position);
                                notifyDataSetChanged();
                                for(int i=0;i<assignmentList.size();i++) {
                                    cAssignmentList.add(assignmentList.get(i));
                                }
                                assignmentList = cAssignmentList;
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
        void onClick(Assignment assignment, long assignmentId);
    }
}
