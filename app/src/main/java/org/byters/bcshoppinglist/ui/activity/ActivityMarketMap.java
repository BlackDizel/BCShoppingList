package org.byters.bcshoppinglist.ui.activity;

import android.content.Context;
import android.content.Intent;

public class ActivityMarketMap extends ActivityBase {

    public static void display(Context context) {
        context.startActivity(new Intent(context, ActivityMarketMap.class));
    }

}
