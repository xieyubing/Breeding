package com.cx.breeding.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by asus on 2017/11/7.
 */

public class Category extends DataSupport {

    private int id; //类别id
    private String name; //类别名称
    private String pic;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
