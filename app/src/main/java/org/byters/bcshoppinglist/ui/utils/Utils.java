package org.byters.bcshoppinglist.ui.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final String DATE_FORMAT = "dd MMMM";

    @Nullable
    public static String getDateDisplay(@Nullable Context context, @Nullable Long date) {
        if (context == null || date == null) return null;

        Date d = new Date();
        d.setTime(date);
        return new SimpleDateFormat(DATE_FORMAT).format(d);
    }
}
