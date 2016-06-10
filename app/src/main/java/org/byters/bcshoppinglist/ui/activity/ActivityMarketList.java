package org.byters.bcshoppinglist.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.ui.fragment.FragmentMarketList;

public class ActivityMarketList extends ActivityBase {

    public static void display(Context context) {
        context.startActivity(new Intent(context, ActivityMarketList.class));
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
        ActivityMarketProductList.display(this);
    }
}
