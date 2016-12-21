package com.hm.project_glue.main.timeline;

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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hm.project_glue.R;
import com.hm.project_glue.main.MainActivity;
import com.hm.project_glue.main.timeline.data.Photos;
import com.hm.project_glue.main.timeline.data.PostList;
import com.hm.project_glue.main.timeline.data.Posts;
import com.hm.project_glue.main.timeline.data.TimelineData;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static com.hm.project_glue.main.timeline.TimelinePresentImpl.timeChange;
import static com.hm.project_glue.util.http.CallRest.callHttpLike;

public class TimelineFragment extends Fragment implements TimelinePresenter.View{
    private TimelinePresentImpl presenter;
    private static ArrayList<PostList> datas = null;
    private static RecyclerView listRecyclerView;
    private RecyclerCardAdapter adapter;
    private LinearLayout listLinearProgressTop, listLinearProgressBottom, linearListNew;
    private View view;
    private TimelineData post;
    public static boolean loadingFlag= true;
    private int indexOf = 1;
    private String selectGroup="1", nextPage ="";
    private static final String TAG = "TEST";

    public TimelineFragment() {
    }
    public static TimelineFragment newInstance() {
        TimelineFragment fragment = new TimelineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            return;
        }
        presenter = new TimelinePresentImpl(this);
        presenter.setView(this);
        post = new TimelineData();
        datas = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_timeline, container, false);
        listLinearProgressTop = (LinearLayout) view.findViewById(R.id.time_listLinearProgressTop);
        listLinearProgressBottom = (LinearLayout) view.findViewById(R.id.time_listLinearProgressBottom);
        linearListNew = (LinearLayout) view.findViewById(R.id.time_linearListNew);
        if(savedInstanceState==null){
            presenter.callHttp("1",true);
        }
        listRecyclerView = (RecyclerView) view.findViewById(R.id.time_recylerCardView);

        Button btnListNewPost = (Button) view.findViewById(R.id.time_btnListNewPost);
        btnListNewPost.setOnClickListener(v-> {
            // TODO new 버튼
            linearListNew.setVisibility(View.GONE);
            // 이동
            listRecyclerView.setScrollY(0);
        });
        adapter  = new RecyclerCardAdapter(datas,R.layout.time_recycler_card_item,getContext());
        listRecyclerView.setAdapter(adapter);
        FloatingActionButton floatingBtnWrite = (FloatingActionButton)view.findViewById(R.id.time_floatingBtnWrite);
        floatingBtnWrite.setOnClickListener(v -> {
            ((MainActivity)getActivity()).moveActivity(2);
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        listRecyclerView.setLayoutManager(manager);

        listRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == SCROLL_STATE_SETTLING){
                    //최상단
                    if((recyclerView.computeVerticalScrollOffset() == 0) && !loadingFlag){
                        Log.i(TAG,"start:::");
                        presenter.callHttp("1", true);
                    }
                    //최하단
                    if((recyclerView.computeVerticalScrollOffset()+recyclerView.getHeight()
                            == recyclerView.computeVerticalScrollRange()) && !loadingFlag){
                        if(post.getNext()!=null){
                            postListUpdate();
                        }
                        else{ // Last page
                            Toast.makeText(getContext(),getContext().getResources().getString(R.string.listEndPosition),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

        });

        return view;
    }
    private void postListUpdate(){
        indexOf = post.getNext().indexOf("=");
        nextPage = post.getNext().substring(indexOf+1);
        Log.i(TAG, "////nextPage: "+nextPage);
        presenter.callHttp(nextPage ,false);

    }
    public void setProgress(int code){
        switch (code){
            case 1:
                loadingFlag = true;
                listLinearProgressTop.setVisibility(View.VISIBLE);  break;
            case 2:
                loadingFlag = false;
                listLinearProgressTop.setVisibility(View.GONE); break;
            case 3:
                loadingFlag = true;
                listLinearProgressBottom.setVisibility(View.VISIBLE); break;
            case 4:
                loadingFlag = false;
                listLinearProgressBottom.setVisibility(View.GONE); break;
        }

    }
    @Override
    public void dataChanged(TimelineData res) {
        if(res.getPrevious()==null){
            datas.clear();
        }
        post.setPrevious(res.getPrevious());
        post.setNext(res.getNext());
        datas.addAll(res.getResults().getPostList());
        Log.i(TAG, "datas.size : "+datas.size());
        adapter.notifyDataSetChanged();
    }

    public static void likeChanged(TextView tvLike,TextView tvDislike, String resLike, String resDislike){
        loadingFlag=false;
        tvLike.setText(resLike);
        tvDislike.setText(resDislike);
    }
    // TODO 리사이클러 뷰 어텝터
    public static class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder>{
        ArrayList<PostList> datas;
        int itemLayout;
        Context context;

        public RecyclerCardAdapter(ArrayList<PostList> datas, int itemLayout, Context context){
            this.datas = datas;
            this.itemLayout = itemLayout;
            this.context = context;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(RecyclerCardAdapter.ViewHolder holder, final int position) {
            Posts data = datas.get(position).getPosts();

            holder.listCardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //클릭시

                }
            });
            //좋아요버튼
            holder.like.setOnClickListener(v->{

                if(!loadingFlag){
                    callHttpLike(data.getPk(),true,holder.tvListCardLike, holder.tvListCardDislike );
                    holder.like.setBackgroundResource(R.drawable.likeup);
                    holder.dislike.setBackgroundResource(R.drawable.dislike);
                }

            });
            holder.dislike.setOnClickListener(v->{
                if(!loadingFlag) {
                    callHttpLike(data.getPk(), false, holder.tvListCardLike, holder.tvListCardDislike);
                    holder.like.setBackgroundResource(R.drawable.like);
                    holder.dislike.setBackgroundResource(R.drawable.dislikeup);
                }
            });
            // 이미지 처리
            String url="";
            Log.i(TAG,"photo size :"+ data.getPhotos().size());
            if(data.getPhotos().size() ==0){
                holder.gridView.setVisibility(View.GONE);
            }else{
                if(data.getPhotos().size() > 1){
                    holder.gridView.setNumColumns(2);
                }else{
                    holder.gridView.setNumColumns(1);
                }
                ArrayList<String> photosurl = new ArrayList<>();
                for(Photos s : data.getPhotos()){
                    photosurl.add(s.getPhoto().getFull_size());
                }
                holder.gridView.setVisibility(View.VISIBLE);
                TimePhotoAdapter timePhotoListAdapter = new TimePhotoAdapter(context,photosurl, R.layout.time_photo_list_item);
                holder.gridView.setAdapter(timePhotoListAdapter);
            }
            // 작성자 이미지
            if(data.getUser().getImage()!=null){
                Glide.with(context).load(data.getUser().getImage()).override(70,70)
                        .bitmapTransform(new CropCircleTransformation(context)).into(holder.imgListCardGroupImg);
            }else{
                Glide.with(context).load(R.drawable.noprofile).override(70,70)
                        .bitmapTransform(new CropCircleTransformation(context)).into(holder.imgListCardGroupImg);
            }
            //내용
            int in1 = data.getContent().indexOf(System.getProperty("line.separator"));
            if(in1 !=(-1)){
                if(in1 >= 40){
                    String str = data.getContent().substring(0,40)+"\n"+context.getResources().getString(R.string.listDetailClick);
                    holder.tvListCardContents.setText(str);
                }else{
                    String str = data.getContent().substring(0,in1)+"\n"+context.getResources().getString(R.string.listDetailClick);
                    holder.tvListCardContents.setText(str);
                }
            }else{
                if(data.getContent().length() >= 40){
                    String str = data.getContent().substring(0,40)+"\n"+context.getResources().getString(R.string.listDetailClick);
                    holder.tvListCardContents.setText(str);
                }else{   holder.tvListCardContents.setText(data.getContent());  }
            }



            // 이름
            holder.tvListCardTitle.setText(data.getUser().getName());
            // 구룹이름
            holder.tvListCardGroupName.setText(data.getGroup());
            // 시간
            String getCreated_date = timeChange(context, datas.get(position).getCreated_date());
            holder.tvListCardTime.setText(getCreated_date);
            // like
            holder.tvListCardLike.setText(data.getLikes_count());
            holder.tvListCardDislike.setText(data.getDislikes_count());

            //댓글 수
            holder.time_tvCommentCount.setText(""+data.getComments().size());
            //댓글 수에 따른 댓글 2개 표시
            if( data.getComments().size() > 0 ){
                if(data.getComments().get(0).getUser().getImage()!=null) {
                    Glide.with(context).load(data.getComments().get(0).getUser().getImage()).override(70, 70)
                            .bitmapTransform(new CropCircleTransformation(context)).into(holder.time_imgComment1);
                }else{
                    Glide.with(context).load(R.drawable.noprofile).override(70, 70)
                            .bitmapTransform(new CropCircleTransformation(context)).into(holder.time_imgComment1);
                }
                holder.time_tvCommentName1.setText(data.getComments().get(0).getUser().getName());
                String commentCreated_date1 = timeChange(context, data.getComments().get(0).getCreated_date());
                holder.time_tvCommenttime1.setText(commentCreated_date1);
                holder.time_tvCommentCon1.setText(data.getComments().get(0).getContent());
                holder.linearCon1.setVisibility(View.VISIBLE);

                if(data.getComments().size() > 1){
                    if(data.getComments().get(1).getUser().getImage()!=null) {
                        Glide.with(context).load(data.getUser().getImage()).override(70, 70)
                                .bitmapTransform(new CropCircleTransformation(context)).into(holder.time_imgComment2);
                    }else{
                        Glide.with(context).load(R.drawable.noprofile).override(70, 70)
                                .bitmapTransform(new CropCircleTransformation(context)).into(holder.time_imgComment2);
                    }
                    holder.time_tvCommentName2.setText(data.getComments().get(0).getUser().getName());
                    String commentCreated_date2 = timeChange(context, data.getComments().get(1).getCreated_date());
                    holder.time_tvCommenttime2.setText(commentCreated_date2);
                    holder.time_tvCommentCon2.setText(data.getComments().get(1).getContent());
                    holder.linearCon2.setVisibility(View.VISIBLE);
                }
            }


            holder.itemView.setTag(data);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgListCardGroupImg,time_imgComment1,time_imgComment2;
            Button like, dislike;
            TextView tvListCardContents, tvListCardGroupName, tvListCardTime,tvListCardTitle,
                    tvListCardLike,tvListCardDislike,time_tvCommentCount,
                    time_tvCommentName1,time_tvCommentName2,
                    time_tvCommentCon1, time_tvCommentCon2,time_tvCommenttime1,time_tvCommenttime2;
            CardView listCardItem;
            GridView gridView;
            LinearLayout linearCon1, linearCon2;

            public ViewHolder(View itemView) {
                super(itemView);
                imgListCardGroupImg = (ImageView) itemView.findViewById(R.id.time_imgListCardGroupImg);
                tvListCardContents  = (TextView) itemView.findViewById(R.id.time_tvListCardContents);
                tvListCardGroupName = (TextView) itemView.findViewById(R.id.time_tvListCardGroupName);
                tvListCardTitle     = (TextView) itemView.findViewById(R.id.time_tvListCardTitle);
                tvListCardTime      = (TextView) itemView.findViewById(R.id.time_tvListCardTime);
                tvListCardLike      = (TextView) itemView.findViewById(R.id.time_tvTimeCardLike);
                tvListCardDislike   = (TextView) itemView.findViewById(R.id.time_tvTimeCardDislike);

                //Coment 부분
                time_tvCommentCount = (TextView) itemView.findViewById(R.id.time_tvCommentCount);
                time_imgComment1    = (ImageView) itemView.findViewById(R.id.time_imgComment1);
                time_imgComment2    = (ImageView) itemView.findViewById(R.id.time_imgComment2);
                time_tvCommentName1 = (TextView) itemView.findViewById(R.id.time_tvCommentName1);
                time_tvCommentName2 = (TextView) itemView.findViewById(R.id.time_tvCommentName2);
                time_tvCommentCon1  = (TextView) itemView.findViewById(R.id.time_tvCommentCon1);
                time_tvCommentCon2  = (TextView) itemView.findViewById(R.id.time_tvCommentCon2);
                time_tvCommenttime1  = (TextView) itemView.findViewById(R.id.time_tvCommenttime1);
                time_tvCommenttime2  = (TextView) itemView.findViewById(R.id.time_tvCommenttime2);
                linearCon1          =(LinearLayout) itemView.findViewById(R.id.time_linearCon1);
                linearCon2          =(LinearLayout) itemView.findViewById(R.id.time_linearCon2);

                gridView            = (GridView) itemView.findViewById(R.id.time_gridView);
                like                = (Button)   itemView.findViewById(R.id.time_btnLike);
                dislike             = (Button)   itemView.findViewById(R.id.time_btnDislike);
                listCardItem        = (CardView) itemView.findViewById(R.id.time_listCardItem);
            }
        }
    }

}
