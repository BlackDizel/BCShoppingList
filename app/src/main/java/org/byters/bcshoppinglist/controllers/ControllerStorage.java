package org.byters.bcshoppinglist.controllers;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ControllerStorage {


    public static final String CACHE_SHOPPING_LIST = "cache_shopping_list";

    private static final String TAG = "controllerStorage";

    /*private static final String PREF_STORAGE = "cache_prefs";
    private static final String PREF_CAFE_ID = "pref_cafe_id";*/

    public synchronized static void writeObjectToFile(Context context, Object object, String filename) {

        ObjectOutputStream objectOut = null;
        if (object == null)
            context.deleteFile(filename);
        else {
            try {
                FileOutputStream fileOut = context.openFileOutput(filename, Activity.MODE_PRIVATE);
                objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(object);
                fileOut.getFD().sync();

            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            } finally {
                if (objectOut != null) {
                    try {
                        objectOut.close();
                    } catch (IOException e) {
                        Log.e(TAG, Log.getStackTraceString(e));
                    }
                }
            }
        }
    }

    public synchronized static Object readObjectFromFile(Context context, String filename) {

        ObjectInputStream objectIn = null;
        Object object = null;
        boolean needRemove = false;
        try {

            FileInputStream fileIn = context.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (IOException | ClassNotFoundException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            needRemove = true;
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        }

        if (needRemove) RemoveFile(context, filename);

        return object;
    }

    public static synchronized void RemoveFile(Context ctx, String filename) {
        try {
            ctx.getApplicationContext().deleteFile(filename);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    //region prefs
    /*private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_STORAGE, Context.MODE_PRIVATE);
    }

    public static int getPrefCafeId(Context context) {
        return getPreferences(context).getInt(PREF_CAFE_ID, ControllerCaffeeInfo.NO_VALUE);
    }

    public static void setPrefCafeId(Context context, int value) {
        getPreferences(context)
                .edit()
                .putInt(PREF_CAFE_ID, value)
                .apply();
    }*/

    //endregion

}
