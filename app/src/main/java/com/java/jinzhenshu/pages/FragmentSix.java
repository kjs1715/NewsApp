package com.java.jinzhenshu.pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.java.jinzhenshu.DetailsActivity;
import com.java.jinzhenshu.R;
import com.java.jinzhenshu.adapter.NewAdapter;
import com.java.jinzhenshu.base.BaseFragment;
import com.java.jinzhenshu.bean.News;
import com.java.jinzhenshu.parse.Parser;
import com.java.jinzhenshu.untils.DaoUntils;
import com.java.jinzhenshu.untils.NetWorkUntils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/*滚动新闻*/
public class FragmentSix extends BaseFragment{

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private NewAdapter adapter;

    String deleteUrl;

    private List<News> showDatas=new ArrayList<>();

    @Override
    protected int onSetContentView() {
        return R.layout.fragment_two;
    }

    @Override
    protected void onInitData() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter=new NewAdapter(new NewAdapter.NewAdapterCallBack() {
            @Override
            public void onClick(News news) {
                Intent intent=new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("url",news.getLink());
                intent.putExtra("isSc",news.getIsSC());
                startActivity(intent);
            }

            @Override
            public void onLongClic(News news) {
                deleteUrl=news.getLink();
                dialog.show();
            }
        });

        recyclerview.setAdapter(adapter);
        createDialog();

        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getSqdate();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                getSqdate();
            }
        });
    }

    int page=1;
    int size=5;
    @Override
    public void onResume() {
        super.onResume();
       getSqdate();
    }

    private void getSqdate(){
        showDatas.clear();
        List<News> sqDatas=DaoUntils.queryByType(6);
        if (sqDatas.size()!=0){
            if (page*size>sqDatas.size()){
                adapter.setDatas(sqDatas);
                if (page==1){
                    smartRefreshLayout.finishRefresh();
                }else {
                    smartRefreshLayout.finishLoadmoreWithNoMoreData();
                }
            }else {
                for (int i=0;i<page*size;i++){
                    showDatas.add(sqDatas.get(i));
                }
                adapter.setDatas(showDatas);
                if (page==1){
                    smartRefreshLayout.finishRefresh();
                }else {
                    smartRefreshLayout.finishLoadmore();
                }
            }
        }else {
            getData();
        }
    }

    private void getData(){
        if (NetWorkUntils.GetNetworkType(getActivity())==NetWorkUntils.NO){
            return;
        }
        String urlString = "http://www.people.com.cn/rss/game.xml";

        Parser parser = new Parser();
        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(List<News> list) {
                for (News news:list){
                    news.setType(6);
                }

                if (DaoUntils.queryByType(6).size()==0){
                    DaoUntils.insetNewsList(list);
                    for (int i=0;i<5;i++){
                        showDatas.add(list.get(i));
                    }
                    adapter.setDatas(showDatas);
                }else {
                    List<News> sqDatas=DaoUntils.queryByType(6);
                    for (int i=0;i<5;i++){
                        showDatas.add(sqDatas.get(i));
                    }
                    adapter.setDatas(showDatas);
                }

            }

            @Override
            public void onError() {

            }
        });
    }

    AlertDialog dialog;
    private void createDialog(){
        dialog= new AlertDialog.Builder(getContext()).setMessage("你确定删除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DaoUntils.deleteByLink(deleteUrl);
                        adapter.setDatas(DaoUntils.queryByType(2));
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
    }
}
