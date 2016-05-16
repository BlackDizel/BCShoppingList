package org.byters.bcshoppinglist.controllers;

import android.content.Context;
import android.support.annotation.Nullable;

import org.byters.bcshoppinglist.model.ShoppingList;
import org.byters.bcshoppinglist.model.ShoppingListItem;

import java.util.ArrayList;

public class ControllerList {
    public static final int WAITING = 0;
    public static final int PURCHASED = 1;
    public static final int UNKNOWN = -1;

    private static ControllerList instance;

    @Nullable
    private ArrayList<ShoppingList> data;

    private ControllerList() {
    }

    public static ControllerList getInstance() {
        if (instance == null) instance = new ControllerList();
        return instance;
    }

    public int getCount(@Nullable Context context, int state) {
        if (state != WAITING && state != PURCHASED || context == null) return 0;
        if (getData(context) == null || getData(context).size() == 0) return 0;

        int count = 0;
        for (ShoppingList item : getData(context)) {
            if (isStateEqual(item, state) && !item.isDeleted)
                ++count;
        }
        return count;
    }

    private boolean isStateEqual(ShoppingList item, int state) {
        return isPurchased(item) && state == PURCHASED
                || !isPurchased(item) && state == WAITING;
    }

    private boolean isPurchased(ShoppingList item) {
        if (item == null || item.isDeleted || item.items == null) return true;

        for (ShoppingListItem shoppingListItem : item.items)
            if (shoppingListItem.isNeedToPurchase) return false;

        return true;
    }

    @Nullable
    private ArrayList<ShoppingList> getData(@Nullable Context context) {
        if (context == null) return null;
        if (data == null)
            data = (ArrayList<ShoppingList>) ControllerStorage.readObjectFromFile(context, ControllerStorage.CACHE_SHOPPING_LIST);
        if (data == null) data = new ArrayList<>();
        return data;
    }

    @Nullable
    public String getTitle(Context context, int position) {
        if (position < 0 || getData(context) == null || getData(context).size() <= position)
            return null;
        return getData(context).get(position).title;
    }

    @Nullable
    public Long getLastPurchaseDate(Context context, int position) {
        if (position < 0
                || getData(context) == null
                || getData(context).size() <= position
                || getData(context).get(position).purchasesDates == null
                || getData(context).get(position).purchasesDates.size() == 0)
            return null;

        return getData(context).get(position).purchasesDates.get(0);
    }

    //todo: on add purchase date sort and reverse list
/*
    Collections.sort(getData(context).get(position).purchasesDates);
    Collections.reverse(getData(context).get(position).purchasesDates);
*/

}
