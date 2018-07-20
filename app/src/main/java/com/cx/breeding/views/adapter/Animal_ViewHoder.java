package com.cx.breeding.views.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.breeding.R;
import com.cx.breeding.bean.Animal;
import com.cx.breeding.views.currency.BaseViewHolder;

import java.text.SimpleDateFormat;

import butterknife.BindView;

/**
 * Created by asus on 2017/11/7.
 */

public class Animal_ViewHoder extends BaseViewHolder<Animal> {


    @BindView(R.id.iv_portrait)
    ImageView ivPortrait;
    @BindView(R.id.tv_name)
    TextView tvname;
    @BindView(R.id.tv_author)
    TextView tvauthor;
    @BindView(R.id.tv_date)
    TextView tvdate;
    @BindView(R.id.tv_upchapter)
    TextView tvUpchapter;

    public Animal_ViewHoder(View itemView){
        super(itemView);
    }

    @Override
    public void binViewDate(Animal date) {
        if (date.getImgurl() != null){
            ivPortrait.setImageURI(Uri.parse(date.getImgurl()));
        }

        tvname.setText(date.getName());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        tvdate.setText(sdf.format(date.getDate()));
    }
}
