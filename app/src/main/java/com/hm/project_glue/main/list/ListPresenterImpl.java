package com.hm.project_glue.main.list;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hm.project_glue.main.list.data.Photo;
import com.hm.project_glue.main.list.data.Photos;
import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.main.list.data.Results;
import com.hm.project_glue.util.Networking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


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
    PostData postData;

    public ListPresenterImpl(ListFragment fragment) {
        this.fragment=fragment;
        listInModel = new ListModel(fragment.getContext());
        context = fragment.getActivity();

    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void callHttp(PostData post, String GroupId){
        this.postData = post;

        ProgressDialog progress = new ProgressDialog(context);
         new AsyncTask<String, Void, PostData>(){
            @Override
            protected PostData doInBackground(String... params) {

                String authorization = "Token "+ Networking.getToken();
                Map<String, String> queryMap = new HashMap<>();
                queryMap.put("page", "1");
                try {
                    final Call<PostData> response = ListRestAdapter.getInstance().getListData(authorization, GroupId, queryMap);
                    Log.i(TAG, "final Call<PostData> ");
                    PostData getData = response.execute().body();
                    postData.setCount(getData.getCount());
                    postData.setNext(getData.getNext());
                    postData.setPrevious(getData.getPrevious());
                    postData.setResults(getData.getResults());
                    ArrayList<Results> resultsList = getData.getResults();
                    for(Results getResults : resultsList){
                        Results results = new Results();
                        results.setContent(getResults.getContent());
                        results.setUploaded_user(getResults.getUploaded_user());
                        results.setLikes_count(getResults.getLikes_count());
                        results.setLike(getResults.getLike());
                        results.setPk(getResults.getPk());
                        ArrayList<Photos> photosList = getResults.getPhotos();
                        for(Photos getPhotos : photosList){
                            Photos photos = new Photos();
                            Photo photo = new Photo();
                            photo.setMedium_thumbnail(getPhotos.getPhoto().getMedium_thumbnail());
                            photo.setSmall_thumbnail(getPhotos.getPhoto().getSmall_thumbnail());
                            photo.setFull_size(getPhotos.getPhoto().getFull_size());
                            photo.setThumbnail(getPhotos.getPhoto().getThumbnail());
                            photos.setPhotos(photo);
                            photos.setPk(getPhotos.getPk());
                            photosList.add(photos);
                        }
                        results.setPhotos(photosList);
                        resultsList.add(results);

                    }
                    postData.setResults(resultsList);
//                    for (Results result : body.getResults()) { // 게시물 단위
//                        Log.i(TAG, "Results result : item.getResults()");
//                        Results data = new Results();
//                        data.setContent(result.getContent());
//                        data.setUploaded_user(result.getUploaded_user());
//                        data.setLikes_count(result.getLikes_count());
//                        data.setGroup(result.getGroup());
//                        data.setPhotos(result.getPhotos());
//                        Log.i(TAG, "post.add");
//                        post.add(data);
////                        int photoSize = result.getPhotos().size();
////                        for (int i = 1; i < photoSize; i++) {
////                            Photos photos = new Photos();
////                            Photo photo = new Photo();
////                            if (result.getPhotos().get(i).getPhoto().getFull_size() != null) {
////                                photo.setFull_size(result.getPhotos().get(i).getPhoto().getFull_size());
////                                photos.setPhotos(photo);
////                                result.getPhotos().add(photos);
////                            } else {
////                                photo.setFull_size("");
////                                photos.setPhotos(photo);
////                                result.getPhotos().add(photos);
////                            }
////                            data.getPhotos().add(photos);
////                        }
//
//
//    //                        for(Photos photoMember : result.getPhotos()){
//    //
//    //                            Photo photo = new Photo();
//    //                            photo.setFull_size(photoMember.getPhoto().getFull_size());
//    //
//    //                            Photos photos = new Photos();
//    //                            photos.setPhotos(photo);
//    //
//    //                            data.getPhotos().add(photos);
//    //                        }
//
//                    }
                    Log.i(TAG,"results");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return postData;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //TODO  메인쓰레드 프로그래스 바 보여주기
                progress.setMessage("Loging....");
                progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
                progress.setCancelable(false);
                progress.show();
            }
            @Override
            protected void onPostExecute(PostData postData) {
                super.onPostExecute(postData);
                progress.dismiss();
                view.dataChanged(postData);

            }
        }.execute();



    }





}
