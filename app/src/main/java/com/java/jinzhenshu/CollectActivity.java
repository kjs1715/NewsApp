package com.java.jinzhenshu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.java.jinzhenshu.adapter.NewAdapter;
import com.java.jinzhenshu.bean.News;
import com.java.jinzhenshu.untils.DaoUntils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private NewAdapter adapter;
    String deleteUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        adapter=new NewAdapter(new NewAdapter.NewAdapterCallBack() {
            @Override
            public void onClick(News news) {
                Intent intent=new Intent(CollectActivity.this, DetailsActivity.class);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setDatas(DaoUntils.queryByIsSC());
    }

    AlertDialog dialog;
    private void createDialog(){
        dialog= new AlertDialog.Builder(this).setMessage("你确定删除吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DaoUntils.deleteByLink(deleteUrl);
                        adapter.setDatas(DaoUntils.queryByIsSC());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
    }
}
