package org.byters.bcshoppinglist.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.byters.bcshoppinglist.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceShoppingList {

    private static ApiShoppingList api;

    public static synchronized ApiShoppingList getApi() {
        if (api == null) {
            api = initializeApi();
        }
        return api;
    }


    private static ApiShoppingList initializeApi() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiShoppingList.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ApiShoppingList.class);
    }
}
