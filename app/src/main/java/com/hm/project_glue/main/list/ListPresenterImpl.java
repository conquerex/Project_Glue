package com.hm.project_glue.main.list;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HM on 2016-11-29.
 */
//conn.setRequestProperty("Value", "test");
public class ListPresenterImpl implements ListPresenter {
    private ListFragment fragment;
    private ListModel listInModel;
    private ListPresenter.View view;
    private static final String TAG = "TEST";
    Context context;
    public ListPresenterImpl(ListFragment fragment) {
        this.fragment=fragment;
        listInModel = new ListModel(fragment.getContext());
        context = fragment.getActivity();

    }

    @Override
    public void setView(View view) {
        this.view = view;
    }


    public void getPostJson(String groupId) {
        HashMap userInfoMap = new HashMap();


        new AsyncTask<Map, Void, String>() {


            @Override
            protected String doInBackground(Map... params) {
                String result = "";
                try {
                    result = ListModel.groupPostListData(groupId, params[0]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                try {
                    String arrayResult = "{\"array\":"+result+"}";
                    JSONObject jsonResult = new JSONObject(arrayResult);
                    JSONArray jsonArray = jsonResult.getJSONArray("array");

                    int length = jsonArray.length();
//                    for(int i=0; i < length; i ++){
//                        PostData data = new PostData();
//                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//
//                        data.content = jsonObject.getString("content");
//                        data.group = jsonObject.getString("group");
//                        data.uploaded_user = jsonObject.getString("uploaded_user");
//                        data.photos = jsonObject.get("photos");
//
//
//
////
////                        sb.append(trainName + "\n");
//
//                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }.execute(userInfoMap);
    }

}
