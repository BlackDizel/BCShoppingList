package org.byters.bcshoppinglist.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.controllers.ControllerMarketList;
import org.byters.bcshoppinglist.controllers.ControllerProductList;
import org.byters.bcshoppinglist.ui.view.ImageViewer;

import java.util.ArrayList;

public class ActivityMarketMap extends ActivityBase
        implements Target {

    public static void display(Context context) {
        context.startActivity(new Intent(context, ActivityMarketMap.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_map);

        initMap();
    }

    private void initMap() {

        String uri = ControllerMarketList.getInstance().getSelectedMarketMapUri();
        if (TextUtils.isEmpty(uri))
            return;

        Picasso.with(this).load(uri).into(this);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ImageViewer imageViewer = (ImageViewer) findViewById(R.id.ivMap);
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
}
