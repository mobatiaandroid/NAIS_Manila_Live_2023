package com.mobatia.nasmanila.activities.cca.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mobatia on 14/08/18.
 */

public class CCACategoryModel implements Serializable {

    /*"id": 3,
        "name": "Academic",
        "activities": [*/
    String id;
    String name;
    ArrayList<CCAActivityModel>mCCAActivityModelList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CCAActivityModel> getmCCAActivityModelList() {
        return mCCAActivityModelList;
    }

    public void setmCCAActivityModelList(ArrayList<CCAActivityModel> mCCAActivityModelList) {
        this.mCCAActivityModelList = mCCAActivityModelList;
    }
}
