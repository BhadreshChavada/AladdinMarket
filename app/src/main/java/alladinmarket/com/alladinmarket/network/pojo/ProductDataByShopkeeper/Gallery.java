package alladinmarket.com.alladinmarket.network.pojo.ProductDataByShopkeeper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AMD21 on 23/10/17.
 */

public class Gallery {

    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("shop_mobile")
    @Expose
    private String shopMobile;

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getShopMobile() {
        return shopMobile;
    }

    public void setShopMobile(String shopMobile) {
        this.shopMobile = shopMobile;
    }




}
