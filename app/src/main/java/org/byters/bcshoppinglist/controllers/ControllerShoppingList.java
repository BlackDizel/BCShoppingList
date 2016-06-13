package org.byters.bcshoppinglist.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.byters.bcshoppinglist.model.Product;
import org.byters.bcshoppinglist.model.ShoppingList;
import org.byters.bcshoppinglist.model.ShoppingListItem;
import org.byters.bcshoppinglist.model.StoreCategory;

import java.util.ArrayList;
import java.util.Collections;

public class ControllerShoppingList {
    private static ControllerShoppingList instance;
    @Nullable
    private ShoppingList data;
    private ArrayList<ShoppingListItem> dataInCategory;

    private ControllerShoppingList() {
    }

    public static ControllerShoppingList getInstance() {
        if (instance == null) instance = new ControllerShoppingList();
        return instance;
    }

    public void setItemsInCategory(@NonNull StoreCategory category) {
        if (data == null || data.items == null || category == null) return;

        ArrayList<ShoppingListItem> result = null;
        for (ShoppingListItem item : data.items)
            for (Product product : category.products) {
                if (product.name.contains(item.title)) {
                    if (result == null) result = new ArrayList<>();
                    result.add(item);
                    break;
                }
            }
        dataInCategory = result;
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
            if (!item.isNeedToPurchase && ControllerList.getInstance().isPurchased(data)) {
                if (data.purchasesDates == null) data.purchasesDates = new ArrayList<>();
                data.purchasesDates.add(System.currentTimeMillis());
                Collections.sort(data.purchasesDates);
                Collections.reverse(data.purchasesDates);
            }

            ControllerList.getInstance().saveCache(context);
        }
    }

    @Nullable
    public ShoppingListItem getItem(int position) {
        if (position < 0 || data == null || data.items == null || data.items.size() <= position)
            return null;
        return data.items.get(position);
    }

    public void removeItem(Context context, ShoppingListItem item) {
        if (item == null || data == null || data.items == null) return;
        data.items.remove(item);
        ControllerList.getInstance().saveCache(context);
    }

    @Nullable
    public ArrayList<ShoppingListItem> getItems() {
        return data == null ? null : data.items;
    }

    public void clearCategory() {
        dataInCategory = null;
    }

    public int getCountInCategory() {
        return dataInCategory == null ? 0 : dataInCategory.size();
    }

    @Nullable
    public String getTitleInCategory(int position) {
        if (dataInCategory == null || position < 0 || position >= dataInCategory.size())
            return null;
        return dataInCategory.get(position).title;
    }
}
