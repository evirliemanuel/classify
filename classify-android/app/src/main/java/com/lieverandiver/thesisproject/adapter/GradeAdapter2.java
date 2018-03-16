package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evm.project.schapp.helper.GradeHelper;
import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Grade;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import io.classify.model.Mark;

public class GradeAdapter2 extends RecyclerView.Adapter<GradeAdapter2.GradeViewHolder> {

    private Context context;
    private List<Mark> gradeList;
    private LayoutInflater layoutInflater;

    public GradeAdapter2(Context context, List<Mark> gradeList, long termId) {
        this.gradeList = gradeList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_all_total_grade_cardview, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeViewHolder holder, int position) {
        holder.setView(gradeList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    class GradeViewHolder extends RecyclerView.ViewHolder {

        private TextView txtGrdScore;
        private TextView txtGrdScore2;
        private TextView txtEquiv;
        private TextView txName;
        private TextView txDate;

        public GradeViewHolder(View itemView) {
            super(itemView);
            txtGrdScore = (TextView) itemView.findViewById(R.id.grade_score_part);
            txtGrdScore2 = (TextView) itemView.findViewById(R.id.grade_score_part2);
            txtEquiv = (TextView) itemView.findViewById(R.id.grade_score_part3);
            txName = (TextView) itemView.findViewById(R.id.grade_studentname);
            txDate = (TextView) itemView.findViewById(R.id.grade_update_date);

        }

        public void setView(final Mark mark, final int position) {
            String name = String.format("%s, %s %s", mark.getMidterm().getStudent().getLastName(),
                    mark.getMidterm().getStudent().getFirstName(),
                    mark.getMidterm().getStudent().getMiddleName().substring(0, 1)) + ".";
            DecimalFormat format = new DecimalFormat("#.##");
            format.setRoundingMode(RoundingMode.UP);
            String score = String.format(Locale.ENGLISH, "%s", format.format(mark.getMidterm().getTotalScore()));
            String score2 = String.format(Locale.ENGLISH, "%s", format.format(mark.getFinalterm().getTotalScore()));
            txName.setText(name);
            txtGrdScore.setText(score);
            txtGrdScore2.setText(score2);
            txtEquiv.setText(GradeHelper.calculate(mark.getFinalterm().getTotalScore()));
        }
    }
}
