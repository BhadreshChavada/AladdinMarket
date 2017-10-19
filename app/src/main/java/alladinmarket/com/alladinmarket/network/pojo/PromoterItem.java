
package alladinmarket.com.alladinmarket.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoterItem {

    @SerializedName("traid")
    @Expose
    private String traid;
    @SerializedName("promoter_name")
    @Expose
    private String promoter_name;
    @SerializedName("district")
    @Expose
    private String district;

    public String getTraid() {
        return traid;
    }

    public void setTraid(String traid) {
        this.traid = traid;
    }

    public String getPromoter_name() {
        return promoter_name;
    }

    public void setPromoter_name(String promoter_name) {
        this.promoter_name = promoter_name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

}
