
package alladinmarket.com.alladinmarket.network.pojo.ProductDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gallery {

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("shop_mobile")
    @Expose
    private String shop_mobile;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getShop_mobile() {
        return shop_mobile;
    }

    public void setShop_mobile(String shop_mobile) {
        this.shop_mobile = shop_mobile;
    }

}
