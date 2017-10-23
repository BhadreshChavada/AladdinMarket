package alladinmarket.com.alladinmarket.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.adapters.SearchShopkeeperAdaper;
import alladinmarket.com.alladinmarket.network.pojo.product.AllProducts;
import alladinmarket.com.alladinmarket.network.pojo.AllShops;
import alladinmarket.com.alladinmarket.network.pojo.ShopkeeperItem;

public class ShopKeepersActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SearchShopkeeperAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_product);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_search_product_list);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Gson gson = new Gson() ;
        gson.fromJson(getSharedPreferences("MYPrefs",MODE_PRIVATE).getString("shops_all",""), AllProducts
                .class) ;
        final ArrayList<ShopkeeperItem> shopsItems=   gson.fromJson(
                getSharedPreferences("MYPrefs",MODE_PRIVATE).getString("shops_all",""), AllShops.class).getMarket_items();
        // specify an adapter (see also next example)
        mAdapter = new SearchShopkeeperAdaper(this,shopsItems);
        mAdapter.setOnItemClickListener(new SearchShopkeeperAdaper.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent i = new Intent(ShopKeepersActivity.this,ShopkeeprDetail.class);
                i.putExtra("ShopekeeperPosition",position);
                startActivity(i);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
