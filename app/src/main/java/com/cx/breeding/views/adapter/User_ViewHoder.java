package com.cx.breeding.views.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.breeding.R;
import com.cx.breeding.bean.User;
import com.cx.breeding.views.currency.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by asus on 2017/11/7.
 */

public class User_ViewHoder extends BaseViewHolder<User> {

    @BindView(R.id.iv_portrait)
    ImageView ivPortrait;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_level)
    TextView tvLevel;

    public User_ViewHoder(View itemView) {
        super(itemView);
    }

    @Override
    public void binViewDate(User date) {
        if (date.getImgurl() != null){
            ivPortrait.setImageURI(Uri.parse(date.getImgurl()));
        }

        tvName.setText(date.getUsername());
        switch (date.getLevel()){
            case 1:
                tvLevel.setText("管理员");
                break;
            case 2:
                tvLevel.setText("普通用户");
                break;
            case 3:
                tvLevel.setText("Vip用户");
                break;
        }
    }
}
