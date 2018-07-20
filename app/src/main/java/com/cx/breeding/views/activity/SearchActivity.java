package com.cx.breeding.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cx.breeding.MainActivity;
import com.cx.breeding.R;
import com.cx.breeding.bean.Animal;
import com.cx.breeding.bean.User;
import com.cx.breeding.views.adapter.Animal_Adapter;
import com.cx.breeding.views.adapter.User_Adapter;
import com.cx.breeding.views.currency.RecyclerViewClickListener;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by asus on 2017/11/10.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.recyler_view)
    RecyclerView recylerView;
    @BindView(R.id.et_search)
    EditText etSearch;
    private int id;
    private LinearLayoutManager layoutManager;
    private List<Animal> Alist;
    private List<User> Ulists;
    private Animal_Adapter Aadapter;
    private User_Adapter Uadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        id = getIntent().getIntExtra("Id", 0);
        layoutManager = new LinearLayoutManager(this);
        recylerView.setLayoutManager(layoutManager);
        setrecylerViewListener();
    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.img_search:
                String search = etSearch.getText().toString().trim();
                if(search.equals("")||search.length()==0||search == null){
                    Toast.makeText(this,"搜索内容不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (id == 1) {
                    Alist = DataSupport.where("name like ?","%"+search+"%").find(Animal.class);
                    if (Alist.size()<=0){
                        Toast.makeText(this,"需要查询的数据为空！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Aadapter = new Animal_Adapter(this);
                    Aadapter.setBottomCount(0);
                    Aadapter.setHeaderCount(0);
                    Aadapter.addItems(Alist);
                    recylerView.setAdapter(Aadapter);
                    Ulists = null;
                } else {
                    Ulists = DataSupport.where("username like ?","%"+search+"%").find(User.class);
                    if (Ulists.size()<=0){
                        Toast.makeText(this,"需要查询的数据为空！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Uadapter = new User_Adapter(this);
                    Uadapter.setBottomCount(0);
                    Uadapter.setHeaderCount(0);
                    Uadapter.addItems(Ulists);
                    recylerView.setAdapter(Uadapter);
                    Alist = null;
                }
                break;
        }
    }

    private void setrecylerViewListener() {

        recylerView.addOnItemTouchListener(new RecyclerViewClickListener(this, recylerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (Alist == null) {
                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                        intent.putExtra("Id", Ulists.get(position).getId());
                        startActivity(intent);
                } else {
                        Intent intent = new Intent(getApplicationContext(), AnimalItemActivity.class);
                        intent.putExtra("Id", Alist.get(position).getId());
                        startActivity(intent);
                }
            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));

    }


}
