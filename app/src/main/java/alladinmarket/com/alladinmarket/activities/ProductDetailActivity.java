package alladinmarket.com.alladinmarket.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.adapters.ProductImagesAdaper;
import alladinmarket.com.alladinmarket.adapters.SearchProductAdaper;

public class ProductDetailActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProductImagesAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar mToolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_product_images);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mLayoutManager = new GridLayoutManager(this,6);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ProductImagesAdaper();
        mAdapter.setOnItemClickListener(new ProductImagesAdaper.OnItemClickListener() {
         @Override
         public void onItemClick(View itemView, int position) {



         }
     });

        mRecyclerView.setAdapter(mAdapter);
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
