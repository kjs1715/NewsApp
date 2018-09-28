/*
 *   Copyright 2016 Marco Gomiero
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.java.jinzhenshu.parse;

import android.os.AsyncTask;
import android.util.Log;


import com.java.jinzhenshu.bean.News;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Parser extends AsyncTask<String, Void, String> implements Observer {

    private XMLParser xmlParser;

    private OnTaskCompleted onComplete;

    public Parser() {

        xmlParser = new XMLParser();
        xmlParser.addObserver(this);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(List<News> list);

        void onError();
    }

    public void onFinish(OnTaskCompleted onComplete) {
        this.onComplete = onComplete;
    }

    @Override
    protected String doInBackground(String... ulr) {

        Response response = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                 .addHeader("Content-Type","text/html; charset=UTF-8")
                .url(ulr[0])
                .build();

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful())
                return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            onComplete.onError();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        try {

            Document document= DocumentHelper.parseText(result);
            Element rss=document.getRootElement();
            Element channel =rss.element("channel");
            List<Element> items=channel.elements("item");

            List<News> newsList=new ArrayList<>();
            for (Element element:items){
                News news=new News();
                news.setAuthor(element.elementText("author"));
                news.setCategory(element.elementText("category"));
//                news.setDescription(element.elementText("description"));
                news.setLink(element.elementText("link"));
                news.setPubDate(element.elementText("pubDate"));
                news.setTitle(element.elementText("title"));
                newsList.add(news);
            }
            onComplete.onTaskCompleted(newsList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable observable, Object data) {

    }

}
