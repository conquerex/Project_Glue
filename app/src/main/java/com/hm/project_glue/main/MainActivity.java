package com.hm.project_glue.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.hm.project_glue.R;
import com.hm.project_glue.main.home.HomeFragment;
import com.hm.project_glue.main.info.InfoFragment;
import com.hm.project_glue.main.list.ListFragment;
import com.hm.project_glue.main.post.PostDetailFragment;
import com.hm.project_glue.main.msg.MsgFragment;
import com.hm.project_glue.main.timeline.TimelineFragment;
import com.hm.project_glue.sign.SignActivity;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.addGroup.AddGroupActivity;
import com.hm.project_glue.util.write.WriteActivity;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private CallbackManager callbackManager;
    public static DisplayMetrics metrics;
    public static Context mainContext;
    private HomeFragment home;
    private MsgFragment msg;
    private InfoFragment info;
    private ListFragment list;
    private PostDetailFragment post;
    private TimelineFragment time;
    private Networking networking;
    private TabLayout tab;
    private int MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE =11;
    public boolean myPermissions = false;
    public  ViewPager pager;
    private final int facebookResultCode = -1,galleyResultCode = 2;
    private final int addGroupCode = 3;
    public static String TAG = "TEST";
    public FrameLayout mainframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.listToolbar);

        setSupportActionBar(toolbar);

        if(savedInstanceState!=null) {
            finish();
            return;
        }
        setContentView(R.layout.activity_main);

        networking = new Networking(this);
        home =  HomeFragment.newInstance();
        msg  =  MsgFragment.newInstance();
        info =  InfoFragment.newInstance();
        time =  TimelineFragment.newInstance();
        mainframe = (FrameLayout) findViewById(R.id.mainframe);
        tab = (TabLayout) findViewById(R.id.tabLayout);
        pager = (ViewPager) findViewById(R.id.pager);
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

        PagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

        metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);




    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
                case 1 : fragment = time; break;
                case 2 : fragment = msg;
                    msg.notifyDataSetChanged();
                    break;
                case 3 : fragment = info; break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
    }


    // 2016.12.20 galleyActivity 재사용을 위해 int로 구분
    public void galleyActivity(int code) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, code);
    }


    @Override
    public void goToListFragment(String groupId){
        list =  ListFragment.newInstance(groupId);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.gla_there_come,R.anim.gla_there_gone);
        ft.replace(R.id.mainframe, list);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void goToPostFragment(String postId) {
        post = PostDetailFragment.newInstance(postId);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.gla_there_gone,R.anim.gla_there_come);
        ft.replace(R.id.mainframe, post);
        ft.addToBackStack(null);
        ft.commit();
    }


    public void moveActivity(int activityCode){
        Intent i = null;
        switch (activityCode){
            case 1 :
                i = new Intent(MainActivity.this, SignActivity.class);
                break;
            case 2 :
                i = new Intent(MainActivity.this, WriteActivity.class);
                break;
            case 3 :
                i = new Intent(MainActivity.this, AddGroupActivity.class);
                startActivityForResult(i, 3);
                break;
        }
        startActivity(i);
        overridePendingTransition(R.anim.ani_there_up_come, R.anim.gla_there_come);
    }
    @Override   //facebook(-1) , PhotoDetail ( 2 )
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1 :
                break;
            case galleyResultCode :
                Log.i(TAG, "galleyResultCode");
                if(data != null){
                    Uri imageUri = data.getData();    // Intent에서 받아온 갤러리 URI
                    String selections[] = { MediaStore.Images.Media.DATA}; // 실제 이미지 패스 데이터
                    if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                    }
                    else {
//                        checkPermissions();
                    }
                    Cursor cursor = getContentResolver().query(imageUri, selections, null,null,null);
                    if(cursor.moveToNext()){
                        String imagePath = cursor.getString(0);                        // 사이즈 지정 옵션
                        info.setBitmap(imagePath);
                        Log.i(TAG, "info.setBitmap(imagePath)");
                    }
                }
                break;
            case facebookResultCode :
                callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
            case addGroupCode :

                Log.i(TAG, "galleyResultCode");
                break;
        }
    }

    public void logOut() { // Facebook 로그아웃, 프리퍼런스 값 초기화, activity 이동
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();    // 페이스북 로그아웃
        networking.logout();                    //프리퍼런스 초기화
        int SignActivity = 1;
        moveActivity(SignActivity);
    }

    public void setTabBar(int setTabBarCode){

        switch (setTabBarCode){
            case 1 :
                tab.setVisibility(View.VISIBLE);
                break;
            case 2 :
                Log.i(TAG, "tab 2");
                tab.setVisibility(View.GONE);
                break;
        }
    }

    public void permissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                // 동의 메세지
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE);
            }
        }else{
            myPermissions = true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 11 :
                // 수락
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myPermissions = true;
                    galleyActivity(11);
                } else { //거절
                    myPermissions = false;
                }
                return;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toolbar toolbar = (Toolbar) findViewById(R.id.listToolbar);
        setSupportActionBar(toolbar);
    }
}
