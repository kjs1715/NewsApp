package com.java.jinzhenshu.untils;

import android.content.Context;
import android.util.Log;


import com.java.jinzhenshu.DaoMaster;
import com.java.jinzhenshu.DaoSession;
import com.java.jinzhenshu.NewsDao;
import com.java.jinzhenshu.bean.News;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DaoUntils {

    public static DaoSession daoSession;
    public static Context mcontext;
    public static void init(Context context){
        mcontext=context;
        DaoMaster.DevOpenHelper openHelper=new DaoMaster.DevOpenHelper(context,"trans.db");
        DaoMaster daoMaster=new DaoMaster(openHelper.getWritableDb());
        daoSession=daoMaster.newSession();

    }

    public static void insetNewsList(List<News> list){
        daoSession.getNewsDao().insertInTx(list);
    }

    public static List<News> queryByIsRead(){
        return daoSession.getNewsDao().queryBuilder().where(NewsDao.Properties.IsRead.eq(2)).list();
    }

    public static List<News> queryByIsSC(){
        return daoSession.getNewsDao().queryBuilder().where(NewsDao.Properties.IsSC.eq(2)).list();
    }

    public static List<News> queryByType(int type){
        List<News> datas;
        if(NetWorkUntils.GetNetworkType(mcontext)==NetWorkUntils.NO){
            datas=daoSession.getNewsDao().queryBuilder().where(NewsDao.Properties.Type.eq(type)).where(NewsDao.Properties.IsRead.eq(2)).list();
        }else {
            datas=daoSession.getNewsDao().queryBuilder().where(NewsDao.Properties.Type.eq(type)).list();
        }

        Collections.sort(datas, new Comparator<News>() {
            @Override
            public int compare(News news, News t1) {
                return (int) (news.getLastTime()-t1.getLastTime());
            }
        });
        return datas;
    }

    public static void updateContent(String content,String url){
        List<News> datas=  daoSession.getNewsDao().queryBuilder().where(NewsDao.Properties.Link.eq(url)).list();
        News news=datas.get(0);
        if (news.getContent()==""||news.getContent()==null){
            news.setContent(content);
            daoSession.getNewsDao().update(news);
        }
    }

    public static void updateByLink(String link){
        List<News> list=daoSession.getNewsDao().queryBuilder().where(NewsDao.Properties.Link.eq(link)).list();
        News news=list.get(0);
        news.setIsRead(2);
        news.setLastTime(System.currentTimeMillis());
        daoSession.getNewsDao().update(news);
    }

    public static void upDateIsSC(String link){
        List<News> list=daoSession.getNewsDao().queryBuilder().where(NewsDao.Properties.Link.eq(link)).list();
        News news=list.get(0);
        if (news.getIsSC()==2){
            news.setIsSC(1);
        }else {
            news.setIsSC(2);
        }
        daoSession.getNewsDao().update(news);
    }

    public static void deleteByLink(String link){
        List<News> list=daoSession.getNewsDao().queryBuilder().where(NewsDao.Properties.Link.eq(link)).list();
        daoSession.getNewsDao().delete(list.get(0));
    }

    public static News queryByLink(String link){
        List<News> list=daoSession.getNewsDao().queryBuilder().where(NewsDao.Properties.Link.eq(link)).list();
        return list.get(0);
    }

}
