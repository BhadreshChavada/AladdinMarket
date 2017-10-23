
package alladinmarket.com.alladinmarket.network.pojo.Promoter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Promoter {

    @SerializedName("promoter_id")
    @Expose
    private String promoterId;
    @SerializedName("promoter_name")
    @Expose
    private String promoterName;
    @SerializedName("market_name")
    @Expose
    private String marketName;
    @SerializedName("pincode")
    @Expose
    private String pincode;

    public String getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(String promoterId) {
        this.promoterId = promoterId;
    }

    public String getPromoterName() {
        return promoterName;
    }

    public void setPromoterName(String promoterName) {
        this.promoterName = promoterName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }




}
