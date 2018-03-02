package io.classify;

import android.app.Application;
import android.os.Bundle;

import javax.inject.Inject;

import io.ermdev.classify.R;
import io.classify.ui.BasicActivity;

public class MainActivity extends BasicActivity {

    @Inject
    Application application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appComponent.inject(this);
    }
}
