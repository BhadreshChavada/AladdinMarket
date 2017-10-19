package alladinmarket.com.alladinmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.network.pojo.Sub_category;
import alladinmarket.com.alladinmarket.network.pojo.Subcat;
import alladinmarket.com.alladinmarket.network.pojo.product.AllProducts;
import alladinmarket.com.alladinmarket.network.pojo.product.Datum;
import alladinmarket.com.alladinmarket.services.MyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubcategoryActivity extends AppCompatActivity {

    ListView listView ;
    Toolbar mToolbar ;
    ArrayAdapter<String> myAdapter ;
    ArrayList<Sub_category> subCategoryItems ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        listView = (ListView)findViewById(R.id.lv_subcategory_list);

       // String[] categories = {"SubCategory","SubCategory","SubCategory","SubCategory","SubCategory","SubCategory","SubCategory"};



         myAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getSubCategories() );

        listView.setAdapter(myAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.v("checkIDHere",subCategoryItems.get(i).getParent()+"");
                Intent intent = new Intent(SubcategoryActivity.this,SeacrhProductActivity.class);
                intent.putExtra("SelectedSubCategoryID",subCategoryItems.get(i).getName());
                getProducts(subCategoryItems.get(i).getName());
                startActivity(intent);
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



    public ArrayList<String> getSubCategories(){
Log.v("checlRxedID",getIntent().getIntExtra("selectedCategoryID",0)+"");

        final ArrayList<String>  subcategoryNames = new ArrayList<>();
        Call<Subcat> getpromoters =
                MyService.apiServiceActual.listSubCategory(getIntent().getIntExtra("selectedCategoryID",0));
        getpromoters.enqueue(new Callback<Subcat>() {
            @Override
            public void onResponse(Call<Subcat> call, Response<Subcat> response) {
                Log.v("resonse",response.code()+"length"+response.body().getSub_category().size()) ;
                for (int i = 0 ; i <response.body().getSub_category().size() ; i++)
                {
                   subCategoryItems = (ArrayList<Sub_category>) response.body().getSub_category();
                    subcategoryNames.add(i,response.body().getSub_category().get(i).getName());
                   // subcategoryNames[i]=response.body().get(i).getSubcategoryname() ;

                }

                Log.v("size",subcategoryNames.size()+"length"+response.body().getSub_category().size()) ;
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Subcat> call, Throwable t) {

            }
        }) ;
        Log.v("sizeIn",subcategoryNames.size()+"length") ;
        return subcategoryNames ;
    }

    public void getProducts (String id){
        Log.v("checkIDafterIN",getIntent().getIntExtra("SelectedSubCategoryID",8)+"");
        Call<alladinmarket.com.alladinmarket.network.pojo.product.ProductItem> getproducts =
                MyService.apiServiceActual.listSubCategoryProducts(id);
        getproducts.enqueue(new Callback<alladinmarket.com.alladinmarket.network.pojo.product.ProductItem>() {
            @Override
            public void onResponse(Call<alladinmarket.com.alladinmarket.network.pojo.product.ProductItem> call,
                                   Response<alladinmarket.com.alladinmarket.network.pojo.product.ProductItem> response) {
                Log.v("responsebeforeShared",response.code()+"length"+response.body().getData().size()) ;
              //  productItems = response.body() ;

                //mAdapter.notifyDataSetChanged();
                Gson gson = new Gson() ;

                AllProducts allProducts = new AllProducts() ;
                allProducts.setProduct_items((ArrayList<Datum>) response.body().getData());
                String allPromotersHere = gson.toJson(allProducts);
                getSharedPreferences("MYPrefs",MODE_PRIVATE).edit().putString("products_all",allPromotersHere).apply();
            }


            @Override
            public void onFailure(Call<alladinmarket.com.alladinmarket.network.pojo.product.ProductItem> call, Throwable t) {

            }
        }) ;

    }
}
