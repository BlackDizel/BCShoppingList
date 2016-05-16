package org.byters.bcshoppinglist.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingList implements Serializable {
    public String title;
    public boolean isDeleted;
    public ArrayList<ShoppingListItem> items;
    public ArrayList<Long> purchasesDates;
}
