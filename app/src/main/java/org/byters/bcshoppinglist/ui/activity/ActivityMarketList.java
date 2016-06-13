package org.byters.bcshoppinglist.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.ui.fragment.FragmentMarketList;
import org.byters.bcshoppinglist.ui.fragment.FragmentMarketMap;
import org.byters.bcshoppinglist.ui.fragment.FragmentMarketProductList;

public class ActivityMarketList extends ActivityBase {

    public static final String INTENT_TYPE = "intent_type";
    public static final int TYPE_LIST = 0;
    public static final int NO_VALUE = -1;

    public static void display(Context context) {
        context.startActivity(new Intent(context, ActivityMarketList.class));
    }

    public static void displayForList(Context context) {
        Intent intent = new Intent(context, ActivityMarketList.class);
        intent.putExtra(INTENT_TYPE, TYPE_LIST);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, new FragmentMarketList())
                .commit();

        initToolbar();
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

    public void navigateNext() {
        int type = getIntent().getIntExtra(INTENT_TYPE, NO_VALUE);
        if (type == NO_VALUE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContent, new FragmentMarketProductList())
                    .addToBackStack(null)
                    .commit();
        } else
            navigateMap(type);
    }

    public void navigateMap(int type) {
        Fragment fragment = FragmentMarketMap.newInstance(type);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, fragment)
                .addToBackStack(null)
                .commit();

    }

}
