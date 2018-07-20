package com.cx.breeding.views.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cx.breeding.MainActivity;
import com.cx.breeding.R;
import com.cx.breeding.bean.Animal;
import com.cx.breeding.views.adapter.Animal_Adapter;
import com.cx.breeding.views.currency.RecyclerViewClickListener;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by asus on 2017/11/7.
 */

public class AnimalActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.tl_custom)
    Toolbar tlCustom;
    @BindView(R.id.recyler_view)
    RecyclerView recylerView;
    private LinearLayoutManager layoutManager;
    private List<Animal> Alist;
    private Animal_Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_main);
        int a = getIntent().getIntExtra("Id",0);
        if (a!=0){
            Alist = DataSupport.where("category = ?", String.valueOf(a)).find(Animal.class);
            Log.i("大小：",Alist.size()+"");
        }
        initAniml();
        setrecylerViewListener();
    }

    private void initAniml() {
        layoutManager = new LinearLayoutManager(this);
        recylerView.setLayoutManager(layoutManager);
        adapter = new Animal_Adapter(this);
        adapter.setBottomCount(1);
        adapter.setHeaderCount(0);
        adapter.addItems(Alist);
        recylerView.setAdapter(adapter);
    }

    private void setrecylerViewListener() {

        recylerView.addOnItemTouchListener(new RecyclerViewClickListener(this, recylerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position<Alist.size()){
                    Intent intent = new Intent(getApplicationContext(), AnimalItemActivity.class);
                    intent.putExtra("Id",Alist.get(position).getId());
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(getApplicationContext(), AddAnimalActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (position<Alist.size()){
                    showDialog1(position);
                }
            }
        }));

    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.img_search:
                break;
        }
    }

    private void showDialog1(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("系统提示");
        builder.setMessage("确定删除选中的数据吗？");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               int a =  DataSupport.delete(Animal.class,Alist.get(position).getId());
                if (a>0){
                    Toast.makeText(getApplication(),"删除成功",Toast.LENGTH_LONG).show();
                    //刷新？？
                   adapter.removeItem(position);
                }else {
                    Toast.makeText(getApplication(),"删除失败",Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.show();
    }
}
