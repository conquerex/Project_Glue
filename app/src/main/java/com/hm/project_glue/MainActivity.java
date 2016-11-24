package com.hm.project_glue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 임시로 레이아웃을 fragment_sign_up로 변경
        setContentView(R.layout.fragment_sign_up);
    }
}
