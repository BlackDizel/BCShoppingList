package org.byters.bcshoppinglist.ui.adapters;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerProductList;
import org.byters.bcshoppinglist.ui.activity.ActivityMarketMap;


public class AdapterProductList extends RecyclerView.Adapter<AdapterProductList.ViewHolder> {
    public AdapterProductList() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(getId(), parent, false));
    }

    @LayoutRes
    private int getId() {
        return R.layout.view_product_list_item;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return ControllerProductList.getInstance().getFilteredItemsCount();
    }

    public void updateData() {
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private int categoryId;
        private TextView tvTitle, tvCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            itemView.setOnClickListener(this);
        }

        public void setData(int position) {
            categoryId = ControllerProductList.getInstance().getFilteredCategoryId(position);

            String title = ControllerProductList.getInstance().getFilteredItemTitle(position);
            tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
            String categoryTitle = ControllerProductList.getInstance().getFilteredItemCategoryTitle(position);
            tvCategory.setText(TextUtils.isEmpty(categoryTitle) ? "" : categoryTitle);
        }

        @Override
        public void onClick(View v) {
            ControllerProductList.getInstance().setSelectedCategoryId(categoryId);
            ActivityMarketMap.display(v.getContext());
        }
    }
}
