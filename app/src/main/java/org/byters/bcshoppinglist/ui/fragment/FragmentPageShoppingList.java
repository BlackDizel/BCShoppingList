package org.byters.bcshoppinglist.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerShoppingList;
import org.byters.bcshoppinglist.ui.activity.ActivityMarketList;
import org.byters.bcshoppinglist.ui.adapters.AdapterShoppingList;

public class FragmentPageShoppingList extends FragmentList {
    private AdapterShoppingList adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AdapterShoppingList();
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_shopping_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_show_in_market)
            ActivityMarketList.displayForList(getContext());
        return super.onOptionsItemSelected(item);
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
