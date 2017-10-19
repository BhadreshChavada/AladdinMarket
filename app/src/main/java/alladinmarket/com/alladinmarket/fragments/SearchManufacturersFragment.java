package alladinmarket.com.alladinmarket.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.activities.ManufacturerDetail;
import alladinmarket.com.alladinmarket.activities.PromoterDetailActivity;
import alladinmarket.com.alladinmarket.adapters.CartAdaper;
import alladinmarket.com.alladinmarket.adapters.ManufacturersAdapter;
import alladinmarket.com.alladinmarket.adapters.PromoterAdaper;

/**
 * Created by nmn on 3/4/17.
 */

public class SearchManufacturersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ManufacturersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manufacturers, container, false);
        // Set title bar

        mRecyclerView = (RecyclerView)v. findViewById(R.id.rv_cart_items);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ManufacturersAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ManufacturersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent i = new Intent(getContext(), ManufacturerDetail.class) ;
                startActivity(i);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        return v;
    }
}
