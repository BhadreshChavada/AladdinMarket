
package alladinmarket.com.alladinmarket.network.pojo.Promoter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promoter {

    @SerializedName("Promoter")
    @Expose
    private List<List<String>> promoter = null;

    public List<List<String>> getPromoter() {
        return promoter;
    }

    public void setPromoter(List<List<String>> promoter) {
        this.promoter = promoter;
    }

}
