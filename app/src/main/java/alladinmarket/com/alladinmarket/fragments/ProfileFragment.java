package alladinmarket.com.alladinmarket.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.activities.DrawerActivity;
import alladinmarket.com.alladinmarket.activities.LoginActivity;
import alladinmarket.com.alladinmarket.adapters.FragmentPager;
import alladinmarket.com.alladinmarket.network.pojo.LoginEntity.LoginObject;
import alladinmarket.com.alladinmarket.utils.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiServiceActual;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nmn on 3/4/17.
 */

public class ProfileFragment extends Fragment{

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    FragmentPager adapter ;

    CirclePageIndicator indicator ;


    EditText name , Email , contact  ;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ViewPager mViewPagerFragments ;
    TabLayout mTabLayout ;

    private static final Integer[] IMAGES= {R.drawable.tyu,R.drawable.tyu,R.drawable.tyu,R.drawable.tyu};
    String[] tabTitles={"BEST OFFERS","CATEGORIES", "TOP STORIES"};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  name = (EditText)v.findViewById(R.id.edit_name);
        Email  = (EditText)v.findViewById(R.id.edit_text_email);*/
//        Log.v("chek",name.getText()+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        // Set title bar
        name = (EditText)v.findViewById(R.id.edit_name);
        Email  = (EditText)v.findViewById(R.id.edit_text_email); // edit_text_contact_no
        contact  = (EditText)v.findViewById(R.id.edit_text_email);
      //  mTabLayout.setupWithViewPager(mViewPagerFragments);
        Log.v("chek",name.getText()+"");
      name.setHint(getProfileDetails().getLoginObject().getFirst_name());

        Email.setHint(getProfileDetails().getLoginObject().getUser_email());

        name.setHint(getProfileDetails().getLoginObject().getFirst_name());
      //  Pro
        //contact.setHint(getProfileDetails().getLoginObject().get);

        return v;
    }



    public Profile getProfileDetails (){


    Gson gs = new Gson() ;
    Profile profile1 =
            gs.fromJson(    getContext().getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("profile_detail", ""),
            Profile.class) ;

    Log.v("checkDetails",profile1.getLoginObject().getLast_name()+"checkDetails") ;

    return profile1 ;

}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
}
