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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hm.project_glue.R;
import com.hm.project_glue.main.MainActivity;
import com.hm.project_glue.main.home.data.HomeResponse;
import com.hm.project_glue.main.list.data.Photos;
import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.main.list.data.Results;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static com.hm.project_glue.main.home.HomeFragment.homeResponses;
import static com.hm.project_glue.util.http.CallRest.callHttpLike;

public class ListFragment extends Fragment implements ListPresenter.View {

    private ListPresenter listPresenter;
    private static ArrayList<Results> datas = null;
    private static RecyclerView listRecyclerView;
    private RecyclerCardAdapter adapter;
    private LinearLayout listLinearProgressTop, listLinearProgressBottom, linearListNew;
    private PostData post;
    private View view;
    private boolean loadingFlag = true;
    private int indexOf = 1;
    private String selectGroup="1", nextPage ="";
    private static final String TAG = "TEST";

    public ListFragment() {
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
        post = new PostData();
        datas = new ArrayList<>();

        Log.i(TAG,"onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        listLinearProgressTop = (LinearLayout) view.findViewById(R.id.listLinearProgressTop);
        listLinearProgressBottom = (LinearLayout) view.findViewById(R.id.listLinearProgressBottom);
        linearListNew = (LinearLayout) view.findViewById(R.id.linearListNew);

        if(savedInstanceState==null){
            listPresenter.callHttp(selectGroup, "1",true);
        }
        listRecyclerView = (RecyclerView) view.findViewById(R.id.recylerCardView);

        Button btnListNewPost = (Button) view.findViewById(R.id.btnListNewPost);
        btnListNewPost.setOnClickListener(v-> {
            // TODO new 버튼
            linearListNew.setVisibility(View.GONE);
            // 이동
            listRecyclerView.setScrollY(0);
        });
        adapter  = new RecyclerCardAdapter(datas,R.layout.list_recycler_card_item,getContext());
        listRecyclerView.setAdapter(adapter);
        FloatingActionButton floatingBtnWrite = (FloatingActionButton)view.findViewById(R.id.floatingBtnWrite);
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
                        listPresenter.callHttp(selectGroup, "1", true);
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

    public void newPostNotification(){
        linearListNew.setVisibility(View.VISIBLE);
    }

    private void postListUpdate(){
        indexOf = post.getNext().indexOf("=");
        nextPage = post.getNext().substring(indexOf+1);
        Log.i(TAG, "////nextPage: "+nextPage);
        listPresenter.callHttp(selectGroup, nextPage ,false);

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
    public void dataChanged(PostData res) {
        if(res.getPrevious()==null){
            datas.clear();
        }
        post.setPrevious(res.getPrevious());
        post.setNext(res.getNext());
        datas.addAll(res.getResults());
        Log.i(TAG, "datas.size : "+datas.size());
        adapter.notifyDataSetChanged();
    }
    // TODO 리사이클러 뷰 어텝터

    public static class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder>{
        ArrayList<Results> datas;
        int itemLayout;
        Context context;

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

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Results data = datas.get(position);

            holder.listCardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //클릭시

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
                ListPhotoListAdapter listPhotoListAdapter = new ListPhotoListAdapter(context,photosurl, R.layout.list_photo_list_item);
                holder.gridView.setAdapter(listPhotoListAdapter);
            }
            // 작성자 이미지
            if(data.getUser().getImage()!=null){
                Glide.with(context).load(data.getUser().getImage()).override(70,70)
                        .bitmapTransform(new CropCircleTransformation(context)).into(holder.imgListCardGroupImg);
            }
            //내용
            if(data.getContent().length() >= 40){
                String str = data.getContent().substring(0,40)+"\n"+context.getResources().getString(R.string.listDetailClick);
                holder.tvListCardContents.setText(str);
            }
            else{
                holder.tvListCardContents.setText(data.getContent());
            }
            // 이름
            holder.tvListCardTitle.setText(data.getUser().getName());
            // 구룹이름
            for(HomeResponse s : homeResponses){
                if(data.getUploaded_user().equals(s.getId())){
                    holder.tvListCardGroupName.setText(s.getGroup_name());
                    break;
                }
            }
            //날짜
            holder.tvListCardTime.setText("2016/12/06");
            // like
            holder.tvListCardLike.setText(data.getLikes_count());
            holder.tvListCardDislike.setText(data.getDislikes_count());

            holder.like.setOnClickListener(v->{
                holder.like.setBackgroundResource(R.drawable.likeup);
                holder.dislike.setBackgroundResource(R.drawable.dislike);
                callHttpLike(data.getPk(), true, holder.tvListCardLike, holder.tvListCardDislike);

            });
            holder.dislike.setOnClickListener(v->{
                holder.like.setBackgroundResource(R.drawable.like);
                holder.dislike.setBackgroundResource(R.drawable.dislikeup);
                callHttpLike(data.getPk(), false, holder.tvListCardLike, holder.tvListCardDislike);
            });


            holder.itemView.setTag(data);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imgListCardGroupImg;
            TextView tvListCardContents, tvListCardGroupName, tvListCardTime,tvListCardTitle, tvListCardLike, tvListCardDislike;
            CardView listCardItem;
            Button like, dislike;
            GridView gridView;

            public ViewHolder(View itemView) {
                super(itemView);
                imgListCardGroupImg = (ImageView) itemView.findViewById(R.id.imgListCardGroupImg);
                tvListCardContents  = (TextView) itemView.findViewById(R.id.tvListCardContents);
                tvListCardGroupName = (TextView) itemView.findViewById(R.id.tvListCardGroupName);
                tvListCardTitle     = (TextView) itemView.findViewById(R.id.tvListCardTitle);
                tvListCardTime      = (TextView) itemView.findViewById(R.id.tvListCardTime);
                tvListCardLike      = (TextView) itemView.findViewById(R.id.tvListCardLike);
                tvListCardDislike   = (TextView) itemView.findViewById(R.id.tvListCardDislike);
                gridView            = (GridView) itemView.findViewById(R.id.gridView);
                listCardItem        = (CardView) itemView.findViewById(R.id.listCardItem);
                like                = (Button) itemView.findViewById(R.id.list_btnLike);
                dislike             = (Button) itemView.findViewById(R.id.list_btnDislike);

            }
        }
    }
    private static void test(){

    }


}
