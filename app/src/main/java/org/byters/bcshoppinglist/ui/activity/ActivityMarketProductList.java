package org.byters.bcshoppinglist.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerProductList;
import org.byters.bcshoppinglist.controllers.utils.OnProductListUpdateListener;
import org.byters.bcshoppinglist.ui.adapters.AdapterProductList;

public class ActivityMarketProductList extends ActivityBase
        implements OnProductListUpdateListener,
        SearchView.OnQueryTextListener {

    AdapterProductList adapter;

    public static void display(Context context) {
        context.startActivity(new Intent(context, ActivityMarketProductList.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_product_list);

        ControllerProductList.getInstance().clearSearch();
        initToolbar();
        initList();
    }

    private void initList() {
        RecyclerView rvProducts = (RecyclerView) findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterProductList();
        rvProducts.setAdapter(adapter);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ControllerProductList.getInstance().updateData();
        ControllerProductList.getInstance().addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ControllerProductList.getInstance().removeListener(this);
    }

    @Override
    public void onProductListUpdate() {
        adapter.updateData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_market_product_list, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        ((SearchView) searchMenuItem.getActionView()).setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ControllerProductList.getInstance().search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
