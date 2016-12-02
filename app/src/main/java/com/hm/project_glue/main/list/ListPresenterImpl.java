package com.hm.project_glue.main.list;

import android.content.Context;

import com.hm.project_glue.Util.Networking;

/**
 * Created by HM on 2016-11-29.
 */
//conn.setRequestProperty("Value", "test");
public class ListPresenterImpl implements ListPresenter {
    private ListFragment fragment;
    private ListModel listInModel;
    private ListPresenter.View view;
    Context context;
    public ListPresenterImpl(ListFragment fragment) {
        this.fragment=fragment;
        listInModel = new ListModel(fragment.getContext());
        context = fragment.getActivity();
        Networking.getToken();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }




}
