package alladinmarket.com.alladinmarket.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.adapters.CartAdaper;
import alladinmarket.com.alladinmarket.network.pojo.CartItems.CartItemObject;
import alladinmarket.com.alladinmarket.network.pojo.CartItems.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiServiceActual;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nmn on 3/4/17.
 */

public class MyCartFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Product> products = new ArrayList<>();

    android.os.Handler hn = new android.os.Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {

            mAdapter = new CartAdaper((ArrayList<Product>) msg.obj);
            mRecyclerView.setAdapter(mAdapter);

        }
    } ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("MYCART","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("MYCART","onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        // Set title bar

        Log.v("MYCArt","onCreateView");

        mRecyclerView = (RecyclerView)v. findViewById(R.id.rv_cart_items);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)


        updateData();





        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("MYCART","onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("MYCART","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("MYCART","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("MYCART","onResume");}

    @Override
    public void onPause() {
        super.onPause();
        Log.v("MYCART","onPause");}

    @Override
    public void onStop() {
        super.onStop();

        Log.v("MYCART","onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("MYCART","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("MYCART","onDestroyh");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v("MYCART","onDetach");
    }


    public void updateData()
    {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                 apiCall();
            }
        } ;

        Thread th = new Thread(runnable);
        th.start();
    }


    public void apiCall() {

        Call<CartItemObject> callImages = apiServiceActual.listCartItems(getContext().getSharedPreferences("MYPrefs",MODE_PRIVATE).getString("UserId","1")) ;
        callImages.enqueue(new Callback<CartItemObject>() {
            @Override
            public void onResponse(Call<CartItemObject> call, Response<CartItemObject> response) {
                Log.v("checkAPICartItems","response_code"+response.code()+response.body().getStatus());


                  products =(ArrayList) response.body().getData().getProduct() ;


                Message message = new Message();
                message.obj = products ;

                hn.sendMessage(message) ;




                        /*Gson gson = new Gson();
                        allCategories.setCategory(response.body().getCategory());
                        //  allCategories.setMarket_items(response.body());
                        String allMarket = gson.toJson(allCategories);
                        getSharedPreferences("MYPrefs",MODE_PRIVATE).edit().putString("categories_all",allMarket).apply();*/
            }

            @Override
            public void onFailure(Call<CartItemObject> call, Throwable t) {
                Log.v("errorAPI","response_error"+"someErrorCategory");
            }
        });






    }
}
