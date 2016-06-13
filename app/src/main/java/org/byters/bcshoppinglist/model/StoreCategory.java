package org.byters.bcshoppinglist.model;

import android.graphics.Point;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class StoreCategory {
    public ArrayList<Product> products;
    public ArrayList<ArrayList<Coord>> coords;
    public int id;
    public String name;

    public boolean containsAny(ArrayList<ShoppingListItem> items) {
        if (items == null || products == null) return false;
        for (Product product : products)
            for (ShoppingListItem item : items)
                if (product.name.contains(item.title))
                    return true;
        return false;
    }

    @Nullable
    public ArrayList<ArrayList<Point>> getPolygon() {
        if (coords == null) return null;

        ArrayList<ArrayList<Point>> result = null;
        for (ArrayList<Coord> array : coords) {

            ArrayList<Point> points = null;

            for (Coord coord : array) {
                Point point = new Point(coord.x, coord.y);
                if (points == null) points = new ArrayList<>();
                points.add(point);
            }

            if (points == null) continue;

            if (result == null) result = new ArrayList<>();
            result.add(points);
        }
        return result;
    }
}
