package alladinmarket.com.alladinmarket.network;


import java.util.ArrayList;
import java.util.HashMap;

import alladinmarket.com.alladinmarket.network.pojo.CartItems.CartItemObject;
import alladinmarket.com.alladinmarket.network.pojo.Categories;
import alladinmarket.com.alladinmarket.network.pojo.DistrictItem;
import alladinmarket.com.alladinmarket.network.pojo.LoginEntity.LoginObject;
import alladinmarket.com.alladinmarket.network.pojo.Market_item;
import alladinmarket.com.alladinmarket.network.pojo.NewTrendsItem;
import alladinmarket.com.alladinmarket.network.pojo.OrderObject.OrderObject;
import alladinmarket.com.alladinmarket.network.pojo.ProductDataByShopkeeper.ProductDataByShopkeeper;
import alladinmarket.com.alladinmarket.network.pojo.ProductDetails.ProductDetailItem;
import alladinmarket.com.alladinmarket.network.pojo.ProductItem;
import alladinmarket.com.alladinmarket.network.pojo.PromoterItem;
import alladinmarket.com.alladinmarket.network.pojo.ServerResponse;
import alladinmarket.com.alladinmarket.network.pojo.ShopkeeperItem;
import alladinmarket.com.alladinmarket.network.pojo.SubCategoryItem;
import alladinmarket.com.alladinmarket.network.pojo.Subcat;
import alladinmarket.com.alladinmarket.network.pojo.Trend.TrendItem;
import alladinmarket.com.alladinmarket.network.pojo.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by nmn on 5/4/17.
 */

public interface ApiInterface {

    @GET("market")
    Call<ArrayList<Market_item>> listMarkets();

    @GET("get_category.php")
    Call<Categories> listCategories();

    @GET("get_cart_product_list.php")
    Call<CartItemObject> listCartItems(@Query("uid") String userId);

    @GET("update_cart.php")
    Call<CartItemObject> updateCart(@Query("item_key") String itemKey, @Query("qunt") String quantity);

    @GET("update_cart.php")
    Call<CartItemObject> deleteCart(@Query("item_key") String itemKey);

    @GET("get_trending_prod_imgs.php")
    Call<alladinmarket.com.alladinmarket.network.pojo.ObjectImage.ImageObject> listImages();

    @GET("get_orderlist.php")
    Call<OrderObject> listOrders(@Query("uid") String orderId);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginObject> login(@Field("username_email") String username, @Field("password") String password);

    @Multipart
    @POST("register.php")
    Call<Void> register(@QueryMap HashMap<String, String> map, @Part MultipartBody.Part file);


    @POST("register.php")
    Call<Void> register(@QueryMap HashMap<String, String> map);


    @GET("get_sub_category.php")
    Call<Subcat> listSubCategory(@Query("cid") int parentID);


    @GET("get_product_list.php")
    Call<alladinmarket.com.alladinmarket.network.pojo.product.ProductItem>
    listSubCategoryProducts(@Query("sc") String subcategory);

    @GET("privacy_about.php")
    Call<Void>
    getPrivacyPolicy();


    @GET("add_product.php")
    Call<Void>
    addProduct(@Query("uid") String userId, @Query("post_title") String postTitle, @Query("post_content") String content,
               @Query("testing") String testing, @Query("sku") String pr, @Query("price") String price,
               @Query("stock") String stck, @Query("term_id") String termId);

    @GET("get_trends_list.php")
    Call<TrendItem>
    listTrendsProducts();


    @GET("get_promoter_list.php")
    Call<alladinmarket.com.alladinmarket.network.pojo.Promoter.PromoterObject>
    listPromoters();

    @GET("get_product.php")
    Call<ProductDetailItem>
    listProductDetail(@Query("pid") String productId);


    // older ones
    @GET("districts")
    Call<ArrayList<DistrictItem>> listDistricts();

    @GET("subcategories")
    Call<ArrayList<SubCategoryItem>> listSubcategories(@Query("category") int category);

    @GET("products")
    Call<ArrayList<ProductItem>> listproducts(@Query("subcategory") int subcategory);

    @GET("productwise_shopkeepers")
    Call<ArrayList<ShopkeeperItem>> listshops(@Query("product_FK") int product);


    @GET("search_market")
    Call<ArrayList<Market_item>> listMarkets_pincode(@Query("pincode") int pincode);

    @GET("newTrends")
    Call<ArrayList<NewTrendsItem>> getNewTrends(@Query("district") int district);

    @GET("newTrendsAll")
    Call<ArrayList<NewTrendsItem>> getNewTrendsAll();

    @GET("promoters")
    Call<ArrayList<PromoterItem>> getpromoters(@Query("district") int district);

    @GET("promotersAll")
    Call<ArrayList<PromoterItem>> getpromotersAll();

    @GET("productwise_shopkeepers")
    Call<ArrayList<Market_item>> getproductwise_shopkeepers(@Query("product_FK") int product);


    @FormUrlEncoded
    @POST("login")
    Call<User> loginUser(@Field("email") String email,
                         @Field("password") String password
    );

    @Multipart
    @POST("uploadImage")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    @GET("get_product_by_shopkeeper_id.php")
    Call<ProductDataByShopkeeper> ProductDetails(@Query("prod_id") String productID, @Query("shopkep_id") String shopkeeperID);

    @Multipart
    @POST("edit_profile.php")
    Call<Void> updateProfile(@QueryMap HashMap<String, String> map, @Part MultipartBody.Part file);


    @POST("edit_profile.php")
    Call<Void> updateProfile(@QueryMap HashMap<String, String> map);
}
