package org.byters.bcshoppinglist.ui.adapters;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerMarketList;


public class AdapterMarkets extends RecyclerView.Adapter<AdapterMarkets.ViewHolder> {
    public AdapterMarkets() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(getId(), parent, false));
    }

    @LayoutRes
    private int getId() {
        return R.layout.view_market_list_item;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return ControllerMarketList.getInstance().getMarketsCount();
    }

    public void updateData() {
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivMarket;

        public ViewHolder(View itemView) {
            super(itemView);
            ivMarket = (ImageView) itemView.findViewById(R.id.ivMarket);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }

        public void setData(int position) {
            String imageUri = ControllerMarketList.getInstance().getImageUri(position);
            if (TextUtils.isEmpty(imageUri))
                ivMarket.setImageDrawable(null);
            else Picasso.with(ivMarket.getContext()).load(imageUri).into(ivMarket);

            String title = ControllerMarketList.getInstance().getTitle(position);
            tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);

        }

    }
}
