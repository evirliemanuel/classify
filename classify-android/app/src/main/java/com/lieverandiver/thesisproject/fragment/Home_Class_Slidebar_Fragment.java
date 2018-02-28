package com.lieverandiver.thesisproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lieverandiver.thesisproject.R;

/**
 * Created by Verlie on 8/30/2017.
 */

@Deprecated
public class Home_Class_Slidebar_Fragment extends Fragment {

    public Home_Class_Slidebar_Fragment(){


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_fragment_slidebar_class, container, false);
        return view;
    }
}
