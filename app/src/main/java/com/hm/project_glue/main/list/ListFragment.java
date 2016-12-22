package com.hm.project_glue.main.list;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.hm.project_glue.main.OnFragmentInteractionListener;
import com.hm.project_glue.main.list.data.Photos;
import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.main.list.data.Results;
import com.hm.project_glue.util.Util;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

import static com.hm.project_glue.util.http.CallRest.callHttpLike;

public class ListFragment extends Fragment implements ListPresenter.View {
    private static OnFragmentInteractionListener mListener;
    private static final String ARG_PARAM1 = "param1";
    private String mParam1="";
    private ListPresenter listPresenter;
    private static ArrayList<Results> datas = null;
    private static RecyclerView listRecyclerView;
    private RecyclerCardAdapter adapter;
    private LinearLayout listLinearProgressTop, listLinearProgressBottom, linearListNew;
    private PostData post;
    private View view;
    Toolbar toolbar;
    private static boolean loadingFlag = true;
    private int indexOf = 1;
    private String selectGroup="1", nextPage ="";
    private static final String TAG = "TEST";

    public ListFragment() {
    }

    public static ListFragment newInstance(String param1) {
        ListFragment fragment = new ListFragment();
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
        }else{
            return;
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);

        MainActivity activity = (MainActivity) getActivity();

        // toolbar
        toolbar = (Toolbar) view.findViewById(R.id.listToolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            activity.setSupportActionBar(toolbar);
        }


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

//        setHasOptionsMenu(true);
//        actionBar = getActivity().getActionBar();
//        actionBar.setTitle("Post List");
//        actionBar.setDisplayHomeAsUpEnabled(true);
        listLinearProgressTop = (LinearLayout) view.findViewById(R.id.listLinearProgressTop);
        listLinearProgressBottom = (LinearLayout) view.findViewById(R.id.listLinearProgressBottom);
        linearListNew = (LinearLayout) view.findViewById(R.id.linearListNew);

        if(savedInstanceState==null){
                      listPresenter.callHttp(mParam1, "1",true);
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
        // write 버튼
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
                        listPresenter.callHttp(mParam1, "1", true);
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
        listPresenter.callHttp(mParam1, nextPage ,false);

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
        if( res.getResults().size()>0){
            toolbar.setTitle(res.getResults().get(0).getGroup().getGroup_name());
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
                    mListener.goToPostFragment(data.getPk());

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
               ListPhotoAdapter timePhotoListAdapter = new ListPhotoAdapter(context,photosurl, R.layout.list_photo_list_item);
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
            holder.tvListCardGroupName.setText(data.getGroup().getGroup_name());
            // 시간
//            String getCreated_date = timeChange(context, datas.get(position).getContent().);
//            holder.tvListCardTime.setText(getCreated_date);
            // like
            holder.tvListCardLike.setText(data.getLikes_count());
            holder.tvListCardDislike.setText(data.getDislikes_count());

            //댓글 수
            holder.linearCon1.setVisibility(View.GONE);
            holder.linearCon2.setVisibility(View.GONE);
            holder.list_tvCommentCount.setText(""+data.getComments().size());
            //댓글 수에 따른 댓글 2개 표시
            if( data.getComments().size() > 0 ){
                if(data.getComments().get(0).getUser().getImage()!=null) {
                    Glide.with(context).load(data.getComments().get(0).getUser().getImage()).override(70, 70)
                            .bitmapTransform(new CropCircleTransformation(context)).into(holder.list_imgComment1);
                }else{
                    Glide.with(context).load(R.drawable.noprofile).override(70, 70)
                            .bitmapTransform(new CropCircleTransformation(context)).into(holder.list_imgComment1);
                }
                holder.list_tvCommentName1.setText(data.getComments().get(0).getUser().getName());
                String commentCreated_date1 = Util.timeChange(context, data.getComments().get(0).getCreated_date());
                holder.list_tvCommenttime1.setText(commentCreated_date1);
                holder.list_tvCommentCon1.setText(data.getComments().get(0).getContent());
                holder.linearCon1.setVisibility(View.VISIBLE);

                if(data.getComments().size() > 1){
                    if(data.getComments().get(1).getUser().getImage()!=null) {
                        Glide.with(context).load(data.getUser().getImage()).override(70, 70)
                                .bitmapTransform(new CropCircleTransformation(context)).into(holder.list_imgComment2);
                    }else{
                        Glide.with(context).load(R.drawable.noprofile).override(70, 70)
                                .bitmapTransform(new CropCircleTransformation(context)).into(holder.list_imgComment2);
                    }
                    holder.list_tvCommentName2.setText(data.getComments().get(0).getUser().getName());
                    String commentCreated_date2 = Util.timeChange(context, data.getComments().get(1).getCreated_date());
                    holder.list_tvCommenttime2.setText(commentCreated_date2);
                    holder.list_tvCommentCon2.setText(data.getComments().get(1).getContent());
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
            ImageView imgListCardGroupImg,list_imgComment1,list_imgComment2;
            TextView tvListCardContents, tvListCardGroupName, tvListCardTime,tvListCardTitle, tvListCardLike, tvListCardDislike,
                    list_tvCommentCount,list_tvCommentName1,list_tvCommentName2,list_tvCommentCon1,list_tvCommentCon2,
                    list_tvCommenttime1,list_tvCommenttime2;
            LinearLayout linearCon1, linearCon2;
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

                //Coment 부분
                list_tvCommentCount = (TextView) itemView.findViewById(R.id.list_tvCommentCount);
                list_imgComment1    = (ImageView) itemView.findViewById(R.id.list_imgComment1);
                list_imgComment2    = (ImageView) itemView.findViewById(R.id.list_imgComment2);
                list_tvCommentName1 = (TextView) itemView.findViewById(R.id.list_tvCommentName1);
                list_tvCommentName2 = (TextView) itemView.findViewById(R.id.list_tvCommentName2);
                list_tvCommentCon1  = (TextView) itemView.findViewById(R.id.list_tvCommentCon1);
                list_tvCommentCon2  = (TextView) itemView.findViewById(R.id.list_tvCommentCon2);
                list_tvCommenttime1  = (TextView) itemView.findViewById(R.id.list_tvCommenttime1);
                list_tvCommenttime2  = (TextView) itemView.findViewById(R.id.list_tvCommenttime2);
                linearCon1          =(LinearLayout) itemView.findViewById(R.id.list_linearCon1);
                linearCon2          =(LinearLayout) itemView.findViewById(R.id.list_linearCon2);



                gridView            = (GridView) itemView.findViewById(R.id.gridView);
                listCardItem        = (CardView) itemView.findViewById(R.id.listCardItem);
                like                = (Button) itemView.findViewById(R.id.list_btnLike);
                dislike             = (Button) itemView.findViewById(R.id.list_btnDislike);

            }
        }
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
