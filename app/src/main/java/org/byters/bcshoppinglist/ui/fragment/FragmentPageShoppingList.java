package org.byters.bcshoppinglist.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.byters.bcshoppinglist.controllers.ControllerShoppingList;
import org.byters.bcshoppinglist.ui.adapters.AdapterShoppingList;

public class FragmentPageShoppingList extends FragmentList {
    private AdapterShoppingList adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AdapterShoppingList();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvItems.setAdapter(adapter);
    }

    public void addItem(Context context, String title) {
        ControllerShoppingList.getInstance().addItem(context, title);
        adapter.addItem();
    }
}
