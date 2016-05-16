package org.byters.bcshoppinglist.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byters.bcshoppinglist.R;

public class FragmentList extends FragmentBase {
    protected RecyclerView rvItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        rvItems = (RecyclerView) v.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}
