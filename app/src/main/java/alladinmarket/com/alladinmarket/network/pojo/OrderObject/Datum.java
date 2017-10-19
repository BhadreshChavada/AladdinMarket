
package alladinmarket.com.alladinmarket.network.pojo.OrderObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("order_id")
    @Expose
    private String order_id;
    @SerializedName("order_total")
    @Expose
    private String order_total;
    @SerializedName("net_amount")
    @Expose
    private String net_amount;
    @SerializedName("order_status")
    @Expose
    private String order_status;
    @SerializedName("customer")
    @Expose
    private Integer customer;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public String getNet_amount() {
        return net_amount;
    }

    public void setNet_amount(String net_amount) {
        this.net_amount = net_amount;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

}
