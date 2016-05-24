package org.byters.bcshoppinglist.model;

import android.support.annotation.Nullable;

public class Store {
    public int id;

    public String city;
    public String name;
    public String address;

    @Nullable
    public Float lat;
    @Nullable
    public Float lon;

    public String urlMap;
    public String urlLogo;
}
