package org.byters.bcshoppinglist.ui.fragment;


import android.os.Bundle;
import android.view.View;

import org.byters.bcshoppinglist.controllers.ControllerMarketList;
import org.byters.bcshoppinglist.controllers.utils.OnMarketListUpdateListener;
import org.byters.bcshoppinglist.ui.adapters.AdapterMarkets;

public class FragmentMarketList extends FragmentList
        implements OnMarketListUpdateListener {

    private AdapterMarkets adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AdapterMarkets();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvItems.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ControllerMarketList.getInstance().addListener(this);
        ControllerMarketList.getInstance().updateData();
    }

    @Override
    public void onPause() {
        super.onPause();
        ControllerMarketList.getInstance().removeListener(this);
    }

    @Override
    public void onMarketListUpdate() {
        adapter.updateData();
    }

}
