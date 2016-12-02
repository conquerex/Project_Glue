package com.hm.project_glue.main.list;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.TextView;

import com.hm.project_glue.R;
import com.hm.project_glue.main.OnFragmentInteractionListener;
import com.hm.project_glue.main.list.data.RecyclerData;

import java.util.ArrayList;


public class ListFragment extends Fragment implements ListPresenter.View {
    private OnFragmentInteractionListener mListener;
    private ListPresenter listPresenter;
    public static ArrayList<RecyclerData> datas = null;
    RecyclerView listRecyclerView;
    RecyclerCardAdapter adapter;

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
        listPresenter = new ListPresenterImpl(ListFragment.this);
        listPresenter.setView(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        datas = new ArrayList<>();
        RecyclerData data;

        for( int i = 1 ; i <= 10 ; i ++ ) {
            data = new RecyclerData();
            data.contents = i+" "+"내용입니다 내용입니다 내용입니다. ";
            data.groupName = i+"name";
            data.mainImg = R.mipmap.sample;
            data.img = R.drawable.com_facebook_button_icon;
            data.time = "2016/12/02";
            datas.add(data);
        }

        listRecyclerView = (RecyclerView) view.findViewById(R.id.recylerCardView);
        adapter  = new RecyclerCardAdapter(datas,R.layout.list_recycler_card_item,getContext());
        listRecyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        listRecyclerView.setLayoutManager(manager);
        return view;
    }


    // TODO 리사이클러 뷰 어텝터

    public static class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder>{

        ArrayList<RecyclerData> datas;
        int itemLayout;
        Context context;

        // 생성자
        public RecyclerCardAdapter(ArrayList<RecyclerData> datas, int itemLayout, Context context){
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
            RecyclerData data = datas.get(position);


            holder.listCardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //클릭시
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra("position", position);
//                    //intent.putExtra("OBJECT",data);
//                    context.startActivity(intent);
                }
            });
            holder.imgListCardGroupImg.setBackgroundResource(data.img);
            holder.imgListCardMainImg.setBackgroundResource(data.mainImg);
            holder.tvListCardContents.setText(data.contents);
            holder.tvListCardGroupName.setText(data.groupName);
            holder.tvListCardTime.setText(data.time);
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

            ImageView imgListCardGroupImg;
            ImageView imgListCardMainImg;
            TextView tvListCardContents;
            TextView tvListCardGroupName;
            TextView tvListCardTime;
            CardView listCardItem;

            public ViewHolder(View itemView) {
                super(itemView);
                imgListCardGroupImg = (ImageView) itemView.findViewById(R.id.imgListCardGroupImg);
                imgListCardMainImg = (ImageView) itemView.findViewById(R.id.imgListCardMainImg);
                tvListCardContents = (TextView) itemView.findViewById(R.id.tvListCardContents);
                tvListCardGroupName = (TextView) itemView.findViewById(R.id.tvListCardGroupName);
                tvListCardTime = (TextView) itemView.findViewById(R.id.tvListCardTime);
                listCardItem = (CardView) itemView.findViewById(R.id.listCardItem);

            }
        }
    }


}
