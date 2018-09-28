package com.java.jinzhenshu.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class News {

    @Id(autoincrement = true)
    private Long id=null;

    private String title;
    private String link;
    private String author;
    private String category;
    private String pubDate;
    private String description;
    /*是否收藏 收藏是2*/
    private int isSC;
   /*是够阅读 阅读是2*/
    private int isRead;
    /*数据分类*/
    private int type;
    /*最后一次阅读的时间*/
    private Long lastTime=0l;
    /*离线加载的内容*/
    private String content;


    @Generated(hash = 59813017)
    public News(Long id, String title, String link, String author, String category,
            String pubDate, String description, int isSC, int isRead, int type,
            Long lastTime, String content) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.author = author;
        this.category = category;
        this.pubDate = pubDate;
        this.description = description;
        this.isSC = isSC;
        this.isRead = isRead;
        this.type = type;
        this.lastTime = lastTime;
        this.content = content;
    }

    @Generated(hash = 1579685679)
    public News() {
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsSC() {
        return isSC;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setIsSC(int isSC) {
        this.isSC = isSC;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
