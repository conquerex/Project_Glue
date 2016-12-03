package com.hm.project_glue.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.project_glue.R;
import com.hm.project_glue.main.OnFragmentInteractionListener;
import java.util.ArrayList;


public class HomeFragment extends Fragment implements HomePresenter.View{
    private static final String TAG = "HomeFragment";
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private ArrayList<HomeModel> datas;

    private OnFragmentInteractionListener mListener;
    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        datas = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView)view.findViewById(R.id.homeRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(linearLayoutManager);

        for(int i = 0 ; i < 5 ; i++){
            HomeModel data = new HomeModel(R.drawable.sample_card_img2, "Sample ----- " + i);
//            data.homeCardImage = R.drawable.sample_card_img2;
//            data.homeCardTitle = "Sample ----- " + i;
            Log.i(TAG, "----------- data.homeCardTitle ---- "+ i);
            datas.add(data);
        }

        HomeRecyclerAdapter adapter = new HomeRecyclerAdapter(datas,
                R.layout.fragment_home_item, recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        return view;
    }

    private static class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>{

        ArrayList<HomeModel> datas;
        int itemLayout;
        Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivHomeCard;
            TextView tvHomeCard;
            CardView cardView;

            public ViewHolder(View itemView) {
                super(itemView);
                cardView   = (CardView) itemView.findViewById(R.id.homeCardView);
                ivHomeCard = (ImageView)itemView.findViewById(R.id.ivHomeCard);
                tvHomeCard = (TextView) itemView.findViewById(R.id.tvHomeCard);
            }
        }

        public HomeRecyclerAdapter(ArrayList<HomeModel> datas, int itemLayout, Context context) {
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
        public void onBindViewHolder(ViewHolder holder, int position) {
            final HomeModel data = datas.get(position);
            holder.ivHomeCard.setBackgroundResource(data.homeCardImage);

            holder.ivHomeCard.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"이미지가 클릭됨!!",Toast.LENGTH_SHORT).show();
                }
            });

            Log.i(TAG, "----------- onBindViewHolder ---- "+ data);
            holder.tvHomeCard.setText(data.homeCardTitle);
            holder.cardView.setTag(data);

            setAnimation(holder.cardView, position);
        }

        int lastPosition = -1;
        public void setAnimation(View view, int position){
            if(position > lastPosition){
                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                view.startAnimation(animation);
                lastPosition = position;
            }
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

    }
}