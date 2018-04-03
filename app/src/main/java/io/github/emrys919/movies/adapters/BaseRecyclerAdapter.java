package io.github.emrys919.movies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import io.github.emrys919.movies.views.holders.BaseViewHolder;

/**
 * Created by myolwin00 on 6/14/17.
 */

public abstract class BaseRecyclerAdapter<T extends BaseViewHolder, W> extends RecyclerView.Adapter<T> {

    protected LayoutInflater mLayoutInflater;
    protected List<W> mData;

    public BaseRecyclerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<W> newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    public void setNewData(List<W> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    public void addNewData(List<W> newData) {
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData = new ArrayList<>();
        notifyDataSetChanged();
    }

}
