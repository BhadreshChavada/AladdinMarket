package alladinmarket.com.alladinmarket.network.pojo.product;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nmn on 29/5/17.
 */

public class AllProducts {


    public ArrayList<Datum> getMarket_items() {
        return products_items;
    }

    public void setProduct_items(ArrayList<Datum> products_items) {
        this.products_items = products_items;
    }

    @SerializedName("products_items")
    ArrayList<Datum> products_items ;
}
