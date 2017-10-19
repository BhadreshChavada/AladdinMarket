package alladinmarket.com.alladinmarket.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;

import alladinmarket.com.alladinmarket.R;

/**
 * Created by nmn on 3/4/17.
 */

public class SearchFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_market, container, false);
        // Set title bar




        return v;
    }
}
