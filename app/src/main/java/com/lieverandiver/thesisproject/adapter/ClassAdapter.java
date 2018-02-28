package com.lieverandiver.thesisproject.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lieverandiver.thesisproject.R;
import com.remswork.project.alice.model.Class;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassAdapterViewHolder> {

    private List<Class> classList;
    private Context context;
    private LayoutInflater layoutInflater;
    private ClassAdapterListener classAdapterListener;
    private boolean isGo = true;

    public ClassAdapter(Context context, List<Class> classList) {
        this.context = context;
        this.classList = classList;
        layoutInflater = LayoutInflater.from(context);
        if(context instanceof ClassAdapterListener)
            classAdapterListener = (ClassAdapterListener) context;
    }

    @Override
    public ClassAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_data_clazz, parent, false);
        return new ClassAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClassAdapterViewHolder holder, int position) {
        holder.setView(classList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

      class ClassAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private ImageView imageView;
            private TextView textViewSubject;
            private TextView textViewSimpleText;
            private ImageView buttonClick;
            private RelativeLayout imageScreen;
            private Handler handler;
            private Class _class;

            ClassAdapterViewHolder(View itemView) {
                super(itemView);
                textViewSubject = (TextView) itemView.findViewById(
                        R.id.fragment_slidebar_cardview_clazz_text_subject);
                textViewSimpleText = (TextView) itemView.findViewById(
                        R.id.fragment_slidebar_cardview_clazz_text_section);
                imageScreen = (RelativeLayout) itemView.findViewById(
                        R.id.class_cardview_progressbar_layout);
                buttonClick = (ImageView) itemView.findViewById(R.id.button_click);
                handler = new Handler(context.getMainLooper());
            }

        void setView(final Class _class, final int position) {
            this._class = _class;
            imageScreen.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(_class.getSubject() != null)
                                textViewSubject.setText(_class.getSubject().getName());
                            if(_class.getSection() != null)
                                textViewSimpleText.setText(_class.getSection().getName());
                            buttonClick.setOnClickListener(ClassAdapterViewHolder.this);
                            imageScreen.setVisibility(View.GONE);
                        }
                    });
                }
            }).start();
        }

          @Override
          public void onClick(View v) {
              if (isGo) {
                  isGo = false;
                  new Thread(new Runnable() {
                      @Override
                      public void run() {
                          handler.post(new Runnable() {
                              @Override
                              public void run() {
                                  imageScreen.setVisibility(View.VISIBLE);
                              }
                          });
                          try {
                              Thread.sleep(1200);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                          classAdapterListener.showClassView(_class.getId());
                          isGo = true;
                          handler.post(new Runnable() {
                              @Override
                              public void run() {
                                  imageScreen.setVisibility(View.GONE);
                              }
                          });

                      }
                  }).start();
              }
          }
      }

    public interface ClassAdapterListener {
        void showClassView(long id);
    }
}
