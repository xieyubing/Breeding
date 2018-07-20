package com.cx.breeding.views.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.cx.breeding.bean.Animal;
import com.cx.breeding.bean.Category;
import com.cx.breeding.bean.User;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Date;

import butterknife.ButterKnife;

/**
 * Created by asus on 2017/8/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Connector.getDatabase();
        if (DataSupport.findAll(User.class).size()<=0){
            User user = new User();
            user.setDate(new Date());
            user.setUsername("admin");
            user.setPassword("123456");
            user.setSex('男');
            user.setAge(22);
            user.setLevel(1);
            user.save();
        }

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setStatusBar();
    }


    protected void setStatusBar() {
       /* StatusBarCompat.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary));*/
    }


}
