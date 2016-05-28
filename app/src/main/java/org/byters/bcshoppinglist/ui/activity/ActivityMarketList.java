package org.byters.bcshoppinglist.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerMarketList;
import org.byters.bcshoppinglist.controllers.utils.OnMarketListUpdateListener;
import org.byters.bcshoppinglist.ui.adapters.AdapterMarkets;

public class ActivityMarketList extends ActivityBase
        implements OnMarketListUpdateListener {

    private AdapterMarkets adapter;

    public static void display(Context context) {
        context.startActivity(new Intent(context, ActivityMarketList.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);
        initToolbar();
        initList();
    }

    private void initList() {
        RecyclerView rvMarkets = (RecyclerView) findViewById(R.id.rvMarkets);
        rvMarkets.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterMarkets();
        rvMarkets.setAdapter(adapter);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ControllerMarketList.getInstance().addListener(this);
        ControllerMarketList.getInstance().updateData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ControllerMarketList.getInstance().removeListener(this);
    }

    @Override
    public void onMarketListUpdate() {
        adapter.updateData();
    }
}
