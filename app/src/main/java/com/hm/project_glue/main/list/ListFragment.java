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
        listPresenter.getPostJson("3");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        datas = new ArrayList<>();
        RecyclerData data;

        for( int i = 1 ; i <= 10 ; i ++ ) {
            data = new RecyclerData();
            String str = i+" "+"Markdown은 텍스트 기반의 마크업언어로 2004년 존그루버에 의해 만들어졌으며 쉽게 쓰고 읽을 수 있으며 HTML로 변환이 가능하다. 특수기호와 문자를 이용한 매우 간단한 구조의 문법을 사용하여 웹에서도 보다 빠르게 컨텐츠를 작성하고 보다 직관적으로 인식할 수 있다. 마크다운이 최근 각광받기 시작한 이유는 깃헙(https://github.com) 덕분이다. 깃헙의 저장소Repository에 관한 정보를 기록하는 README.md는 깃헙을 사용하는 사람이라면 누구나 가장 먼저 접하게 되는 마크다운 문서였다. 마크다운을 통해서 설치방법, 소스코드 설명, 이슈 등을 간단하게 기록하고 가독성을 높일 수 있다는 강점이 부각되면서 점점 여러 곳으로 퍼져가게 된다.";
            data.setContents(str);
            data.setGroupName(i+"놀자Group");

            data.setMainImg(R.mipmap.sample);
            data.setImg(R.drawable.com_facebook_button_icon);
            data.setTime("2016/12/02");
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
            holder.imgListCardGroupImg.setBackgroundResource(data.getImg());
            holder.imgListCardMainImg.setBackgroundResource(data.getMainImg());

            if(data.getContents().length() >= 20){
                String str = data.getContents().substring(0,19)+"\n"+"더 보기...";
                holder.tvListCardContents.setText(str);
            }
            else{
                holder.tvListCardContents.setText(data.getContents());
            }

            holder.tvListCardGroupName.setText(data.getGroupName());
            holder.tvListCardTime.setText(data.getTime());
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
