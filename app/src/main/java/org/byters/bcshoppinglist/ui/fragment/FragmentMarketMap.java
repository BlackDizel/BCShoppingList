package org.byters.bcshoppinglist.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerMarketList;
import org.byters.bcshoppinglist.controllers.ControllerProductList;
import org.byters.bcshoppinglist.ui.view.ImageViewer;

import java.util.ArrayList;

public class FragmentMarketMap extends FragmentBase
        implements Target {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initMap();
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ImageViewer imageViewer = (ImageViewer) getView().findViewById(R.id.ivMap);
        imageViewer.loadImage(bitmap);

        ArrayList<ArrayList<Point>> points = ControllerProductList.getInstance().getSelectedItemPolygon();
        if (points == null) return;
        for (ArrayList<Point> item : points)
            imageViewer.addPolygon(item);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    private void initMap() {

        String uri = ControllerMarketList.getInstance().getSelectedMarketMapUri();
        if (TextUtils.isEmpty(uri))
            return;

        Picasso.with(getContext()).load(uri).into(this);
    }
}
