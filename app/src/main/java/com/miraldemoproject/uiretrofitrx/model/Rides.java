package com.miraldemoproject.uiretrofitrx.model;

import com.google.gson.annotations.SerializedName;


public class Rides {

    @SerializedName("id")
    private int id;

    @SerializedName("ride")
    private String rideName;

    @SerializedName("picture")
    private String rideImage;

    @SerializedName("description")
    private String rideDescrption;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public String getRideImage() {
        return rideImage;
    }

    public void setRideImage(String rideImage) {
        this.rideImage = rideImage;
    }

    public String getRideDescrption() {
        return rideDescrption;
    }

    public void setRideDescrption(String rideDescrption) {
        this.rideDescrption = rideDescrption;
    }
}
