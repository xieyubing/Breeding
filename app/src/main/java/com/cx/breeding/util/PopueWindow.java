package com.cx.breeding.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.cx.breeding.R;

/**
 * Created by asus on 2017/11/9.
 */

public class PopueWindow {

   private Context context;
    public PopueWindow( Context context){
        this.context = context;
    }
    public void showPopueWindow(){
        View popView = View.inflate(context, R.layout.popue_window,null);
        Button bt_album = (Button) popView.findViewById(R.id.btn_pop_album);
        Button bt_camera = (Button) popView.findViewById(R.id.btn_pop_camera);
        Button bt_cancle = (Button) popView.findViewById(R.id.btn_pop_cancel);
        //获取屏幕宽高
        int weight = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels*1/3;

        final PopupWindow popupWindow = new PopupWindow(popView,weight,height);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(false);

        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
                lp.alpha = 1.0f;
                ((Activity) context).getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.5f;
        ((Activity) context).getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM,0,50);

    }
}
