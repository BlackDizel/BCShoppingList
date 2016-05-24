package org.byters.bcshoppinglist.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.ui.adapters.AdapterProductList;

public class ActivityMarketProductList extends ActivityBase {

    AdapterProductList adapter;

    public static void display(Context context) {
        context.startActivity(new Intent(context, ActivityMarketProductList.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_product_list);
        RecyclerView rvProducts = (RecyclerView) findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterProductList();
        rvProducts.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
