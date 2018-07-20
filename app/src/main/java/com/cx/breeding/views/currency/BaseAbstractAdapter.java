package com.cx.breeding.views.currency;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/7/18.
 */

public abstract class BaseAbstractAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    protected final String TAG = getClass().getSimpleName();
    protected final Context mContext;
    protected final LayoutInflater mlayoutInflter;
    protected List<T> mDateList = new ArrayList<>();

    public BaseAbstractAdapter(Context context){
        mContext = context;
        mlayoutInflter = LayoutInflater.from(mContext);
    }


    public Context getContext() {
        return mContext;
    }

    public List<T> getmDateList() {
        return mDateList;
    }


    public T getItemDate(int postion){
        return (postion>=0 && postion < mDateList.size())?mDateList.get(postion):null;
    }

    public void removeItem(int position){
        if (position>=0&&position<mDateList.size()){
            mDateList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addItem(T data,int position){
        if (position>=0&&position<=mDateList.size()){
            mDateList.add(position,data);
            notifyItemInserted(position);
        }
    }

    public void addItem(T data){
        addItem(data,mDateList.size());
    }

    public void clerItems(){
        int size = mDateList.size();
        if (size>0){
            mDateList.clear();
            notifyItemRangeRemoved(0,size);
        }
    }

    public void addItems(List<T> data,int position){
        if (position>=0&&position<=mDateList.size()&&data!=null&&data.size()>0){
            mDateList.addAll(position,data);
            notifyItemRangeChanged(position,data.size());
        }
    }

    public void addItems(List<T> data) {
        addItems(data, mDateList.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseViewHolder){
            ((BaseViewHolder) holder).binViewDate(getItemDate(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDateList == null ? 0 : mDateList.size();
    }
}
