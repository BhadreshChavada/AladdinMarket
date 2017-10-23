package alladinmarket.com.alladinmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.adapters.SearchProductAdaper;
import alladinmarket.com.alladinmarket.fragments.SearchShopsActivity;
import alladinmarket.com.alladinmarket.network.pojo.AllShops;
import alladinmarket.com.alladinmarket.network.pojo.ProductDetails.ProductDetailItem;
import alladinmarket.com.alladinmarket.network.pojo.ProductItem;
import alladinmarket.com.alladinmarket.network.pojo.ShopkeeperItem;
import alladinmarket.com.alladinmarket.network.pojo.product.AllProducts;
import alladinmarket.com.alladinmarket.network.pojo.product.Datum;
import alladinmarket.com.alladinmarket.utils.MyApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiService;
import static alladinmarket.com.alladinmarket.services.MyService.apiServiceActual;

public class SeacrhProductActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private SearchProductAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar mToolbar;
    private ArrayList<ProductItem> productItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seacrh_product);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_product_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Products");


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter (see also next example)


        Gson gson = new Gson();
        gson.fromJson(getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("markets_all", ""), AllProducts
                .class);

        Log.d("RESPONSE",getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("products_all", ""));
        ArrayList<Datum> productItems = gson.fromJson(
                getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("products_all", ""), AllProducts.class).getMarket_items();

        Log.v("checkItems", productItems.size() + "size");
        mAdapter = new SearchProductAdaper(getProducts());

        getShopKeepers();


        mAdapter.setOnItemClickListener(new SearchProductAdaper.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {


                getProductDetail(getIntent().getExtras().getString("SelectedSubCategoryID"),position);


            }
        });

        mRecyclerView.setAdapter(mAdapter);
        Log.v("finalSize", productItems.size() + "");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Datum> getProducts() {
           /* Log.v("checkIDafterIN",getIntent().getIntExtra("SelectedSubCategoryID",8)+"");
        Call<ArrayList<ProductItem>> getproducts = apiService.listproducts(getIntent().getIntExtra("SelectedSubCategoryID",8));
        getproducts.enqueue(new Callback<ArrayList<ProductItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductItem>> call, Response<ArrayList<ProductItem>> response) {
                Log.v("resonse",response.code()+"length"+response.body().size()) ;
              productItems = response.body() ;

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ProductItem>> call, Throwable t) {

            }
        }) ;
            return productItems;*/

        Gson gson = new Gson();
        AllProducts allProducts = new AllProducts();
        String val = getSharedPreferences("MYPrefs", MODE_PRIVATE).
                getString("products_all", "");
        allProducts.setProduct_items(gson.fromJson(val, AllProducts.class).getMarket_items());

        return allProducts.getMarket_items();
    }


    public void getShopKeepers() {

        Call<ArrayList<ShopkeeperItem>> getproducts = apiService.listshops(0);
        getproducts.enqueue(new Callback<ArrayList<ShopkeeperItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ShopkeeperItem>> call, Response<ArrayList<ShopkeeperItem>> response) {
                Log.v("resonseShops", response.code() + "length" + response.body().size());
                AllShops allshops = new AllShops();
                Gson gson = new Gson();
                allshops.setShopkeeper_items(response.body());
                String allShopsHere = gson.toJson(allshops);
                getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().putString("shops_all", allShopsHere).apply();
            }

            @Override
            public void onFailure(Call<ArrayList<ShopkeeperItem>> call, Throwable t) {

            }
        });

    }

    public void getProductDetail(String productDetail,final int pos) {
           /* Log.v("checkIDafterIN",getIntent().getIntExtra("SelectedSubCategoryID",8)+"");
        Call<ArrayList<ProductItem>> getproducts = apiService.listproducts(getIntent().getIntExtra("SelectedSubCategoryID",8));
        getproducts.enqueue(new Callback<ArrayList<ProductItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductItem>> call, Response<ArrayList<ProductItem>> response) {
                Log.v("resonse",response.code()+"length"+response.body().size()) ;
              productItems = response.body() ;

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ProductItem>> call, Throwable t) {

            }
        }) ;
            return productItems;*/


        Call<ProductDetailItem> getproducts = apiServiceActual.listProductDetail(productDetail);
        getproducts.enqueue(new Callback<ProductDetailItem>() {
            @Override
            public void onResponse(Call<ProductDetailItem> call, Response<ProductDetailItem> response) {
                Log.v("responseProductDetail", response.code() + "length" + response.body().getData());
                //  productItems = response.body() ;

                //mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductDetailItem> call, Throwable t) {

            }
        });

        Gson gson = new Gson();
        AllProducts allProducts = new AllProducts();
        String val = getSharedPreferences("MYPrefs", MODE_PRIVATE).
                getString("products_all", "");
        allProducts.setProduct_items(gson.fromJson(val, AllProducts.class).getMarket_items());


        if (MyApplication.sShopkeeper_flag == true) {
            Intent i = new Intent(SeacrhProductActivity.this, ProductDetailActivity.class);
            startActivity(i);

        } else {
            Intent i = new Intent(SeacrhProductActivity.this, SearchShopsActivity.class);
            i.putExtra("ProductId",allProducts.getMarket_items().get(pos).getProduct_id().toString());
            startActivity(i);
        }

        // return allProducts.getMarket_items() ;
    }


}

