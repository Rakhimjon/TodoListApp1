package com.doubletechnology.todolistapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    public PendingFragment pendingFragment;
    public DoneFragment doneFragment;

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    String [] todo_indc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);


        todo_indc = getResources().getStringArray(R.array.todo_indc);

        pendingFragment = new PendingFragment();
        Bundle bundle = new Bundle();
        pendingFragment.setArguments(bundle);

        doneFragment = new DoneFragment();
        Bundle bundle1 = new Bundle();
        doneFragment.setArguments(bundle);

        viewPager.setAdapter(new TodoAdapter());
        tabLayout.setupWithViewPager(viewPager);


    }

    public void addDoneText(String s){
        doneFragment.addString(s);

    }

    private class TodoAdapter extends FragmentPagerAdapter {

        public TodoAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){

                return pendingFragment;
            }else {
                return doneFragment;
            }
        }

        @Override
        public int getCount() {
            return todo_indc.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return todo_indc[position];
        }
    }
}
