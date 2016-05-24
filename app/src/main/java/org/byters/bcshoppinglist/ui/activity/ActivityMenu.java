package org.byters.bcshoppinglist.ui.activity;

import android.os.Bundle;
import android.view.View;

import org.byters.bcshoppinglist.R;

public class ActivityMenu extends ActivityBase
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.tvShoppingList).setOnClickListener(this);
        findViewById(R.id.tvSearch).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSearch:
                ActivityMarketList.display(this);
                break;
            case R.id.tvShoppingList:
                ActivityShoppingList.display(this);
                break;
        }

    }
}
