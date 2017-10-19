package alladinmarket.com.alladinmarket.network.pojo;

import java.util.ArrayList;

/**
 * Created by nmn on 29/5/17.
 */

public class AllShops {

    public ArrayList<ShopkeeperItem> getMarket_items() {
        return shopkeeper_items;
    }

    public void setShopkeeper_items(ArrayList<ShopkeeperItem> shopkeeper_items) {
        this.shopkeeper_items = shopkeeper_items;
    }

    ArrayList<ShopkeeperItem> shopkeeper_items ;
}
