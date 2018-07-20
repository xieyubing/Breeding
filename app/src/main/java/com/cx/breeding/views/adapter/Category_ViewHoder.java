package com.cx.breeding.views.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.breeding.R;
import com.cx.breeding.bean.Category;
import com.cx.breeding.views.currency.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by asus on 2017/11/7.
 */

public class Category_ViewHoder extends BaseViewHolder<Category> {

    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    public Category_ViewHoder(View itemView) {
        super(itemView);
    }

    @Override
    public void binViewDate(Category date) {
        if (date.getPic() != null){
            ivImg.setImageURI(Uri.parse(date.getPic()));
        }
        tvName.setText(date.getName());
    }
}
