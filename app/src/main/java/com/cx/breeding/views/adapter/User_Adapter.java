package com.cx.breeding.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cx.breeding.R;
import com.cx.breeding.bean.User;
import com.cx.breeding.views.currency.BaseAbstractMultipleItemAdapter;

import java.util.List;

/**
 * Created by asus on 2017/11/7.
 */

public class User_Adapter extends BaseAbstractMultipleItemAdapter<User> {


    public User_Adapter(Context context) {
        super(context);
    }

    @Override
    public void setBottomCount(int bottomCount) {
        super.setBottomCount(bottomCount);
    }

    @Override
    public void setHeaderCount(int headerCount) {
        super.setHeaderCount(headerCount);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentView(ViewGroup parent, int viewType) {
        return new User_ViewHoder(mlayoutInflter.inflate(R.layout.user_list_item,null));
    }

    @Override
    public RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {
        return new BotttomViewHoder(mlayoutInflter.inflate(R.layout.activity_bottom, null));
    }


    @Override
    public void addItems(List<User> data) {
        super.addItems(data);
    }

    @Override
    public void removeItem(int position) {
        super.removeItem(position);
    }
}
