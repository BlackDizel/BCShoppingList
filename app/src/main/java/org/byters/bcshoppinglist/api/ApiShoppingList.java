package org.byters.bcshoppinglist.api;

import org.byters.bcshoppinglist.model.Store;
import org.byters.bcshoppinglist.model.StoreCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiShoppingList {

    String BASE_URL = "http://vps-1097899.vpshome.pro:8000/api/";

    @GET("stores")
    Call<ArrayList<Store>> getMarketList();

    @GET("store/{id}/categories")
    Call<ArrayList<StoreCategory>> getMarketCategoryList(@Path("id") int id);

}
