package com.somecompanydomain.producthuntapplicationtest;

public class ProductCard {

    private String thumbnail;
    private String productName;
    private String productDescription;
    private String upvotes;



//    public ProductCard(String thumbnail, String productName, String productDescription, int upvotes) {
//        this.thumbnail = thumbnail;
//        this.productName = productName;
//        this.productDescription = productDescription;
//        this.upvotes = upvotes;
//    }

    public ProductCard(String thumbnail, String productName, String productDescription, String upvotes) {
        this.thumbnail = thumbnail;
        this.productName = productName;
        this.productDescription = productDescription;
        this.upvotes = upvotes;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(String upvotes) {
        this.upvotes = upvotes;
    }
}
