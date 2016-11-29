package com.hm.project_glue.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hm.project_glue.R;
import com.hm.project_glue.main.home.HomeFragment;
import com.hm.project_glue.main.info.InfoFragment;
import com.hm.project_glue.main.list.ListFragment;
import com.hm.project_glue.main.msg.MsgFragment;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    HomeFragment home;
    MsgFragment msg;
    InfoFragment info;
    ListFragment list;
    PagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home =  HomeFragment.newInstance();
        msg  =  MsgFragment.newInstance();
        info =  InfoFragment.newInstance();
        list =  ListFragment.newInstance();
        TabLayout tab = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        tab.addTab(tab.newTab().setIcon(getResources().getDrawable(R.drawable.signidicon)));
        tab.addTab(tab.newTab().setText("MAP"));
        tab.addTab(tab.newTab().setText("ETC"));
        tab.addTab(tab.newTab().setText("settings"));

        adapter = new MainPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }

    // instanceof <-- Fragment 확인
    @Override
    public void onFragmentInteraction(Fragment  fragment) {
        if (fragment instanceof  HomeFragment){
            Toast.makeText(MainActivity.this, "HomeFragment", Toast.LENGTH_SHORT).show();
        }
        else if (fragment instanceof  ListFragment) {
            Toast.makeText(MainActivity.this, "ListFragment", Toast.LENGTH_SHORT).show();
        }
        else if(fragment instanceof  InfoFragment) {
            Toast.makeText(MainActivity.this, "InfoFragment", Toast.LENGTH_SHORT).show();
        }
        else if(fragment instanceof  MsgFragment) {
            Toast.makeText(MainActivity.this, "MsgFragment", Toast.LENGTH_SHORT).show();
        }
    }

    class MainPagerAdapter extends FragmentStatePagerAdapter {
        static final int FRAGMENT_COUNT = 4;
        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position){
                case 0 : fragment = home; break;
                case 1 : fragment = list; break;
                case 2 : fragment = msg; break;
                case 3 : fragment = info; break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
    }
}
