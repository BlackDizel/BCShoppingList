package org.byters.bcshoppinglist.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.byters.bcshoppinglist.controllers.ControllerList;
import org.byters.bcshoppinglist.ui.adapters.AdapterList;

public class FragmentPageList extends FragmentList {

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
}
