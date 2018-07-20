package com.cx.breeding;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cx.breeding.bean.User;
import com.cx.breeding.views.activity.BaseActivity;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by asus on 2017/11/9.
 */

public class LoginActivity extends BaseActivity {
    final int REQUEST_WRITE = 1;//申请权限的请求码
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.but_log)
    Button butLog;
    private long firstTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Log.i("readTosdCard", "我们需要这个权限给你提供存储服务");
                    showAlert();
                } else {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
                }
            } else {
                init();
            }
        } else {
            init();
        }


    }

    public void init() {
        File sdcardDir = Environment.getExternalStorageDirectory();
        String path = sdcardDir.getPath() + "/Breeding";
        File path1 = new File(path);
        if (!path1.exists()) {
            path1.mkdirs();
        }
        File path2 = new File(path + "/image");
        if (!path2.exists()) {
            path2.mkdirs();
        }
        File path3 = new File(path + "/Excel");
        if (!path3.exists()) {
            path3.mkdirs();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }

    }

    private void showAlert() {
        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("权限说明").
                setMessage("我们需要这个权限给你提供存储服务").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
                    }
                }).
                setNegativeButton("取消", null).
                create();
        alertDialog.show();
    }

    @OnClick(R.id.but_log)
    public void onViewClicked() {
        String name = etUsername.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        if (name.equals("")||name.length()==0||name == null){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_LONG).show();
            return;
        }else if (pwd.equals("")||pwd.length()==0||pwd == null){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_LONG).show();
            return;
        }else {
            List<User> users = DataSupport.where("username = ? AND password = ?", name,pwd).find(User.class);
            if (users.size()>0){
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }else {Toast.makeText(this,"登录失败！",Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(LoginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                finish();
                System.exit(0);
            }
        }

        return super.onKeyUp(keyCode, event);
    }
}
