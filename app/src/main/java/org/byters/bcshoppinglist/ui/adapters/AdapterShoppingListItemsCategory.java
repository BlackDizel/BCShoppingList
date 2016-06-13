package org.byters.bcshoppinglist.ui.adapters;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerShoppingList;


public class AdapterShoppingListItemsCategory extends RecyclerView.Adapter<AdapterShoppingListItemsCategory.ViewHolder> {
    public AdapterShoppingListItemsCategory() {
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
        return ControllerShoppingList.getInstance().getCountInCategory();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }

        public void setData(int position) {
            String title = ControllerShoppingList.getInstance().getTitleInCategory(position);
            tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        }

    }
}
