package com.miraldemoproject.productslanguage.model;

import com.google.gson.annotations.SerializedName;


public class ProductDetails {

    @SerializedName("product_ID")
    private int product_ID;

    @SerializedName("image_ID")
    private String image_ID;

    @SerializedName("product_name")
    private String product_name;

    @SerializedName("product_price")
    private String product_price;

    @SerializedName("product_description")
    private String product_description;

    @SerializedName("product_reviews")
    private String product_reviews;

    @SerializedName("product_stars")
    private float product_stars;

    public float getProduct_stars() {
        return product_stars;
    }

    public void setProduct_stars(float product_stars) {
        this.product_stars = product_stars;
    }

    public String getDescription() {
        return product_description;
    }

    public void setDescription(String description) {
        this.product_description = description;
    }

    public String getReviews() {
        return product_reviews;
    }

    public void setReviews(String reviews) {
        this.product_reviews = reviews;
    }


    public ProductDetails(int id, String image, String name, String price, String description, String reviews) {
        this.setProduct_ID(id);
        this.setImage_ID(image);
        this.setProduct_name(name);
        this.setProduct_price(price);
        this.setDescription(description);
        this.setReviews(reviews);

    }


    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }


    public String getImage_ID() {
        return image_ID;
    }

    public void setImage_ID(String image_ID) {
        this.image_ID = image_ID;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }
}
