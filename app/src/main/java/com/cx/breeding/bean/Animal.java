package com.cx.breeding.bean;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by asus on 2017/11/7.
 */

public class Animal extends DataSupport {

    private int id;
    private String name;  //名称
    private String imgurl; //图片
    private Date date; //时间
    private int category; //类别
    private String quality;//质量

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", date=" + date +
                ", category=" + category +
                ", quality=" + quality +
                '}';
    }
}
