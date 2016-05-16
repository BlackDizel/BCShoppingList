package org.byters.bcshoppinglist.controllers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.byters.bcshoppinglist.model.ShoppingList;
import org.byters.bcshoppinglist.model.ShoppingListItem;

import java.util.ArrayList;

public class ControllerShoppingList {
    private static ControllerShoppingList instance;
    @Nullable
    private ShoppingList data;


    private ControllerShoppingList() {
    }

    public static ControllerShoppingList getInstance() {
        if (instance == null) instance = new ControllerShoppingList();
        return instance;
    }

    public void setData(@Nullable ShoppingList item) {
        this.data = item;
    }

    public int getCount() {
        return data == null || data.items == null ? 0 : data.items.size();
    }

    @Nullable
    public String getTitle(int position) {
        if (position < 0 || data == null || data.items == null || data.items.size() <= position)
            return null;
        return data.items.get(position).title;
    }

    public boolean isChecked(int position) {
        if (position < 0 || data == null || data.items == null || data.items.size() <= position)
            return false;
        return !data.items.get(position).isNeedToPurchase;
    }

    public void addItem(Context context, String title) {
        if (data == null || context == null || TextUtils.isEmpty(title)) return;

        ShoppingListItem item = new ShoppingListItem();
        item.title = title;
        item.isNeedToPurchase = true;

        if (data.items == null)
            data.items = new ArrayList<>();
        data.items.add(item);

        ControllerList.getInstance().saveCache(context);
    }

    public void setItemChecked(Context context, ShoppingListItem item, boolean isChecked) {
        if (data == null) return;
        if (item.isNeedToPurchase != !isChecked) {
            item.isNeedToPurchase = !isChecked;
            ControllerList.getInstance().saveCache(context);
        }
    }

    @Nullable
    public ShoppingListItem getItem(int position) {
        if (position < 0 || data == null || data.items == null || data.items.size() <= position)
            return null;
        return data.items.get(position);
    }
}
