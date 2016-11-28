package com.hm.project_glue.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hm.project_glue.R;

public class MainActivity extends AppCompatActivity implements MainPresenter.View{

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenterImpl(MainActivity.this);
        mainPresenter.setView(this);

    }



}
