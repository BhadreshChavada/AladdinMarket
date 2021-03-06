package alladinmarket.com.alladinmarket.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.activities.ProductDetailActivity;
import alladinmarket.com.alladinmarket.adapters.SearchShopkeeperAdaper;
import alladinmarket.com.alladinmarket.network.pojo.AllShops;
import alladinmarket.com.alladinmarket.network.pojo.ShopkeeperItem;
import alladinmarket.com.alladinmarket.network.pojo.product.AllProducts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiService;


public class SearchShopsActivity extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private SearchShopkeeperAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public SearchShopsActivity() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_search_product);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_search_product_list);


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        String ProductId = getIntent().getStringExtra("ProductId");
        getShopKeepers(ProductId);
    }

    public void getShopKeepers(String ProductId) {

        Call<ArrayList<ShopkeeperItem>> getproducts = apiService.listshops(ProductId);
        getproducts.enqueue(new Callback<ArrayList<ShopkeeperItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ShopkeeperItem>> call, Response<ArrayList<ShopkeeperItem>> response) {

                try {
                    Log.v("resonseShops", response.code() + "length" + response.body().size());
                    AllShops allshops = new AllShops();
                    Gson gson = new Gson();
                    allshops.setShopkeeper_items(response.body());
                    String allShopsHere = gson.toJson(allshops);
                    getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().putString("shops_all", allShopsHere).apply();
                    setupRecycleview();
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Try Again...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ShopkeeperItem>> call, Throwable t) {

            }
        });

    }

    void setupRecycleview() {
        try {
            Gson gson = new Gson();
            gson.fromJson(getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("shops_all", ""), AllProducts
                    .class);
            final ArrayList<ShopkeeperItem> shopsItems = gson.fromJson(
                    getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("shops_all", ""), AllShops.class).getMarket_items();
            // specify an adapter (see also next example)
            mAdapter = new SearchShopkeeperAdaper(this, shopsItems);
            mAdapter.setOnItemClickListener(new SearchShopkeeperAdaper.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {


                    Log.d("ShopkeeperID", shopsItems.get(position).getShopkeeper_ID());


                    Intent i = new Intent(SearchShopsActivity.this, ProductDetailActivity.class);
                    i.putExtra("ProductID", getIntent().getStringExtra("ProductId"));
                    i.putExtra("ShopkeeperID", shopsItems.get(position).getShopkeeper_ID());
                    startActivity(i);
                }
            });
            mRecyclerView.setAdapter(mAdapter);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "Try Again...", Toast.LENGTH_SHORT).show();
        }
    }
}
