package alladinmarket.com.alladinmarket.network.pojo.ProductDataByShopkeeper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AMD21 on 23/10/17.
 */

public class Data {

    @SerializedName("prod_id")
    @Expose
    private Integer prodId;
    @SerializedName("prod_content")
    @Expose
    private String prodContent;
    @SerializedName("prod_title")
    @Expose
    private String prodTitle;
    @SerializedName("prod_excerpt")
    @Expose
    private String prodExcerpt;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("seller_name")
    @Expose
    private String sellerName;
    @SerializedName("prod_image")
    @Expose
    private String prodImage;
    @SerializedName("prod_price")
    @Expose
    private String prodPrice;
    @SerializedName("regular_price")
    @Expose
    private String regularPrice;
    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getProdContent() {
        return prodContent;
    }

    public void setProdContent(String prodContent) {
        this.prodContent = prodContent;
    }

    public String getProdTitle() {
        return prodTitle;
    }

    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    public String getProdExcerpt() {
        return prodExcerpt;
    }

    public void setProdExcerpt(String prodExcerpt) {
        this.prodExcerpt = prodExcerpt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }
}
