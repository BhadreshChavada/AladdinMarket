package alladinmarket.com.alladinmarket.network.pojo;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.network.pojo.Trend.*;
import alladinmarket.com.alladinmarket.network.pojo.Trend.Datum;

/**
 * Created by nmn on 29/5/17.
 */

public class AllNewTrends {

    public ArrayList<alladinmarket.com.alladinmarket.network.pojo.Trend.Datum> getNewTrends_items() {
        return newTrends_items;
    }

    public void setNewTrends_items(ArrayList<Datum> newTrends_items) {
        this.newTrends_items = newTrends_items;
    }

    ArrayList<Datum> newTrends_items ;
}
