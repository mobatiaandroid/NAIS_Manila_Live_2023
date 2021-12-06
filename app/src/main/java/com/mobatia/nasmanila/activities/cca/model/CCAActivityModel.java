package com.mobatia.nasmanila.activities.cca.model;

import java.io.Serializable;

/**
 * Created by mobatia on 14/08/18.
 */

public class CCAActivityModel implements Serializable{

    String itemId;
    String item_name;
    String description;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
