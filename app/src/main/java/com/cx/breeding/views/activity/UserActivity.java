package com.cx.breeding.views.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cx.breeding.MainActivity;
import com.cx.breeding.R;
import com.cx.breeding.bean.User;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus on 2017/11/8.
 */

public class UserActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tl_custom)
    Toolbar tlCustom;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_level)
    TextView tvLevel;

    private User user;
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        id = getIntent().getIntExtra("Id", 0);
        if (id != 0) {
            user = DataSupport.find(User.class, id);
        }
        if (user != null) {
           if (user.getImgurl() != null){
               profileImage.setImageURI(Uri.parse(user.getImgurl()));
           }
            tvUsername.setText(user.getUsername());
            tvPassword.setText(user.getPassword());
            tvAge.setText(String.valueOf(user.getAge()));
            tvSex.setText(String .valueOf(user.getSex()));
            switch (user.getLevel()) {
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


    @OnClick({R.id.img_back, R.id.iv_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_modify:
                intent = new Intent(this, ModifyUserActivity.class);
                intent.putExtra("Id",id);
                Log.i("id==",id+"");
                startActivity(intent);
                finish();
                break;
        }
    }
}
