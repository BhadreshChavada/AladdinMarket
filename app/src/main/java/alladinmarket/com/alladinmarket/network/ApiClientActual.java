package alladinmarket.com.alladinmarket.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nmn on 5/4/17.
 */

public class ApiClientActual {
// http://jay.seretoneinfotech.com/api/
    //public static final String BASE_URL = "http://webkunj.com/demo/android_api/";
// http://jay.seretoneinfotech.com/api/get_category.php
    public static final String BASE_URL = "http://jay.seretoneinfotech.com/api/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}