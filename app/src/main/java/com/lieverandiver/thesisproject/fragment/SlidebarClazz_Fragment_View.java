package com.lieverandiver.thesisproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lieverandiver.thesisproject.R;

/**
 * Created by Verlie on 8/25/2017.
 */

public class SlidebarClazz_Fragment_View extends Fragment {


    public SlidebarClazz_Fragment_View(){


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slidebar_clazz_view, container, false);
        return view;
    }
}
