package org.byters.bcshoppinglist.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingListItem implements Serializable {
    public boolean isNeedToPurchase;
    public String title;
    public ArrayList<Long> purchasesDates;
}
