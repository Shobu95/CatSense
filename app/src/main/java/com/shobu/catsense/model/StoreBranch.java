package com.shobu.catsense.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreBranch implements Parcelable {
    private String storeBranchID;
    private String storeId;
    private String branchName;
    private String cityID;
    private String cityName;

    public StoreBranch() { }

    protected StoreBranch(Parcel in) {
        storeBranchID = in.readString();
        storeId = in.readString();
        branchName = in.readString();
        cityID = in.readString();
        cityName = in.readString();
    }

    public static final Creator<StoreBranch> CREATOR = new Creator<StoreBranch>() {
        @Override
        public StoreBranch createFromParcel(Parcel in) {
            return new StoreBranch(in);
        }

        @Override
        public StoreBranch[] newArray(int size) {
            return new StoreBranch[size];
        }
    };

    public String getStoreBranchID() {
        return storeBranchID;
    }

    public void setStoreBranchID(String storeBranchID) {
        this.storeBranchID = storeBranchID;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(storeBranchID);
        dest.writeString(storeId);
        dest.writeString(branchName);
        dest.writeString(cityID);
        dest.writeString(cityName);
    }
}
