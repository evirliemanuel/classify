package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lieverandiver.thesisproject.adapter.ClassAdapter;
import com.lieverandiver.thesisproject.fragment.Home_Student_Slidebar_Fragment;
import com.lieverandiver.thesisproject.fragment.SliderClassFragment;
import com.lieverandiver.thesisproject.fragment.SliderScheduleFragment;
import com.lieverandiver.thesisproject.fragment.SliderSettingFragment;
import com.remswork.project.alice.model.Teacher;

import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity implements ClassAdapter.ClassAdapterListener,
        SliderSettingFragment.OnProfileClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = MainActivity2.class.getSimpleName();

    private Button btnBack;
    private ToggleButton btnSearch;
    private Button btnSearchOk;
    private Button btnSearchCancel;
    private EditText editTextSearch;
    private FrameLayout frameLayoutSearch;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.small_schedule,
            R.drawable.small_class,
            R.drawable.small_home,
            R.drawable.small_home
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = (ToggleButton) findViewById(R.id.btn_search_class);
        btnSearchOk = (Button) findViewById(R.id._btn_search_ok_class);
        btnBack = (Button) findViewById(R.id.btn_back_student);

        editTextSearch = (EditText) findViewById(R.id.etxt_search_class);
        frameLayoutSearch = (FrameLayout) findViewById(R.id.frame_search_class);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        frameLayoutSearch.setVisibility(View.GONE);
        btnSearch.setOnCheckedChangeListener(this);


    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();

        btnSearch = null;
        btnSearchOk = null;
        btnBack = null;

        editTextSearch = null;
        frameLayoutSearch = null;
        tabLayout = null;
        viewPager = null;

    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Schedule");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.small_schedule, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Class");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.small_class, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Student");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.small_home, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Setting");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.small_home, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SliderScheduleFragment(), "Shedule");
        adapter.addFragment(new SliderClassFragment(), "Class");
        adapter.addFragment(new Home_Student_Slidebar_Fragment(), "Student");
        adapter.addFragment(new SliderSettingFragment(), "Setting");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void showClassView(final long classId) {
        final Intent intent = new Intent(this, ClassViewActivity.class);
        intent.putExtra("classId", classId);
        startActivity(intent);
    }

    @Override
    public void viewProfile(final Teacher teacher) {
        final Intent intent = new Intent(this, TeacherViewActivity.class);
        intent.putExtra("teacherId", teacher.getId());
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            frameLayoutSearch.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.GONE);
        } else {
            frameLayoutSearch.setVisibility(View.GONE);
            tabLayout.setVisibility(View.VISIBLE);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
