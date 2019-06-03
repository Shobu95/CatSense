package com.shobu.catsense.model;

import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class Store extends ExpandableGroup<StoreBranch>
{
    private String storeID;
    private String storeName;
    private List<StoreBranch> storeBranches;


    public Store(String storeID, String storeName, List<StoreBranch> storeBranches) {
        super(storeName, storeBranches);
        this.storeID = storeID;
        this.storeName = storeName;
        this.storeBranches = storeBranches;
    }

    protected Store(Parcel in) {
        super(in);
    }


    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<StoreBranch> getStoreBranches() {
        return storeBranches;
    }

    public void setStoreBranches(List<StoreBranch> storeBranches) {
        this.storeBranches = storeBranches;
    }
}
