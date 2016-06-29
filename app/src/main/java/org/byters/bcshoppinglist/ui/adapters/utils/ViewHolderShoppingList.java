package org.byters.bcshoppinglist.ui.adapters.utils;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerShoppingList;
import org.byters.bcshoppinglist.model.ShoppingListItem;
import org.byters.bcshoppinglist.ui.adapters.AdapterShoppingListItemsCategory;

public class ViewHolderShoppingList extends RecyclerView.ViewHolder
        implements CheckBox.OnCheckedChangeListener
        , View.OnClickListener {
    public static final int TYPE_ALL = 0;
    public static final int TYPE_CATEGORY = 1;
    private RecyclerView.Adapter<ViewHolderShoppingList> adapter;
    private TextView tvTitle;
    private CheckBox cbItem;
    private int type;

    @Nullable
    private ShoppingListItem item;

    public ViewHolderShoppingList(RecyclerView.Adapter<ViewHolderShoppingList> adapter, View itemView, int type) {
        super(itemView);
        this.type = type;
        this.adapter = adapter;
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        cbItem = (CheckBox) itemView.findViewById(R.id.cbItem);

        cbItem.setOnCheckedChangeListener(this);

        View vClose = itemView.findViewById(R.id.ivRemove);
        vClose.setVisibility(type == TYPE_CATEGORY ? View.GONE : View.VISIBLE);
        vClose.setOnClickListener(this);
    }

    public void setData(int position) {
        tvTitle.setText("");
        cbItem.setChecked(false);

        item = type == TYPE_ALL
                ? ControllerShoppingList.getInstance().getItem(position)
                : ControllerShoppingList.getInstance().getItemInCategory(position);

        if (item == null) return;
        String text = item.title;
        tvTitle.setText(TextUtils.isEmpty(text) ? "" : text);
        cbItem.setChecked(!item.isNeedToPurchase);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (item == null) return;
        ControllerShoppingList.getInstance().setItemChecked(buttonView.getContext(), item, isChecked);
    }

    @Override
    public void onClick(View v) {
        if (item == null) return;
        ControllerShoppingList.getInstance().removeItem(v.getContext(), item);
        //todo use notifyItemRemoved();

        if (adapter instanceof AdapterShoppingListItemsCategory)
            ((AdapterShoppingListItemsCategory) adapter).updateData();
        else
            adapter.notifyDataSetChanged();
    }
}
