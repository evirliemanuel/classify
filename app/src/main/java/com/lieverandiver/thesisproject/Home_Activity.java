package com.lieverandiver.thesisproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import com.lieverandiver.thesisproject.adapter.ClassAdapter;
import com.lieverandiver.thesisproject.fragment.Home_Student_Slidebar_Fragment;
import com.lieverandiver.thesisproject.fragment.SliderClassFragment;
import com.lieverandiver.thesisproject.fragment.SliderScheduleFragment;
import com.lieverandiver.thesisproject.fragment.SliderSettingFragment;
import com.remswork.project.alice.model.Teacher;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class Home_Activity extends AppCompatActivity implements ClassAdapter.ClassAdapterListener,
        SliderSettingFragment.OnProfileClickListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button btnBack;
    private ToggleButton btnSearch;
    private Button btnSearchOk;
    private Button btnSearchCancel;
    private EditText editTextSearch;
    private FrameLayout frameLayoutSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        btnSearch = (ToggleButton)findViewById(R.id.btn_search_class);
        btnSearchOk = (Button)findViewById(R.id._btn_search_ok_class);
        btnBack = (Button) findViewById(R.id.btn_back_student);
        editTextSearch =(EditText)findViewById(R.id.etxt_search_class);
        frameLayoutSearch = (FrameLayout)findViewById(R.id.frame_search_class);

        frameLayoutSearch.setVisibility(View.GONE);

        btnSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    frameLayoutSearch.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                } else {
                    frameLayoutSearch.setVisibility(View.GONE);
                    tabLayout.setVisibility(View.VISIBLE);
                }
            }
        });
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
        Intent intent = new Intent(this, ClassViewActivity.class);
        intent.putExtra("classId", classId);
        startActivity(intent);
    }

    @Override
    public void viewProfile(Teacher teacher) {
        Intent intent = new Intent(this, TeacherViewActivity.class);
        intent.putExtra("teacherId", teacher.getId());
        startActivity(intent);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
