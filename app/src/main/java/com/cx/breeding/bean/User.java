package com.cx.breeding.bean;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by asus on 2017/11/7.
 */

public class User extends DataSupport {
    private int id;
    private String username;
    private String password;
    private char sex;
    private int age;
    private String imgurl;
    private Date date;


    private int level;//级别

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", imgurl='" + imgurl + '\'' +
                ", date=" + date +
                ", level=" + level +
                '}';
    }
}
