package alladinmarket.com.alladinmarket.network.pojo;

import java.util.ArrayList;

/**
 * Created by nmn on 29/5/17.
 */

public class AllCategories {

    public ArrayList<CategoryItem> getMarket_items() {
        return category_items;
    }

    public void setMarket_items(ArrayList<CategoryItem> category_items) {
        this.category_items = category_items;
    }

    ArrayList<CategoryItem> category_items ;
}
