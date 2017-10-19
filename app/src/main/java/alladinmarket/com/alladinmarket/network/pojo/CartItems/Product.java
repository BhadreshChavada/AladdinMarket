
package alladinmarket.com.alladinmarket.network.pojo.CartItems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_id")
    @Expose
    private Integer product_id;
    @SerializedName("product_quantity")
    @Expose
    private Integer product_quantity;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("product_image")
    @Expose
    private String product_image;
    @SerializedName("_regular_price")
    @Expose
    private String _regular_price;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(Integer product_quantity) {
        this.product_quantity = product_quantity;
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

    public String get_regular_price() {
        return _regular_price;
    }

    public void set_regular_price(String _regular_price) {
        this._regular_price = _regular_price;
    }

}
