
package alladinmarket.com.alladinmarket.network.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subcat {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("sub_category")
    @Expose
    private List<Sub_category> sub_category = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Sub_category> getSub_category() {
        return sub_category;
    }

    public void setSub_category(List<Sub_category> sub_category) {
        this.sub_category = sub_category;
    }

}
