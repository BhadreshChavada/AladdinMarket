
package alladinmarket.com.alladinmarket.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopkeeperItem  {

    @SerializedName("shopkeeper_ID")
    @Expose
    private String shopkeeper_ID;
    @SerializedName("shopname")
    @Expose
    private String shopname;
    @SerializedName("shopnumber")
    @Expose
    private String shopnumber;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("market")
    @Expose
    private String market;
    @SerializedName("productsshopkeepers_ID")
    @Expose
    private String productsshopkeepers_ID;
    @SerializedName("product_FK")
    @Expose
    private String product_FK;
    @SerializedName("shopkeeper_FK")
    @Expose
    private String shopkeeper_FK;

    public String getShopkeeper_ID() {
        return shopkeeper_ID;
    }

    public void setShopkeeper_ID(String shopkeeper_ID) {
        this.shopkeeper_ID = shopkeeper_ID;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopnumber() {
        return shopnumber;
    }

    public void setShopnumber(String shopnumber) {
        this.shopnumber = shopnumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getProductsshopkeepers_ID() {
        return productsshopkeepers_ID;
    }

    public void setProductsshopkeepers_ID(String productsshopkeepers_ID) {
        this.productsshopkeepers_ID = productsshopkeepers_ID;
    }

    public String getProduct_FK() {
        return product_FK;
    }

    public void setProduct_FK(String product_FK) {
        this.product_FK = product_FK;
    }

    public String getShopkeeper_FK() {
        return shopkeeper_FK;
    }

    public void setShopkeeper_FK(String shopkeeper_FK) {
        this.shopkeeper_FK = shopkeeper_FK;
    }

}
