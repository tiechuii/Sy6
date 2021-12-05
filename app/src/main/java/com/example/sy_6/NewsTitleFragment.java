package com.example.sy_6;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener {
    private boolean isTwoPane;
    private List<com.example.sy_6.News> newsList;
    private com.example.sy_6.NewsAdapter adapter;
    private ListView newsTitleView;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        newsList=getNews();
        adapter =new com.example.sy_6.NewsAdapter(activity,R.layout.news_item,newsList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.news_title_frag,container,false);
        newsTitleView =(ListView)
                view.findViewById(R.id.news_title_recycler_view);
        newsTitleView.setAdapter(adapter);
        newsTitleView.setOnItemClickListener ((AdapterView.OnItemClickListener) this);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout)!=null){
            isTwoPane = true;
            //可以找到news_content_layout布局时，为双页模式
        }else {
            isTwoPane = false;
            //找不到news_content_layout布局时，为单页模式
        }
    }

    @Override
    public void onItemClick(AdapterView<?>parent,View view,int position,long id){
        com.example.sy_6.News news=newsList.get(position);
        if(isTwoPane) {
            // 如果是双页模式，则刷新NewsContentFragment中的内容
            com.example.sy_6.NewsContentFragment newsContentFragment = (com.example.sy_6.NewsContentFragment)
                    getFragmentManager().findFragmentById(R.id.news_content_fragment);
            newsContentFragment.refresh(news.getTitle(), news.getContent());
        }else{
            // 如果是单页模式，则直接启动NewsContentActivity
            com.example.sy_6.NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getTitle());
        }
    }
    private List<com.example.sy_6.News> getNews(){
        List<com.example.sy_6.News> newsList= new ArrayList<>();
        for (int i = 1;i <= 50;i++){
            com.example.sy_6.News news = new com.example.sy_6.News();
            news.setTitle("This is news title"+i);
            news.setContent(getRandomLengthContent ("This is news content"+i+"."));
            newsList.add(news);
        }
        return newsList;
    }

    private String getRandomLengthContent(String content){
        Random random=new Random();
        int length =random.nextInt(5)+1;
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<length; i++){
            builder.append(content);
        }
        return builder.toString();
    }

}

