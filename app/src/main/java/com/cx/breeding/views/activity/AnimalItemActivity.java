package com.cx.breeding.views.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.breeding.MainActivity;
import com.cx.breeding.R;
import com.cx.breeding.bean.Animal;
import com.cx.breeding.bean.Category;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus on 2017/11/8.
 */

public class AnimalItemActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.iv_modify)
    ImageView ivModify;
    @BindView(R.id.tl_custom)
    Toolbar tlCustom;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.tv_quality)
    TextView tvQuality;
    private int id;
    private  Animal animal;
    private String category;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list_item);
       id = getIntent().getIntExtra("Id",0);
        if (id!=0){
           animal = DataSupport.find(Animal.class,id);
            category = DataSupport.find(Category.class,animal.getCategory()).getName();
            if (animal != null){
                if (animal.getImgurl() != null){
                    profileImage.setImageURI(Uri.parse(animal.getImgurl()));
                }
                profileImage.setImageURI(Uri.parse(animal.getImgurl()));
                tvName.setText(animal.getName());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                tvDate.setText(sdf.format(animal.getDate()));
                tvCategory.setText(category);
                tvQuality.setText("优");
            }
        }
    }

    @OnClick({R.id.img_back, R.id.iv_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_modify:
                intent = new Intent(this,ModifyAnimalActivity.class);
                intent.putExtra("Id",id);
                startActivity(intent);
                finish();
                break;
        }
    }
}
