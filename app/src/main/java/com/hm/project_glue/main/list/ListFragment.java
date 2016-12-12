package com.hm.project_glue.main.list;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hm.project_glue.R;
import com.hm.project_glue.main.MainActivity;
import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.main.list.data.Results;

import java.util.ArrayList;

public class ListFragment extends Fragment implements ListPresenter.View {

    private ListPresenter listPresenter;
    public static ArrayList<Results> datas = null;
    RecyclerView listRecyclerView;
    RecyclerCardAdapter adapter;
    PostData post;
    View view;
    private static final String TAG = "TEST";

    public ListFragment() {
        Log.i(TAG, "ListFragment");
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){

            return;
        }
        listPresenter = new ListPresenterImpl(ListFragment.this);

        listPresenter.setView(this);
        post = PostData.newPostInstance();
        datas = new ArrayList<>();

        Log.i(TAG,"onCreate");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG,"onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        if(savedInstanceState==null){
            listPresenter.callHttp(post, "1");
        }
        listRecyclerView = (RecyclerView) view.findViewById(R.id.recylerCardView);
        adapter  = new RecyclerCardAdapter(datas,R.layout.list_recycler_card_item,getContext());
        listRecyclerView.setAdapter(adapter);
        FloatingActionButton floatingBtnWrite = (FloatingActionButton)view.findViewById(R.id.floatingBtnWrite);
        floatingBtnWrite.setOnClickListener(v -> {
            ((MainActivity)getActivity()).moveActivity(2);
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        listRecyclerView.setLayoutManager(manager);

        Log.i(TAG,"onCreateView");
        return view;
    }

    @Override
    public void dataChanged(PostData post) {
        datas.addAll(post.getResults());
        adapter.notifyDataSetChanged();
    }




    // TODO 리사이클러 뷰 어텝터

    public static class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder>{

        ArrayList<Results> datas;
        int itemLayout;
        Context context;

        // 생성자
        public RecyclerCardAdapter(ArrayList<Results> datas, int itemLayout, Context context){
            this.datas = datas;
            this.itemLayout = itemLayout;
            this.context = context;

        }


        // view 를 만들어서 홀더에 저장하는 역할
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

            return new ViewHolder(view);
        }

        // listView getView 를 대체하는 함수
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Results data = datas.get(position);

            holder.listCardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //클릭시

                }
            });

            // TODO get의 포지션 수정 ( 이미지 수 확인 후 여러개 표시)
            String url="";
            Log.i(TAG,"photo size :"+ data.getPhotos().size());
            if(data.getPhotos().size() ==0){
                url = "";
                holder.imgListCardMainImg.setVisibility(View.GONE);
            }else{
                url = data.getPhotos().get(0).getPhoto().getFull_size();
                Log.i(TAG,"image URL:"+url );
                Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        holder.bar.setVisibility(View.GONE);
                        holder.imgListCardMainImg.setVisibility(View.VISIBLE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
//                        holder.bar.setVisibility(View.GONE);
                        holder.imgListCardMainImg.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                        .into(holder.imgListCardMainImg);
            }






            //TODO 임시 샘플 이미지 ( 구룹 이미지 )
            holder.imgListCardGroupImg.setBackgroundResource(R.drawable.logoimg);

            if(data.getContent().length() >= 40){
                String str = data.getContent().substring(0,40)+"\n"+"더 보기...";
                holder.tvListCardContents.setText(str);
            }
            else{
                holder.tvListCardContents.setText(data.getContent());
            }

            holder.tvListCardGroupName.setText(data.getGroup());
            holder.tvListCardTime.setText("2016/12/06");
            holder.itemView.setTag(data);

            setAnimation(holder.listCardItem, position);
        }
        int lastPosision = -1;
        public void setAnimation(View view,int position){
            if(position > lastPosision) {
                Animation ani = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                view.startAnimation(ani);
                lastPosision = position;
            }
        }
        @Override
        public int getItemCount() {
            return datas.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ProgressBar bar;
            ImageView imgListCardGroupImg;
            ImageView imgListCardMainImg;
            TextView tvListCardContents;
            TextView tvListCardGroupName;
            TextView tvListCardTime;
            CardView listCardItem;

            public ViewHolder(View itemView) {
                super(itemView);
//                bar                 = (ProgressBar) itemView.findViewById(R.id.barListPost);
                imgListCardGroupImg = (ImageView) itemView.findViewById(R.id.imgListCardGroupImg);
                imgListCardMainImg  = (ImageView) itemView.findViewById(R.id.imgListCardMainImg);
                tvListCardContents  = (TextView) itemView.findViewById(R.id.tvListCardContents);
                tvListCardGroupName = (TextView) itemView.findViewById(R.id.tvListCardGroupName);
                tvListCardTime      = (TextView) itemView.findViewById(R.id.tvListCardTime);
                listCardItem        = (CardView) itemView.findViewById(R.id.listCardItem);

            }
        }
    }


}
