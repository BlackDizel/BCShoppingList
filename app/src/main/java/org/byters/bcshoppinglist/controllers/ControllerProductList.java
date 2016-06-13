package org.byters.bcshoppinglist.controllers;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.byters.bcshoppinglist.api.ServiceShoppingList;
import org.byters.bcshoppinglist.controllers.utils.OnProductListUpdateListener;
import org.byters.bcshoppinglist.model.Coord;
import org.byters.bcshoppinglist.model.Product;
import org.byters.bcshoppinglist.model.ProductFiltered;
import org.byters.bcshoppinglist.model.StoreCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerProductList
        implements Callback<ArrayList<StoreCategory>> {

    private static final int NO_VALUE = -1;
    private static ControllerProductList instance;
    private ArrayList<StoreCategory> data;
    private ArrayList<ProductFiltered> dataFiltered;
    private ArrayList<OnProductListUpdateListener> listeners;
    private String search;
    private int selectedCategoryId;

    private ControllerProductList() {
        selectedCategoryId = NO_VALUE;
    }

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
        notifyListeners();
    }

    private void notifyListeners() {
        if (listeners != null)
            for (OnProductListUpdateListener listener : listeners)
                listener.onProductListUpdate();
    }

    @Override
    public void onFailure(Call<ArrayList<StoreCategory>> call, Throwable t) {
        notifyListeners();
    }

    @Nullable
    private StoreCategory getItem(int position) {
        if (data == null || position < 0 || data.size() <= position)
            return null;
        return data.get(position);
    }

    public int getFilteredItemsCount() {
        if (data == null || TextUtils.isEmpty(search)) return 0;

        ArrayList<ProductFiltered> dataFiltered = getDataFiltered();
        return dataFiltered.size();
    }

    private ArrayList<ProductFiltered> getDataFiltered() {
        if (dataFiltered != null) return dataFiltered;

        dataFiltered = new ArrayList<>();
        for (StoreCategory category : data) {

            if (category.products == null) continue;

            for (Product product : category.products) {
                if (product.name.contains(search)) {
                    ProductFiltered item = new ProductFiltered();
                    item.categoryId = category.id;
                    item.product = product;
                    dataFiltered.add(item);
                }
            }
        }
        return dataFiltered;
    }

    public void search(String query) {
        this.search = query;
        dataFiltered = null;
        notifyListeners();
    }

    public void clearSearch() {
        this.search = null;
        dataFiltered = null;
        notifyListeners();
    }

    @Nullable
    public String getFilteredItemTitle(int position) {
        if (dataFiltered == null) return null;
        ProductFiltered item = getFilteredItem(position);
        if (item == null) return null;
        return item.product.name;
    }

    @Nullable
    public int getFilteredCategoryId(int position) {
        if (dataFiltered == null) return NO_VALUE;
        ProductFiltered item = getFilteredItem(position);
        if (item == null) return NO_VALUE;
        return item.categoryId;
    }

    @Nullable
    private ProductFiltered getFilteredItem(int position) {
        if (dataFiltered == null || position < 0 || dataFiltered.size() <= position) return null;
        return dataFiltered.get(position);
    }

    @Nullable
    public String getFilteredItemCategoryTitle(int position) {
        if (dataFiltered == null || data == null) return null;
        ProductFiltered item = getFilteredItem(position);
        if (item == null) return null;

        for (StoreCategory category : data)
            if (category.id == item.categoryId)
                return category.name;

        return null;
    }

    @Nullable
    public ArrayList<ArrayList<Point>> getSelectedItemPolygon() {
        if (selectedCategoryId == NO_VALUE || data == null) return null;

        StoreCategory category = null;
        for (StoreCategory item : data)
            if (item.id == selectedCategoryId) {
                category = item;
                break;
            }

        if (category == null || category.coords == null || category.coords.size() == 0) return null;

        ArrayList<ArrayList<Point>> result = null;

        for (ArrayList<Coord> array : category.coords) {

            ArrayList<Point> points = null;

            for (Coord coord : array) {
                Point point = new Point(coord.x, coord.y);
                if (points == null) points = new ArrayList<>();
                points.add(point);
            }

            if (points == null) continue;

            if (result == null) result = new ArrayList<>();
            result.add(points);
        }
        return result;
    }

    public void setSelectedCategoryId(int id) {
        selectedCategoryId = id;
    }

    public ArrayList<ArrayList<Point>> getCurrentShoppingListCategoryList() {

        if (ControllerShoppingList.getInstance().getItems() == null || data == null) return null;

        ArrayList<StoreCategory> categories = new ArrayList<>();
        for (StoreCategory category : data)
            if (category.containsAny(ControllerShoppingList.getInstance().getItems()))
                categories.add(category);

        if (categories.size() == 0) return null;

        ArrayList<ArrayList<Point>> result = null;
        for (StoreCategory category : categories) {
            ArrayList<ArrayList<Point>> polygon = category.getPolygon();
            if (polygon == null) continue;
            if (result == null) result = new ArrayList<>();
            result.addAll(polygon);
        }

        return result;
    }

    public boolean isEmpty() {
        return data == null;
    }
}
