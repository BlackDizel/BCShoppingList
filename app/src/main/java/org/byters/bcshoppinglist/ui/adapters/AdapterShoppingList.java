package org.byters.bcshoppinglist.ui.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerShoppingList;
import org.byters.bcshoppinglist.model.ShoppingListItem;


public class AdapterShoppingList extends RecyclerView.Adapter<AdapterShoppingList.ViewHolder> {

    public AdapterShoppingList() {
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
        return ControllerShoppingList.getInstance().getCount();
    }

    public void addItem() {
        //todo: replace with notifyItemAdded
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
            implements CheckBox.OnCheckedChangeListener {
        private TextView tvTitle;
        private CheckBox cbItem;

        @Nullable
        private ShoppingListItem item;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            cbItem = (CheckBox) itemView.findViewById(R.id.cbItem);

            cbItem.setOnCheckedChangeListener(this);
        }

        public void setData(int position) {
            item = ControllerShoppingList.getInstance().getItem(position);
            String text = ControllerShoppingList.getInstance().getTitle(position);
            tvTitle.setText(TextUtils.isEmpty(text) ? "" : text);
            cbItem.setChecked(ControllerShoppingList.getInstance().isChecked(position));
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ControllerShoppingList.getInstance().setItemChecked(buttonView.getContext(), item, isChecked);
        }
    }
}
