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
    }

    @Override
    public void onClick(View v) {
        ActivityShoppingList.display(this);
    }
}
