package alladinmarket.com.alladinmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.adapters.ProductImagesAdaper;
import alladinmarket.com.alladinmarket.network.pojo.ProductDataByShopkeeper.ProductDataByShopkeeper;
import alladinmarket.com.alladinmarket.network.pojo.ProductDetails.ProductDetailItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiServiceActual;

public class ProductDetailActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProductImagesAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar mToolbar;
    ImageView mainImage;
    TextView txtProductName, txtProductCost;
    Button btnAddCart, btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_product_images);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mainImage = (ImageView) findViewById(R.id.img_product_main);
        txtProductName = (TextView) findViewById(R.id.tv_product_name);
        txtProductCost = (TextView) findViewById(R.id.tv_product_cost);
        btnAddCart = (Button) findViewById(R.id.btn_add_cart);
        btnShare = (Button) findViewById(R.id.btn_share);

        if (getIntent().getStringExtra("ShopkeeperID") != null) {
            apiCall(getIntent().getStringExtra("ProductID"), getIntent().getStringExtra("ShopkeeperID"));
        } else {
//            apiCall(getIntent().getStringExtra("ProductId"));
//            Log.d("ProductId",getIntent().getStringExtra("ProductId"));
            Intent i = getIntent();
            int ProductId = i.getIntExtra("ProductId",0);
            apiCall(String.valueOf(ProductId));
        }
        mLayoutManager = new GridLayoutManager(this, 6);
        mRecyclerView.setLayoutManager(mLayoutManager);


        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // TODO: 23/10/17 write code to share the data
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void apiCall(String productId, String shopkeeperId) {

        Call<ProductDataByShopkeeper> getproducts = apiServiceActual.ProductDetails(productId, shopkeeperId);
        Log.d("URL", String.valueOf(getproducts.request().url()));
        getproducts.enqueue(new Callback<ProductDataByShopkeeper>() {
            @Override
            public void onResponse(Call<ProductDataByShopkeeper> call, Response<ProductDataByShopkeeper> response) {
                Log.v("responseProductDetail", response.code() + "length" + response.body().getData());

//                Log.d("PDetails", response.body().getData().getProdTitle());
                Picasso.with(getApplicationContext())
                        .load(response.body().getData().getProdImage())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(mainImage);

                txtProductName.setText(response.body().getData().getProdTitle());
                txtProductCost.setText("Our Price. \t" + response.body().getData().getProdPrice());

                mAdapter = new ProductImagesAdaper(response.body().getData().getGallery(), ProductDetailActivity.this);

                mRecyclerView.setAdapter(mAdapter);

            }


            @Override
            public void onFailure(Call<ProductDataByShopkeeper> call, Throwable t) {

            }
        });
    }

    void apiCall(String productDetail){
        Call<ProductDetailItem> getproducts = apiServiceActual.listProductDetail(productDetail);
        Log.d("URL", String.valueOf(getproducts.request().url()));
        getproducts.enqueue(new Callback<ProductDetailItem>() {
            @Override
            public void onResponse(Call<ProductDetailItem> call, Response<ProductDetailItem> response) {
                Log.v("responseProductDetail", response.code() + "length" + response.body().getData());
                //  productItems = response.body() ;

                //mAdapter.notifyDataSetChanged();

                Picasso.with(getApplicationContext())
                        .load(response.body().getData().getPost_image())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(mainImage);

                txtProductName.setText(response.body().getData().getPost_title());
                txtProductCost.setText("Our Price. \t" + response.body().getData().getPrice());

                mAdapter = new ProductImagesAdaper(response.body().getData().getGallery(), ProductDetailActivity.this);

                mRecyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<ProductDetailItem> call, Throwable t) {

            }
        });
    }

}
