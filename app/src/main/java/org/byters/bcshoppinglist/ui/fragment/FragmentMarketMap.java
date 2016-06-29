package org.byters.bcshoppinglist.ui.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerMarketList;
import org.byters.bcshoppinglist.controllers.ControllerProductList;
import org.byters.bcshoppinglist.controllers.ControllerShoppingList;
import org.byters.bcshoppinglist.controllers.utils.OnProductListUpdateListener;
import org.byters.bcshoppinglist.controllers.utils.OnTapListener;
import org.byters.bcshoppinglist.model.StoreCategory;
import org.byters.bcshoppinglist.ui.activity.ActivityMarketList;
import org.byters.bcshoppinglist.ui.adapters.AdapterShoppingListItemsCategory;
import org.byters.bcshoppinglist.ui.adapters.utils.AdapterUpdateListener;
import org.byters.bcshoppinglist.ui.view.ImageViewer;

import java.util.ArrayList;

public class FragmentMarketMap extends FragmentBase
        implements Target
        , OnProductListUpdateListener
        , OnTapListener
        , DialogInterface.OnDismissListener
        , AdapterUpdateListener {

    AdapterShoppingListItemsCategory adapter;
    BottomSheetDialog dialog;

    public static Fragment newInstance(int type) {
        Fragment fragment = new FragmentMarketMap();
        Bundle bundle = new Bundle();
        bundle.putInt(ActivityMarketList.INTENT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new AdapterShoppingListItemsCategory();
        return inflater.inflate(R.layout.fragment_market_map, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ImageViewer imageViewer = (ImageViewer) getView().findViewById(R.id.ivMap);
        imageViewer.addTapListener(this);
        ControllerProductList.getInstance().addListener(this);
        adapter.addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ImageViewer imageViewer = (ImageViewer) getView().findViewById(R.id.ivMap);
        imageViewer.removeTapListener(this);
        ControllerProductList.getInstance().removeListener(this);
        adapter.removeListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initMap();
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ImageViewer imageViewer = (ImageViewer) getView().findViewById(R.id.ivMap);
        imageViewer.loadImage(bitmap);

        if (ControllerProductList.getInstance().isEmpty())
            ControllerProductList.getInstance().updateData();
        setImageData();
    }

    private void setImageData() {
        ImageViewer imageViewer = (ImageViewer) getView().findViewById(R.id.ivMap);
        int type = getArguments().getInt(ActivityMarketList.INTENT_TYPE, ActivityMarketList.NO_VALUE);
        if (type == ActivityMarketList.NO_VALUE) {
            ArrayList<ArrayList<Point>> points = ControllerProductList.getInstance().getSelectedItemPolygon();
            if (points == null) return;
            for (ArrayList<Point> item : points)
                imageViewer.addPolygon(item);
        } else if (type == ActivityMarketList.TYPE_LIST) {
            ArrayList<ArrayList<Point>> points = ControllerProductList.getInstance().getCurrentShoppingListCategoryList();
            if (points == null) return;
            for (ArrayList<Point> item : points)
                imageViewer.addPolygon(item);
        }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        if (getContext() != null && isAdded() && getActivity() != null)
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.error_map_loading)
                    .setPositiveButton(R.string.ok, null)
                    .show();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    private void initMap() {

        String uri = ControllerMarketList.getInstance().getSelectedMarketMapUri();
        if (TextUtils.isEmpty(uri))
            return;

        Picasso.with(getContext())
                .load(uri)
                .into(this);
    }

    @Override
    public void onProductListUpdate() {
        setImageData();
    }

    @Override
    public void onTap(int x, int y) {

        StoreCategory category = ControllerProductList.getInstance().getCategory(x, y);
        if (category == null) return;

        ControllerShoppingList.getInstance().setItemsInCategory(category);
        if (ControllerShoppingList.getInstance().getCountInCategory() == 0)
            return;

        dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.view_dialog_shopping_list_items);

        ((TextView) dialog.findViewById(R.id.tvTitle)).setText(category.name);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.rvItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        dialog.setOnDismissListener(this);
        dialog.show();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ControllerShoppingList.getInstance().clearCategory();
    }

    @Override
    public void onAdapterUpdate() {
        if (ControllerShoppingList.getInstance().getCountInCategory() == 0 && dialog != null && dialog.isShowing())
            dialog.dismiss();

    }
}
