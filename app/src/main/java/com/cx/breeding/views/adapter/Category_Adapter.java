package com.cx.breeding.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cx.breeding.R;
import com.cx.breeding.bean.Category;
import com.cx.breeding.views.currency.BaseAbstractMultipleItemAdapter;

import java.util.List;

/**
 * Created by asus on 2017/11/7.
 */

public class Category_Adapter extends BaseAbstractMultipleItemAdapter<Category> {


    @Override
    public void setBottomCount(int bottomCount) {
        super.setBottomCount(bottomCount);
    }

    @Override
    public void setHeaderCount(int headerCount) {
        super.setHeaderCount(headerCount);
    }

    public Category_Adapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentView(ViewGroup parent, int viewType) {
        return new Category_ViewHoder(mlayoutInflter.inflate(R.layout.category_list_item, null));
    }

    @Override
    public RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {
        return new BotttomViewHoder(mlayoutInflter.inflate(R.layout.list_bottom, null));
    }

    @Override
    public void addItems(List<Category> data) {
        super.addItems(data);
    }

    @Override
    public void removeItem(int position) {
        super.removeItem(position);
    }
}
