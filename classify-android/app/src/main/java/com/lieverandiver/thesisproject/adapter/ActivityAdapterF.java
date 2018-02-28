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
import com.remswork.project.alice.model.Activity;
import com.remswork.project.alice.model.ActivityResult;
import com.remswork.project.alice.service.ActivityService;
import com.remswork.project.alice.service.impl.ActivityServiceImpl;

import java.nio.InvalidMarkException;
import java.util.ArrayList;
import java.util.List;

import static com.lieverandiver.thesisproject.R.id.textView;

public class ActivityAdapterF extends RecyclerView.Adapter<ActivityAdapterF.ActivityViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Activity> activityList;
    private Context context;
    private OnClickListener onClickListener;

    public ActivityAdapterF(Context context, List<Activity> activityList) {
        layoutInflater = LayoutInflater.from(context);
        this.activityList = activityList;
        if(context instanceof OnClickListener)
            onClickListener = (OnClickListener) context;
        this.context = context;
    }

    @Override
    public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_all_add_cardview, parent, false);
        ActivityViewHolder holder = new ActivityViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ActivityViewHolder holder, int position) {
        Activity activity = activityList.get(position);
        holder.setView(activity, position);
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {

        private EditText textViewTitle;
        private TextView textViewDate;
        private TextView textViewTotal;
        private CardView cardView;
        private Button btnSave;
        private Button btnCancel;
        private Button btnDelete;
        private LinearLayout linearLayoutOption;
        private ImageView btnOption;
        private Activity activity;
        private int position;

        ActivityViewHolder(View itemView) {
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

        public void setView(final Activity activity,final int position) {
            this.activity = activity;
            this.position = position;

            final String title = String.valueOf(activity.getTitle());


            String date = activity.getDate();
            String total = String.valueOf(activity.getItemTotal());

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
                        activity.setTitle(textViewTitle.getText().toString().trim());
                        ActivityService activityService = new ActivityServiceImpl();
                        activityService.updateActivityById(activity.getId(), activity, 0);

                        activityList.get(position).setTitle(textViewTitle.getText().toString());
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
                    onClickListener.onClick(activity, activity.getId());
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
                                ActivityService activityService = new ActivityServiceImpl();
                                activityService.deleteActivityById(activity.getId());
                                List<Activity> cActivityList = new ArrayList<>();
                                activityList.remove(position);
                                notifyDataSetChanged();
                                for(int i=0;i<activityList.size();i++) {
                                    cActivityList.add(activityList.get(i));
                                }
                                activityList = cActivityList;
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
        void onClick(Activity activity, long activityId);
    }
}
