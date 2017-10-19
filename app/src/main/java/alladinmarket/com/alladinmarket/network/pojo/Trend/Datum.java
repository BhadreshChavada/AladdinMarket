
package alladinmarket.com.alladinmarket.network.pojo.Trend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("product_id")
    @Expose
    private Integer product_id;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("product_image")
    @Expose
    private String product_image;
    @SerializedName("product_price")
    @Expose
    private String product_price;
    @SerializedName("product_regular_price")
    @Expose
    private String product_regular_price;
    @SerializedName("pricePerUnit")
    @Expose
    private String pricePerUnit;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_regular_price() {
        return product_regular_price;
    }

    public void setProduct_regular_price(String product_regular_price) {
        this.product_regular_price = product_regular_price;
    }

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

}
