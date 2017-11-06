package alladinmarket.com.alladinmarket.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.network.ApiClient;
import alladinmarket.com.alladinmarket.network.ApiClientActual;
import alladinmarket.com.alladinmarket.network.ApiInterface;
import alladinmarket.com.alladinmarket.network.pojo.AllDistricts;
import alladinmarket.com.alladinmarket.network.pojo.AllMarkets;
import alladinmarket.com.alladinmarket.network.pojo.AllNewTrends;
import alladinmarket.com.alladinmarket.network.pojo.Categories;
import alladinmarket.com.alladinmarket.network.pojo.DistrictItem;
import alladinmarket.com.alladinmarket.network.pojo.Market_item;
import alladinmarket.com.alladinmarket.network.pojo.ObjectImage.AllImages;
import alladinmarket.com.alladinmarket.network.pojo.Promoter.PromoterObject;
import alladinmarket.com.alladinmarket.network.pojo.Trend.Datum;
import alladinmarket.com.alladinmarket.network.pojo.Trend.TrendItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nmn on 19/4/17.
 */

public class MyService extends Service {


    private static final String TAG = MyService.class.getSimpleName();

    final public static String MY_ACTION = "MY_ACTION";

    // TODO - insert your themoviedb.org API KEY here

    public static ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);

    public static ApiInterface apiServiceActual =
            ApiClientActual.getClient().create(ApiInterface.class);


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                Call<ArrayList<Market_item>> call = apiService.listMarkets();
//                call.enqueue(new Callback<ArrayList<Market_item>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<Market_item>> call, Response<ArrayList<Market_item>> response) {
//                        try {
//                            Log.v("checkAPI", "response_code" + response.code() + "lentgh" + response.body().size());
//                            AllMarkets allMarkets = new AllMarkets();
//                            Gson gson = new Gson();
//                            allMarkets.setMarket_items(response.body());
//                            String allMarket = gson.toJson(allMarkets);
//                            getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().putString("markets_all", allMarket).apply();
//                        } catch (NullPointerException w) {
//                        } catch (Exception e) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<Market_item>> call, Throwable t) {
//                        Log.v("errorAPI", "response_error" + "someError");
//                    }
//                });


                Call<alladinmarket.com.alladinmarket.network.pojo.ObjectImage.ImageObject> callImages = apiServiceActual.
                        listImages();
                callImages.enqueue(new Callback<alladinmarket.com.alladinmarket.network.pojo.ObjectImage.ImageObject>() {
                    @Override
                    public void onResponse(Call<alladinmarket.com.alladinmarket.network.pojo.ObjectImage.ImageObject> call
                            , Response<alladinmarket.com.alladinmarket.network.pojo.ObjectImage.ImageObject> response) {
                        try {

                            Log.v("checkAPIImageObject", "response_code" +
                                    response.code() + "lentgh" + response.body().getData().get(0).getProductImage());
                            AllImages allImages = new AllImages();

                            ArrayList<String> strs = new ArrayList<String>();

                            for (int i = 0; i < response.body().getData().size(); i++) {
                                // String a = strs.get(i) ;
                                //a= response.body().getData().get(i).getProduct_image();
                                strs.add(i, response.body().getData().get(i).getProductImage());

                            }

                            //   LandingFragment.getImages(strs);

                            Gson gson = new Gson();
                            allImages.setArrayList((ArrayList<alladinmarket.com.alladinmarket.network.pojo.ObjectImage.Datum>)
                                    response.body().getData());


                            String allimges = gson.toJson(allImages);
                            getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().putString("images_all", allimges).apply();
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<alladinmarket.com.alladinmarket.network.pojo.ObjectImage.ImageObject> call, Throwable t) {
                        Log.v("errorAPI", "response_error" + "someErrorCategory" + t.getMessage());
                    }
                });


                Call<Categories> callCategories = apiServiceActual.listCategories();
                callCategories.enqueue(new Callback<Categories>() {
                    @Override
                    public void onResponse(Call<Categories> call, Response<Categories> response) {

                        try {
                            Log.v("checkAPICategories", "response_code" + response.code() + "lentgh" + response.body().getCategory().size());
                            Categories allCategories = new Categories();
                            Gson gson = new Gson();
                            allCategories.setCategory(response.body().getCategory());
                            //  allCategories.setMarket_items(response.body());
                            String allMarket = gson.toJson(allCategories);
                            getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().putString("categories_all", allMarket).apply();
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<Categories> call, Throwable t) {
                        Log.v("errorAPI", "response_error" + "someErrorCategory");
                    }
                });

                Call<TrendItem> callNewTrends = apiServiceActual.listTrendsProducts();
                callNewTrends.enqueue(new Callback<TrendItem>() {
                    @Override
                    public void onResponse(Call<TrendItem> call, Response<TrendItem> response) {
                        try {

                            Log.v("checkAPINewtrends", "response_code" + response.code() +
                                    "lentgh" + response.body().getData().size());
                            AllNewTrends allTrends = new AllNewTrends();
                            Gson gson = new Gson();
                            allTrends.setNewTrends_items((ArrayList<Datum>) response.body().getData());
                            String allTrendsNew = gson.toJson(allTrends);
                            getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().putString("trends_all", allTrendsNew).apply();

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<TrendItem> call, Throwable t) {
                        Log.v("errorAPI", "response_error" + "someErrorTrends");
                    }
                });

                Call<alladinmarket.com.alladinmarket.network.pojo.Promoter.PromoterObject> callPromoters =
                        apiServiceActual.listPromoters();
                callPromoters.enqueue(new Callback<alladinmarket.com.alladinmarket.network.pojo.Promoter.PromoterObject>() {
                    @Override
                    public void onResponse(Call<alladinmarket.com.alladinmarket.network.pojo.Promoter.PromoterObject> call, Response<alladinmarket.com.alladinmarket.network.pojo.Promoter.PromoterObject> response) {

                        try {
                            Log.v("checkAPIPromoter", "response_code" + response.code() +
                                    "lentgh" + response.body().getPromoter().size());
                            PromoterObject allPromoters = new PromoterObject();
                            Gson gson = new Gson();
                            allPromoters.setPromoter(response.body().getPromoter());
                            //     allPromoters.setPromoter_items((ArrayList<Promoter>) response.body().getPromoter());
                            String allPromotersHere = gson.toJson(allPromoters);
                            getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().
                                    putString("promoters_all", allPromotersHere).apply();
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<alladinmarket.com.alladinmarket.network.pojo.Promoter.PromoterObject> call, Throwable t) {
                        Log.v("errorAPI", "response_error" + "someErrorPromototers");
                    }
                });


//                Call<ArrayList<DistrictItem>> callDistricts = apiService.listDistricts();
//                callDistricts.enqueue(new Callback<ArrayList<DistrictItem>>() {
//                    @Override
//                    public void onResponse(Call<ArrayList<DistrictItem>> call, Response<ArrayList<DistrictItem>> response) {
//                        try {
//                            Log.v("checkAPIDistricts", "response_code" + response.code() + "lentgh" + response.body().size());
//                            AllDistricts allDistricts = new AllDistricts();
//                            Gson gson = new Gson();
//                            allDistricts.setDistrict_items(response.body());
//                            String allDistrictsHere = gson.toJson(allDistricts);
//                            getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().putString("districts_all", allDistrictsHere).apply();
//                        } catch (Exception e) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ArrayList<DistrictItem>> call, Throwable t) {
//                        Log.v("errorAPI", "response_error" + "someErrorDistricts");
//                    }
//                });


            }
        }, 200);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
