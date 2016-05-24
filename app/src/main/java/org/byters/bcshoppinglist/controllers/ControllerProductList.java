package org.byters.bcshoppinglist.controllers;

import android.support.annotation.Nullable;

import org.byters.bcshoppinglist.api.ServiceShoppingList;
import org.byters.bcshoppinglist.controllers.utils.OnProductListUpdateListener;
import org.byters.bcshoppinglist.model.StoreCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerProductList
        implements Callback<ArrayList<StoreCategory>> {

    private static ControllerProductList instance;
    private ArrayList<StoreCategory> data;
    private ArrayList<OnProductListUpdateListener> listeners;

    public static ControllerProductList getInstance() {
        if (instance == null) instance = new ControllerProductList();
        return instance;
    }

    public void addListener(OnProductListUpdateListener listener) {
        if (listeners == null)
            listeners = new ArrayList<>();
        listeners.add(listener);
    }

    public void removeListener(OnProductListUpdateListener listener) {
        if (listeners == null) return;
        listeners.remove(listener);
    }


    public void updateData() {
        ServiceShoppingList.getApi().getMarketCategoryList(ControllerMarketList.getInstance().getSelectedMarketId()).enqueue(this);
    }

    @Override
    public void onResponse(Call<ArrayList<StoreCategory>> call, Response<ArrayList<StoreCategory>> response) {
        if (response.body() != null)
            data = response.body();
        if (listeners != null)
            for (OnProductListUpdateListener listener : listeners)
                listener.onProductListUpdate();
    }

    @Override
    public void onFailure(Call<ArrayList<StoreCategory>> call, Throwable t) {
        if (listeners != null)
            for (OnProductListUpdateListener listener : listeners)
                listener.onProductListUpdate();
    }


    @Nullable
    private StoreCategory getItem(int position) {
        if (data == null || position < 0 || data.size() <= position)
            return null;
        return data.get(position);
    }

    public int getItemsCount() {
        return data == null ? 0 : data.size();
    }
}
