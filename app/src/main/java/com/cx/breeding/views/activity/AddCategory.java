package com.cx.breeding.views.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cx.breeding.MainActivity;
import com.cx.breeding.R;
import com.cx.breeding.bean.Category;
import com.example.zhouwei.library.CustomPopWindow;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus on 2017/11/10.
 */

public class AddCategory extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.but_sava)
    Button butSava;
    private CustomPopWindow popWindow;
    private Category category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_dialog);
        category = new Category();

    }

    @OnClick({R.id.img_back, R.id.but_sava})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Intent intent = new Intent(AddCategory.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.but_sava:
                String name = etUsername.getText().toString().trim();
                if (name.equals("")||name.length()==0||name == null){
                    Toast.makeText(this,"类别名称不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                category.setName(name);
                if ( category.save()){
                    startActivity(new Intent(AddCategory.this, MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(AddCategory.this,"保存失败！",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    @OnClick(R.id.profile_image)
    public void onViewClicked() {
        View rootview = LayoutInflater.from(this).inflate(R.layout.category_dialog,null);
        View contentView = LayoutInflater.from(this).inflate(R.layout.popue_window,null);
        handleLogic(contentView);
        popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .setAnimationStyle(R.style.popup_anim_style)
                .create()
                .showAtLocation(rootview, Gravity.BOTTOM,0,0);
    }

    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popWindow!=null){
                    popWindow.dissmiss();
                }
                switch (v.getId()){
                    case R.id.btn_pop_album:
                        Intent local = new Intent();
                        local.setType("image/*");
                        local.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(local, 2);
                        break;
                    case R.id.btn_pop_camera:
                        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(it, 1);
                        break;
                    case R.id.btn_pop_cancel:
                        popWindow.dissmiss();
                        break;

                }

            }
        };
        contentView.findViewById(R.id.btn_pop_album).setOnClickListener(listener);
        contentView.findViewById(R.id.btn_pop_camera).setOnClickListener(listener);
        contentView.findViewById(R.id.btn_pop_cancel).setOnClickListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String srcPath;
        if (resultCode == RESULT_OK ) {
            switch(requestCode) {
                case 1:
                    Bundle extras = data.getExtras();
                    Bitmap b = (Bitmap) extras.get("data");
                    profileImage.setImageBitmap(b);
                    String name = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                    String fileNmae = Environment.getExternalStorageDirectory().toString()+File.separator+"/Breeding/image/"+name+".jpg";
                    srcPath = fileNmae;
                    System.out.println(srcPath+"----------保存路径1");
                    File myCaptureFile =new File(fileNmae);
                    category.setPic(fileNmae);
                    try {
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            if (!myCaptureFile.getParentFile().exists()) {
                                myCaptureFile.getParentFile().mkdirs();
                            }
                            BufferedOutputStream bos;
                            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                            b.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                            bos.flush();
                            bos.close();
                        } else {
                            Toast toast= Toast.makeText(this, "保存失败，SD卡无效", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Uri uri = data.getData();
                    profileImage.setImageURI(uri);
                    ContentResolver cr = this.getContentResolver();
                    Cursor c = cr.query(uri, null, null, null, null);
                    c.moveToFirst();
                    srcPath = c.getString(c.getColumnIndex("_data"));
                    System.out.println(srcPath+"----------保存路径2");
                    category.setPic(srcPath);
                    break;
            }
        }
    }
}
