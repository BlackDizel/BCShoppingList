package org.byters.bcshoppinglist.ui.adapters;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerShoppingList;
import org.byters.bcshoppinglist.ui.adapters.utils.ViewHolderShoppingList;


public class AdapterShoppingList extends RecyclerView.Adapter<ViewHolderShoppingList> {

    public AdapterShoppingList() {
    }

    @Override
    public ViewHolderShoppingList onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderShoppingList(this, LayoutInflater.from(parent.getContext()).inflate(getId(), parent, false), ViewHolderShoppingList.TYPE_ALL);
    }

    @LayoutRes
    private int getId() {
        return R.layout.view_shopping_list_item;
    }

    @Override
    public void onBindViewHolder(ViewHolderShoppingList holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return ControllerShoppingList.getInstance().getCount();
    }

    public void addItem() {
        //todo: replace with notifyItemAdded
        notifyDataSetChanged();
    }
}
