package alladinmarket.com.alladinmarket.fragments;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
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
import alladinmarket.com.alladinmarket.adapters.OrdersAdaper;
import alladinmarket.com.alladinmarket.adapters.SearchProductAdaper;
import alladinmarket.com.alladinmarket.adapters.SearchShopkeeperAdaper;
import alladinmarket.com.alladinmarket.adapters.TrendsAdapter;
import alladinmarket.com.alladinmarket.network.pojo.CartItems.CartItemObject;
import alladinmarket.com.alladinmarket.network.pojo.CartItems.Product;
import alladinmarket.com.alladinmarket.network.pojo.OrderObject.Datum;
import alladinmarket.com.alladinmarket.network.pojo.OrderObject.OrderObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiServiceActual;

/**
 * Created by nmn on 3/4/17.
 */

public class OrdersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OrdersAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Datum> orders ;

    private OrdersAdaper ordersAdaper ;

    android.os.Handler hn = new android.os.Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {

            ordersAdaper = new OrdersAdaper((ArrayList<Datum>)msg.obj) ;

            mRecyclerView.setAdapter(ordersAdaper);



        }
    } ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        // Set title bar
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_orders);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
      /*  mAdapter = new OrdersAdaper(orders);
        mRecyclerView.setAdapter(mAdapter);*/

      updateData();




        return v;
    }

    public void updateData()
    {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                apiCall("");
            }
        } ;

        Thread th = new Thread(runnable);
        th.start();
    }


    public void apiCall(String orderId) {

        Call<OrderObject> callImages = apiServiceActual.listOrders("17");
        callImages.enqueue(new Callback<OrderObject>() {
            @Override
            public void onResponse(Call<OrderObject> call, Response<OrderObject> response) {
                Log.v("checkAPIOrderItems","response_code"+response.code()+response.body().getStatus());


               orders =(ArrayList) response.body().getData() ;


                Message message = new Message();
                message.obj = orders ;

                hn.sendMessage(message) ;


            }

            @Override
            public void onFailure(Call<OrderObject> call, Throwable t) {
                Log.v("errorAPI","response_error"+"someErrorCategory");
            }
        });






    }
}
