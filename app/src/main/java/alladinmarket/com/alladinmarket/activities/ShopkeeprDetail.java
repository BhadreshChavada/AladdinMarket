package alladinmarket.com.alladinmarket.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.network.pojo.AllShops;
import alladinmarket.com.alladinmarket.network.pojo.ShopkeeperItem;

public class ShopkeeprDetail extends AppCompatActivity {

    Toolbar mToolbar;
    TextView tv_shop_name, tv_shop_no, tv_featured_category, tv_address;
    ImageView img_map_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeepr_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        tv_shop_no = (TextView) findViewById(R.id.tv_shop_no);
        tv_featured_category = (TextView) findViewById(R.id.tv_featured_category);
        tv_address = (TextView) findViewById(R.id.tv_address);
        img_map_view = (ImageView) findViewById(R.id.img_map_view);

        int position = getIntent().getIntExtra("ShopekeeperPosition", 0);

        Gson gson = new Gson() ;
        final ArrayList<ShopkeeperItem> shopsItems =gson.fromJson(
                getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("shops_all", ""), AllShops.class).getMarket_items();

        tv_shop_name.setText(shopsItems.get(position).getShopname());
        tv_shop_no.setText(shopsItems.get(position).getShopnumber());
        tv_featured_category.setText(shopsItems.get(position).getCategory());
        tv_address.setText(shopsItems.get(position).getAddress());




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
