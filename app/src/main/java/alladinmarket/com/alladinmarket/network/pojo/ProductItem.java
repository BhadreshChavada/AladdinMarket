
package alladinmarket.com.alladinmarket.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductItem {

    @SerializedName("product_ID")
    @Expose
    private String product_ID;
    @SerializedName("productname")
    @Expose
    private String productname;
    @SerializedName("subcategory")
    @Expose
    private String subcategory;

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

}
