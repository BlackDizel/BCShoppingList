package org.byters.bcshoppinglist.ui.adapters;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerShoppingList;
import org.byters.bcshoppinglist.ui.adapters.utils.AdapterUpdateListener;
import org.byters.bcshoppinglist.ui.adapters.utils.ViewHolderShoppingList;

import java.util.ArrayList;


public class AdapterShoppingListItemsCategory extends RecyclerView.Adapter<ViewHolderShoppingList> {

    private ArrayList<AdapterUpdateListener> listeners;
    public void addListener(AdapterUpdateListener listener){
        if (listeners== null) listeners = new ArrayList<>();
        listeners = new ArrayList<>();
        listeners.add(listener);
    }
    public void removeListener(AdapterUpdateListener listener){
        if (listeners==null) return;
        listeners.remove(listener);
    }

    public void updateData(){
        notifyDataSetChanged();
        if (listeners==null) return;
        for (AdapterUpdateListener listener: listeners)
            listener.onAdapterUpdate();
    }


    public AdapterShoppingListItemsCategory() {
    }

    @Override
    public ViewHolderShoppingList onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderShoppingList(this, LayoutInflater.from(parent.getContext()).inflate(getId(), parent, false), ViewHolderShoppingList.TYPE_CATEGORY);
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
        return ControllerShoppingList.getInstance().getCountInCategory();
    }


}
