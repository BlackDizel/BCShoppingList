package org.byters.bcshoppinglist.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.byters.bcshoppinglist.controllers.ControllerList;
import org.byters.bcshoppinglist.controllers.utils.UpdateListener;
import org.byters.bcshoppinglist.model.ShoppingList;
import org.byters.bcshoppinglist.ui.adapters.AdapterList;

public class FragmentPageList extends FragmentList
implements UpdateListener{

    private AdapterList adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AdapterList();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvItems.setAdapter(adapter);
    }

    public void addItem(Context context, String title) {
        ControllerList.getInstance().addItem(context, title);
        adapter.addItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        ControllerList.getInstance().addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ControllerList.getInstance().removeListener(this);

    }

    @Override
    public void onListRemove(ShoppingList item) {
        adapter.updateItems();
    }
}
