package org.byters.bcshoppinglist.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
        hideKeyboard();
        super.onPause();
        ControllerProductList.getInstance().removeListener(this);
    }

    private void hideKeyboard() {
        if (getActivity() == null) return;
        View view = this.getActivity().getCurrentFocus();
        if (view == null) return;
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        ((SearchView) searchMenuItem.getActionView()).setIconified(false);

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
