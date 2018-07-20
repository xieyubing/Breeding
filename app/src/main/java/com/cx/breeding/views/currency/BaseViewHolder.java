package com.cx.breeding.views.currency;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by asus on 2017/7/18.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }



    public abstract void binViewDate(T date);
}
