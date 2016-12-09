package com.hm.project_glue.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.hm.project_glue.R;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.main.home.HomeFragment;
import com.hm.project_glue.main.info.InfoFragment;
import com.hm.project_glue.main.list.ListFragment;
import com.hm.project_glue.main.msg.MsgFragment;
import com.hm.project_glue.sign.SignActivity;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{
    private CallbackManager callbackManager;
    public static DisplayMetrics metrics;
    HomeFragment home;
    MsgFragment msg;
    InfoFragment info;
    ListFragment list;
    PagerAdapter adapter;
    Networking networking;
    TabLayout tab;
    private final String PreferenceName ="localLoginCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networking = new Networking(this);
        home =  HomeFragment.newInstance();
        msg  =  MsgFragment.newInstance();
        info =  InfoFragment.newInstance();
        list =  ListFragment.newInstance();
        tab = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        tab.addTab(tab.newTab().setIcon(R.mipmap.ic_supervisor_account_white_36dp));
        tab.addTab(tab.newTab().setIcon(R.mipmap.ic_photo_library_gray_36dp));
        tab.addTab(tab.newTab().setIcon(R.mipmap.ic_sms_gray_36dp));
        tab.addTab(tab.newTab().setIcon(R.mipmap.ic_account_circle_gray_36dp));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab selectedtab) {
                tab.getTabAt(0).setIcon(R.mipmap.ic_supervisor_account_gray_36dp);
                tab.getTabAt(1).setIcon(R.mipmap.ic_photo_library_gray_36dp);
                tab.getTabAt(2).setIcon(R.mipmap.ic_sms_gray_36dp);
                tab.getTabAt(3).setIcon(R.mipmap.ic_account_circle_gray_36dp);
                switch (selectedtab.getPosition()) {
                    case 0 : selectedtab.setIcon(R.mipmap.ic_supervisor_account_white_36dp);
                        break;
                    case 1 : selectedtab.setIcon(R.mipmap.ic_photo_library_white_36dp);
                        break;
                    case 2 : selectedtab.setIcon(R.mipmap.ic_sms_white_36dp);
                        break;
                    case 3 : selectedtab.setIcon(R.mipmap.ic_account_circle_white_36dp);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        adapter = new MainPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

        metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
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

    public void moveActivity(){
        Intent i = new Intent(MainActivity.this, SignActivity.class);
        startActivity(i);
    }
    @Override   //facebook
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void logOut() { // Facebook 로그아웃, 프리퍼런스 값 초기화, activity 이동
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();    // 페이스북 로그아웃
        networking.logout();                    //프리퍼런스 초기화
        moveActivity();
    }

}
