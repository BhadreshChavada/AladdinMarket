package alladinmarket.com.alladinmarket.network.pojo.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by good on 10/19/2017.
 */

public class ProductsItem {

    @SerializedName("pricePerUnit")
    @Expose
    private String pricePerUnit;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_regular_price")
    @Expose
    private String productRegularPrice;

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductRegularPrice() {
        return productRegularPrice;
    }

    public void setProductRegularPrice(String productRegularPrice) {
        this.productRegularPrice = productRegularPrice;
    }

}
