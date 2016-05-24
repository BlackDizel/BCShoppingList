package org.byters.bcshoppinglist.controllers;

import android.support.annotation.Nullable;

import org.byters.bcshoppinglist.api.ServiceShoppingList;
import org.byters.bcshoppinglist.controllers.utils.OnMarketListUpdateListener;
import org.byters.bcshoppinglist.model.Store;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerMarketList
        implements Callback<ArrayList<Store>> {

    public static final int NO_VALUE = -1;
    private static ControllerMarketList instance;
    private ArrayList<Store> data;
    private ArrayList<OnMarketListUpdateListener> listeners;
    private int selectedMarketId;

    private ControllerMarketList() {
        selectedMarketId = NO_VALUE;
    }

    public static ControllerMarketList getInstance() {
        if (instance == null) instance = new ControllerMarketList();
        return instance;
    }

    public void addListener(OnMarketListUpdateListener listener) {
        if (listeners == null)
            listeners = new ArrayList<>();
        listeners.add(listener);
    }

    public void removeListener(OnMarketListUpdateListener listener) {
        if (listeners == null) return;
        listeners.remove(listener);
    }


    public void updateData() {
        ServiceShoppingList.getApi().getMarketList().enqueue(this);
    }

    @Override
    public void onResponse(Call<ArrayList<Store>> call, Response<ArrayList<Store>> response) {
        if (response.body() != null)
            data = response.body();
        if (listeners != null)
            for (OnMarketListUpdateListener listener : listeners)
                listener.onMarketListUpdate();
    }

    @Override
    public void onFailure(Call<ArrayList<Store>> call, Throwable t) {
        if (listeners != null)
            for (OnMarketListUpdateListener listener : listeners)
                listener.onMarketListUpdate();
    }

    public int getMarketsCount() {
        return data == null ? 0 : data.size();
    }

    @Nullable
    public String getImageUri(int position) {
        Store item = getItem(position);
        if (item == null) return null;
        return item.urlLogo;
    }

    @Nullable
    private Store getItem(int position) {
        if (data == null || position < 0 || data.size() <= position)
            return null;
        return data.get(position);
    }

    @Nullable
    public String getTitle(int position) {
        Store item = getItem(position);
        if (item == null) return null;
        return item.name;
    }

    @Nullable
    public String getAddress(int position) {
        Store item = getItem(position);
        if (item == null) return null;
        return item.address;
    }

    public int getSelectedMarketId() {
        return selectedMarketId;
    }

    public void setSelectedMarketId(int id) {
        this.selectedMarketId = id;
    }

    public int getMarketId(int position) {
        Store item = getItem(position);
        if (item == null) return NO_VALUE;
        return item.id;
    }

    public String getSelectedMarketMapUri() {
        if (getSelectedMarketId() == NO_VALUE || data == null)
            return null;

        for (Store item : data)
            if (item.id == getSelectedMarketId())
                return item.urlMap;

        return null;
    }
}
