package com.hm.project_glue.main;

import android.app.Activity;



public class MainPresenterImpl implements MainPresenter {

    private Activity activity;
    private MainModel mainModel;
    private MainPresenter.View view;

    public MainPresenterImpl(Activity activity) {
        this.activity = activity;
        this.mainModel = new MainModel();

        

    }

    @Override
    public void setView(MainPresenter.View view) {
        this.view = view;
    }


}
