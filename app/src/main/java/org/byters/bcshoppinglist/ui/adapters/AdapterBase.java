package org.byters.bcshoppinglist.ui.adapters;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byters.bcshoppinglist.R;


public class AdapterBase extends RecyclerView.Adapter<AdapterBase.ViewHolder> {
    public AdapterBase() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(getId(), parent, false));
    }

    @LayoutRes
    private int getId() {
        return R.layout.view_shopping_list_item;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {

        }

    }
}
