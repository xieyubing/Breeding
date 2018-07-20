package com.cx.breeding.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cx.breeding.R;
import com.cx.breeding.bean.Animal;
import com.cx.breeding.views.currency.BaseAbstractMultipleItemAdapter;

import java.util.List;

/**
 * Created by asus on 2017/11/7.
 */

public class Animal_Adapter extends BaseAbstractMultipleItemAdapter<Animal> {
    public Animal_Adapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentView(ViewGroup parent, int viewType) {
        return new Animal_ViewHoder(mlayoutInflter.inflate(R.layout.animal_list,null));
    }

    @Override
    public RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {
        return new BotttomViewHoder(mlayoutInflter.inflate(R.layout.activity_bottom, null));
    }

    @Override
    public void addItems(List<Animal> data) {
        super.addItems(data);
    }

    @Override
    public void removeItem(int position) {
        super.removeItem(position);
    }


}
