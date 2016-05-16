package org.byters.bcshoppinglist.ui.adapters;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerList;


public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {
    private static final int TYPE_UNKNOWN = -1;
    private static final int TYPE_WAITING = 0;
    private static final int TYPE_DELIMETER = 1;
    private static final int TYPE_PURCHASED = 2;

    public AdapterList() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_WAITING:
                return new ViewHolderWaiting(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item_waiting, parent, false));
            case TYPE_DELIMETER:
                return new ViewHolderDelimeter(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item_delimeter, parent, false));
            case TYPE_PURCHASED:
                return new ViewHolderPurchased(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item_purchased, parent, false));
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_list_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_WAITING;
            case 1:
                return TYPE_DELIMETER;
            case 2:
                return TYPE_PURCHASED;
            default:
                return TYPE_UNKNOWN;
        }
    }

    public void addItem() {
        notifyDataSetChanged();
        //todo: change with notifyItemAdded;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            itemView.setVisibility(
                    ControllerList.getInstance().isPurchasedExist(itemView.getContext())
                            ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public class ViewHolderDelimeter extends ViewHolder {
        public ViewHolderDelimeter(View itemView) {
            super(itemView);
        }
    }


    public class ViewHolderList extends ViewHolder {

        protected RecyclerView rvItems;
        protected AdapterListState adapterLists;

        public ViewHolderList(View itemView) {
            super(itemView);
            rvItems = (RecyclerView) itemView.findViewById(R.id.rvItems);
            rvItems.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }


        @Override
        public void setData(int position) {
            if (adapterLists != null)
                adapterLists.updateItems();
        }
    }

    public class ViewHolderWaiting extends ViewHolderList {

        public ViewHolderWaiting(View itemView) {
            super(itemView);
            adapterLists = new AdapterListState(itemView.getContext(), ControllerList.WAITING);
            rvItems.setAdapter(adapterLists);
        }

    }

    public class ViewHolderPurchased extends ViewHolderList {
        public ViewHolderPurchased(View itemView) {
            super(itemView);
            adapterLists = new AdapterListState(itemView.getContext(), ControllerList.PURCHASED);
            rvItems.setAdapter(adapterLists);
        }
    }

}
