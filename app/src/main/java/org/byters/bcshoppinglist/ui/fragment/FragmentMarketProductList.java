package org.byters.bcshoppinglist.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerProductList;
import org.byters.bcshoppinglist.controllers.utils.OnProductListUpdateListener;
import org.byters.bcshoppinglist.ui.adapters.AdapterProductList;

public class FragmentMarketProductList extends FragmentList
        implements OnProductListUpdateListener
        , SearchView.OnQueryTextListener {

    private AdapterProductList adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AdapterProductList();
        ControllerProductList.getInstance().clearSearch();

        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvItems.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        ControllerProductList.getInstance().updateData();
        ControllerProductList.getInstance().addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ControllerProductList.getInstance().removeListener(this);
    }

    @Override
    public void onProductListUpdate() {
        adapter.updateData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_market_product_list, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        ((SearchView) searchMenuItem.getActionView()).setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
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
