package com.hm.project_glue.main.post;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hm.project_glue.R;
import com.hm.project_glue.main.OnFragmentInteractionListener;
import com.hm.project_glue.main.list.ListPhotoAdapter;
import com.hm.project_glue.main.list.data.Comments;
import com.hm.project_glue.main.list.data.Photos;
import com.hm.project_glue.main.list.data.Posts;
import com.hm.project_glue.util.Util;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class PostDetailFragment extends Fragment implements Presenter.View {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private OnFragmentInteractionListener mListener;
    PostPresenterImpl presenter;
    PostDetailAdapter adapter;
    ListView listView;
    Posts post;
    ArrayList<Comments> datas;

    ProgressBar proBar;
    LinearLayout viewLinear;
    ImageView userImg;
    Button btnComment,btnCommentSave;
    EditText etComment;
    GridView gridView;
    TextView groupName, contents, time, userName, likeCount, dislikeCount, commentCount;
    public PostDetailFragment() {

    }

    public static PostDetailFragment newInstance(String param1) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        presenter = new PostPresenterImpl(PostDetailFragment.this);
        presenter.setView(this);
        post = new Posts();
        datas = new ArrayList<>();


    }

    @Override
    public void onResume() {
        super.onResume();
        // 셋팅 후에 불러와야 쓰레드 충돌 안남
        presenter.callHttpPostDetail(mParam1);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_post_detail, container, false);
        userImg = (ImageView) view.findViewById(R.id.post_userImg);
        gridView    = (GridView) view.findViewById(R.id.post_gridView);
        groupName   = (TextView) view.findViewById(R.id.post_groupName);
        contents    = (TextView) view.findViewById(R.id.post_tvContents);
        etComment    = (EditText) view.findViewById(R.id.post_etComment);
        btnComment = (Button) view.findViewById(R.id.post_btnComment);
        btnCommentSave = (Button) view.findViewById(R.id.post_btnSave);
        LinearLayout linearComment = (LinearLayout) view.findViewById(R.id.post_editCommet);

        btnComment.setOnClickListener(v->{
            linearComment.setVisibility(View.VISIBLE);
        });
        btnCommentSave.setOnClickListener(v->{
            Util.keyboardOff(v);
            if(etComment.getText().length() > 0){
                String str = etComment.getText().toString();
                presenter.callHttpCommentCreate(mParam1, str);
            }
        });



        time        = (TextView) view.findViewById(R.id.post_tvTime);
        userName    = (TextView) view.findViewById(R.id.post_tvUserName);
        likeCount   = (TextView) view.findViewById(R.id.post_tvLIkecCount);
        dislikeCount = (TextView) view.findViewById(R.id.post_tvDislikeCount);
        commentCount = (TextView) view.findViewById(R.id.post_tvCommentCount);
        viewLinear = (LinearLayout) view.findViewById(R.id.post_viewLinear);
        proBar = (ProgressBar) view.findViewById(R.id.post_proBar);
        listView = (ListView) view.findViewById(R.id.post_commentListView);
        adapter = new PostDetailAdapter(getContext(), datas, R.layout.post_comment_item);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void dataSet(Posts res){
        if(res != null){
            datas.clear();
            datas.addAll(res.getComments());
            groupName.setText(res.getGroup().getGroup_name());
            if(res.getContent()!=null){
                contents.setText(res.getContent());
            }
            if(res.getUser().getName()!=null){
                String s = res.getUser().getName();
                userName.setText(s);
            }

            likeCount.setText(res.getLikes_count());
            dislikeCount.setText(res.getDislikes_count());
            commentCount.setText(res.getComments().size()+"");

            if(res.getUser().getImage()!=null){
                Glide.with(getContext()).load(res.getUser().getImage()).override(70,70)
                        .bitmapTransform(new CropCircleTransformation(getContext())).into(userImg);
            }else{
                Glide.with(getContext()).load(R.drawable.noprofile).override(70,70)
                        .bitmapTransform(new CropCircleTransformation(getContext())).into(userImg);
            }
            if(res.getPhotos().size() ==0){
                gridView.setVisibility(View.GONE);
            }else{
                gridView.setNumColumns(1);
                ArrayList<String> photosurl = new ArrayList<>();
                for(Photos s : res.getPhotos()){
                    photosurl.add(s.getPhoto().getFull_size());
                }
                gridView.setVisibility(View.VISIBLE);
                ListPhotoAdapter timePhotoListAdapter = new ListPhotoAdapter(getContext(),photosurl, R.layout.list_photo_list_item);
                gridView.setAdapter(timePhotoListAdapter);
            }
            proBar.setVisibility(View.GONE);

            Animation animation = new AlphaAnimation(0, 1);
            animation.setDuration(1000);
            viewLinear.setVisibility(View.VISIBLE);
            viewLinear.setAnimation(animation);


        }else{

        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void commentChanged() {
        presenter.callHttpPostDetail(mParam1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
