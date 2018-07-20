package com.cx.breeding;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cx.breeding.bean.Animal;
import com.cx.breeding.bean.Category;
import com.cx.breeding.bean.User;
import com.cx.breeding.util.CraetExcel;
import com.cx.breeding.util.SPHelper;
import com.cx.breeding.views.activity.AddCategory;
import com.cx.breeding.views.activity.AddUserActivity;
import com.cx.breeding.views.activity.AnimalActivity;
import com.cx.breeding.views.activity.BaseActivity;
import com.cx.breeding.views.activity.SearchActivity;
import com.cx.breeding.views.activity.UserActivity;
import com.cx.breeding.views.adapter.Category_Adapter;
import com.cx.breeding.views.adapter.User_Adapter;
import com.cx.breeding.views.currency.RecyclerViewClickListener;
import com.example.zhouwei.library.CustomPopWindow;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.tl_custom)
    Toolbar tlCustom;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.recyler_view)
    RecyclerView recylerView;
    private long firstTime = 0;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private List<Category> Clists;
    private List<User> Ulists;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;

    private View headerView;
    private Category_Adapter adapter;
    private User_Adapter adapter1;
    private CustomPopWindow popWindow;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "导出成功！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "导出失败！", Toast.LENGTH_LONG).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDelegate().setLocalNightMode(SPHelper.getInstance().isNightMode() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawerLayout.setFitsSystemWindows(true);
            drawerLayout.setClipToPadding(false);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, tlCustom, 0, 0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        headerView = navigation.inflateHeaderView(R.layout.nav_header);
        if (!SPHelper.getInstance().isNightMode()) {
            TextView tv_name = (TextView) headerView.findViewById(R.id.tv_name);
            tv_name.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        }
        initCategory();
        setNavigationViewListener();
        setrecylerViewListener();
    }

    private void setrecylerViewListener() {

        recylerView.addOnItemTouchListener(new RecyclerViewClickListener(this, recylerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (Clists == null) {
                    if (position < Ulists.size()) {
                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                        intent.putExtra("Id", Ulists.get(position).getId());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                        startActivity(intent);
                    }
                } else {
                    if (position < Clists.size()) {
                        Intent intent = new Intent(getApplicationContext(), AnimalActivity.class);
                        intent.putExtra("Id", Clists.get(position).getId());
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(MainActivity.this, AddCategory.class));
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (Clists == null) {
                    if (position < Ulists.size()) {
                        showDialog1(position);
                    }
                } else {
                    if (position < Clists.size()) {
                        showDialog1(position);
                    }
                }
            }
        }));

    }

    private void initCategory() {
        layoutManager = new GridLayoutManager(this, 2);
        Clists = DataSupport.findAll(Category.class);
        recylerView.setLayoutManager(layoutManager);
        adapter = new Category_Adapter(this);
        adapter.setBottomCount(1);
        adapter.setHeaderCount(0);
        adapter.addItems(Clists);
        recylerView.setAdapter(adapter);
        Ulists = null;
    }

    private void initUser() {
        layoutManager1 = new LinearLayoutManager(this);
        Ulists = DataSupport.findAll(User.class);
        recylerView.setLayoutManager(layoutManager1);
        adapter1 = new User_Adapter(this);
        adapter1.setBottomCount(1);
        adapter1.setHeaderCount(0);
        adapter1.addItems(Ulists);
        recylerView.setAdapter(adapter1);
        Clists = null;
    }


    public void setNavigationViewListener() {
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_1:
                        initCategory();
                        navigation.getMenu().getItem(0).setChecked(true);
                        navigation.getMenu().getItem(2).setChecked(false);
                        navigation.getMenu().getItem(4).setChecked(false);
                        navigation.getMenu().getItem(3).setChecked(false);
                        break;
                    case R.id.item_2:
                        setDayNight();
                        break;
                    case R.id.item_3:
                        initUser();
                        navigation.getMenu().getItem(2).setChecked(true);
                        navigation.getMenu().getItem(0).setChecked(false);
                        navigation.getMenu().getItem(3).setChecked(false);
                        navigation.getMenu().getItem(4).setChecked(false);
                        break;
                    case R.id.item_4:
                        initExport();
                        navigation.getMenu().getItem(3).setChecked(true);
                        navigation.getMenu().getItem(0).setChecked(false);
                        navigation.getMenu().getItem(2).setChecked(false);
                        navigation.getMenu().getItem(4).setChecked(false);
                        break;
                    case R.id.item_5:
                        Toast.makeText(getApplicationContext(), "使用中有问题请联系开发者！", Toast.LENGTH_LONG).show();
                        navigation.getMenu().getItem(4).setChecked(true);
                        navigation.getMenu().getItem(3).setChecked(false);
                        navigation.getMenu().getItem(2).setChecked(false);
                        navigation.getMenu().getItem(0).setChecked(false);
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });


    }


    public void setDayNight() {
        if (SPHelper.getInstance().isNightMode()) {
            SPHelper.getInstance().setNightMode(false);
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        } else {
            SPHelper.getInstance().setNightMode(true);
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            recreate();
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
                int a = 0;
                if (Clists == null) {
                    a = DataSupport.delete(User.class, Ulists.get(position).getId());
                } else {
                    int count = DataSupport.deleteAll(Animal.class, "category=?", String.valueOf(Clists.get(position).getId()));
                    if (count > 0) {
                        a = DataSupport.delete(Category.class, Clists.get(position).getId());
                    }
                }
                if (a > 0) {
                    Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_LONG).show();
                    //刷新？？
                    if (Clists == null) {
                        adapter1.removeItem(position);
                    } else {
                        adapter.removeItem(position);
                    }
                } else {
                    Toast.makeText(getApplication(), "删除失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.show();
    }

    public void initExport() {
        Toast.makeText(getApplicationContext(), "正在导出中，请稍后", Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CraetExcel excel = new CraetExcel();
                Message message = new Message();
                if (excel.craetExcel()) {
                    message.what = 1;
                } else {
                    message.what = 2;
                }
                SystemClock.sleep(2000);
                mHandler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                finish();
                System.exit(0);
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    @OnClick(R.id.img_search)
    public void onViewClicked() {
        if (Ulists == null ){
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("Id",1);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("Id",2);
            startActivity(intent);
        }

    }
}
