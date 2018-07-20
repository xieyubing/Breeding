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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cx.breeding.MainActivity;
import com.cx.breeding.R;
import com.cx.breeding.bean.Animal;
import com.cx.breeding.bean.Category;
import com.example.zhouwei.library.CustomPopWindow;

import org.litepal.crud.DataSupport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by asus on 2017/11/8.
 */

public class ModifyAnimalActivity extends BaseActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.iv_sava)
    ImageView ivSava;
    @BindView(R.id.tl_custom)
    Toolbar tlCustom;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.sp_category)
    Spinner spCategory;
    @BindView(R.id.et_quality)
    EditText etQuality;
    private int id;
    private  Animal animal;
    private List<Category> Clist;
    private List<String> list;
    private ArrayAdapter<String> arr_adapter;
    private int category;
    private  CustomPopWindow popWindow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_list_item_add);
        id = getIntent().getIntExtra("Id",0);
        Log.i("Id==",id+"");
        if (id!=0){
            animal = DataSupport.find(Animal.class,id);
            list = new ArrayList<>();
            Clist = DataSupport.select("name").find(Category.class);
            for (Category c : Clist) {
                list.add(c.getName());
            }

            etName.setText(animal.getName());
            arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategory.setAdapter(arr_adapter);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            tvDate.setText(sdf.format(new Date()));
            etQuality.setText("优");

            spCategory.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String sInfo = parent.getItemAtPosition(position).toString();
                    Log.i("选择的是：", sInfo);
                    Log.i("下标为：", position + "");
                    category = position + 1;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


    }

    @OnClick({R.id.img_back, R.id.iv_sava,R.id.profile_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_sava:
                init();
                if (animal.update(id)>0){
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this,"添加失败！",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.profile_image:
                View rootview = LayoutInflater.from(this).inflate(R.layout.animal_list_item_add,null);
                View contentView = LayoutInflater.from(this).inflate(R.layout.popue_window,null);
                handleLogic(contentView);
                popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView)
                        .setFocusable(true)
                        .setOutsideTouchable(true)
                        .setAnimationStyle(R.style.popup_anim_style)
                        .create()
                        .showAtLocation(rootview, Gravity.BOTTOM,0,0);
                break;
        }
    }

    private void init() {
        String name = etName.getText().toString().trim();
        String quality = etQuality.getText().toString().trim();
        if (name.equals("") || name.length() == 0 || name == null) {
            Toast.makeText(this, "动物名称不能为空", Toast.LENGTH_LONG).show();
            return;
        } else if (quality.equals("") || quality.length() == 0 || quality == null) {
            Toast.makeText(this, "质量不能为空", Toast.LENGTH_LONG).show();
            return;
        } else {
            animal.setName(name);
            animal.setQuality(quality);
            animal.setDate(new Date());
            animal.setCategory(category);
        }
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
                    String fileNmae = Environment.getExternalStorageDirectory().toString()+ File.separator+"/Breeding/image/"+name+".jpg";
                    srcPath = fileNmae;
                    System.out.println(srcPath+"----------保存路径1");
                    animal.setImgurl(srcPath);
                    File myCaptureFile =new File(fileNmae);
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
                    animal.setImgurl(srcPath);
                    break;
            }
        }
    }
}
