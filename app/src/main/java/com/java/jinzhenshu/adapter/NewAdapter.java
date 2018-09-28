package com.java.jinzhenshu.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.java.jinzhenshu.R;
import com.java.jinzhenshu.bean.News;
import com.java.jinzhenshu.untils.DaoUntils;

import java.util.ArrayList;
import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewHolder> {


    private List<News> datas=new ArrayList<>();

    NewAdapterCallBack callBack;
    public NewAdapter(NewAdapterCallBack callBack){
        this.callBack=callBack;;
    }

    @NonNull
    @Override
    public NewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item,viewGroup,false);
        return new NewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewHolder newHolder, int i) {

        final News news=datas.get(i);

        if (news.getIsRead()==2){
            newHolder.title.setTextColor(R.color.font_bababa);
            newHolder.content.setTextColor(R.color.font_bababa);
            newHolder.time.setTextColor(R.color.font_bababa);
        }else {
            newHolder.title.setTextColor(Color.BLACK);
            newHolder.content.setTextColor(Color.BLACK);
            newHolder.time.setTextColor(Color.BLACK);
        }

        newHolder.title.setText(news.getTitle());
        newHolder.content.setText(news.getDescription());
        newHolder.time.setText(news.getPubDate());
        newHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaoUntils.updateByLink(news.getLink());
                callBack.onClick(news);
            }
        });
        newHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                callBack.onLongClic(news);
                return false;
            }
        });
    }

    public  void  setDatas(List<News> datas){
        this.datas=datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class NewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView content;
        TextView time;

        public NewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tv_title);
            content=itemView.findViewById(R.id.tv_content);
            time=itemView.findViewById(R.id.tv_time);
        }
    }

    public interface NewAdapterCallBack{
        void onClick(News news);
        void onLongClic(News news);
    }

}
