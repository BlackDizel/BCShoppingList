package org.byters.bcshoppinglist.ui.adapters;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerList;
import org.byters.bcshoppinglist.model.ShoppingList;
import org.byters.bcshoppinglist.ui.utils.Utils;


public class AdapterListState extends RecyclerView.Adapter<AdapterListState.ViewHolder> {

    private int state;
    @Nullable
    private Context context;

    public AdapterListState(@Nullable Context context, int state) {
        this.state = ControllerList.UNKNOWN;
        if (state == ControllerList.WAITING || state == ControllerList.PURCHASED)
            this.state = state;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(getId(), parent, false));
    }

    @LayoutRes
    private int getId() {
        return R.layout.view_list_item;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return ControllerList.getInstance().getCount(context, state);
    }

    public void updateItems() {
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle, tvPurchaseDate;
        private ImageView ivRemove;
        @Nullable
        private ShoppingList item;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPurchaseDate = (TextView) itemView.findViewById(R.id.tvPurchaseDate);
            ivRemove = (ImageView) itemView.findViewById(R.id.ivRemove);

            ivRemove.setOnClickListener(this);
            if (state == ControllerList.WAITING)
                itemView.setOnClickListener(this);
            else itemView.setEnabled(false);
        }

        public void setData(int position) {

            item = ControllerList.getInstance().getItem(itemView.getContext(), position);
            String title = ControllerList.getInstance().getTitle(itemView.getContext(), position);
            tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);

            if (state == ControllerList.PURCHASED) {

                String textDate = Utils.getDateDisplay(itemView.getContext()
                        , ControllerList.getInstance().getLastPurchaseDate(itemView.getContext(), position));
                tvPurchaseDate.setText(TextUtils.isEmpty(textDate) ? "" : textDate);
            } else if (state == ControllerList.WAITING) {
                tvPurchaseDate.setVisibility(View.GONE);

            }
        }

        @Override
        public void onClick(View v) {
            if (v == ivRemove) {
                ControllerList.getInstance().removeItem(v.getContext(), item);
                notifyDataSetChanged();

                //todo:replace with notifyItemRemoved

            } else if (v == itemView) {
                //todo:navigate item
            }
        }
    }
}
