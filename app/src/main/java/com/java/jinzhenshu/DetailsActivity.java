package com.java.jinzhenshu;

import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.java.jinzhenshu.bean.News;
import com.java.jinzhenshu.parse.HttpResponse;
import com.java.jinzhenshu.untils.DaoUntils;
import com.java.jinzhenshu.untils.NetWorkUntils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.iv_collect)
    ImageView iv_collect;
    @BindView(R.id.iv_share)
    ImageView iv_share;
    String url;
    int isSc=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        url=getIntent().getStringExtra("url");
        isSc=getIntent().getIntExtra("isSc",0);
        Log.i("ccccccccccccc","isSc==="+isSc);
        if (isSc==2){
            iv_collect.setImageResource(R.drawable.scy);
        }else {
            iv_collect.setImageResource(R.drawable.syn);
        }
        HttpResponse httpResponse=new HttpResponse();
        httpResponse.onFinish(new HttpResponse.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String list) {
//                Document doc = Jsoup.parse(list);
//                String html = doc.toString();
//                webview.loadData(html,"text/html; charset=UTF-8",null);
              DaoUntils.updateContent(list,url);
            }

            @Override
            public void onError() {

            }
        });

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webview.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        if (NetWorkUntils.GetNetworkType(this)==NetWorkUntils.NO){
            News news=DaoUntils.queryByLink(url);
            if(news.getContent() == null || news.getContent() == "") {
                return ;
            }
            Document doc = Jsoup.parse(news.getContent());
            String html = doc.toString();
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setUseWideViewPort(true);

            webview.loadData(html,"text/html; charset=UTF-8",null);
        }else {

            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setUseWideViewPort(true);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webview.getSettings().setSupportMultipleWindows(true);
            webview.getSettings().setAppCacheEnabled(true);
            webview.getSettings().setAppCacheMaxSize(10 * 1024 * 1024);
            webview.getSettings().setAppCachePath("");
            webview.getSettings().setDatabaseEnabled(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.getSettings().setGeolocationEnabled(true);
            webview.getSettings().setSaveFormData(false);
            webview.getSettings().setSavePassword(false);
            webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            webview.loadUrl(url);
            httpResponse.execute(url);
        }
    }


    @OnClick({
            R.id.iv_back,
            R.id.iv_collect,
            R.id.iv_share

    })
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_collect:
                if (isSc==2){
                    Toast.makeText(this,"取消收藏",Toast.LENGTH_SHORT).show();
                    iv_collect.setImageResource(R.drawable.syn);
                    isSc=1;
                }else {
                    Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();
                    iv_collect.setImageResource(R.drawable.scy);
                    isSc=2;
                }
                DaoUntils.upDateIsSC(url);

                break;

            case R.id.iv_share:
                News news=DaoUntils.queryByLink(url);

                Intent share_intent = new Intent();
                share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
                share_intent.setType("text/plain");//设置分享内容的类型
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "分享新闻啦～");//添加分享内容标题
                share_intent.putExtra(Intent.EXTRA_TEXT, news.getTitle()+" 进入可阅览详情: "+url);//添加分享内容
                //创建分享的Dialog
                share_intent = Intent.createChooser(share_intent, "分享新闻");
                startActivity(share_intent);
                break;
        }
    }
}
