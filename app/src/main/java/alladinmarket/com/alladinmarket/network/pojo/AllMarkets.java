package alladinmarket.com.alladinmarket.network.pojo;

import java.util.ArrayList;

/**
 * Created by nmn on 29/5/17.
 */

public class AllMarkets {

    public ArrayList<Market_item> getMarket_items() {
        return market_items;
    }

    public void setMarket_items(ArrayList<Market_item> market_items) {
        this.market_items = market_items;
    }

    ArrayList<Market_item> market_items ;
}
