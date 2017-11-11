package alladinmarket.com.alladinmarket.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.activities.PromoterDetailActivity;
import alladinmarket.com.alladinmarket.adapters.PromoterAdaper;
import alladinmarket.com.alladinmarket.network.pojo.AllDistricts;
import alladinmarket.com.alladinmarket.network.pojo.AllMarkets;
import alladinmarket.com.alladinmarket.network.pojo.DistrictItem;
import alladinmarket.com.alladinmarket.network.pojo.Promoter.Promoter;
import alladinmarket.com.alladinmarket.network.pojo.Promoter.PromoterObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nmn on 3/4/17.
 */

public class PromoterFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PromoterAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Promoter> promoterItems = new ArrayList<>();
    private ArrayList<DistrictItem> districtItems = new ArrayList<>();

    private EditText mDistrict;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_trends, container, false);
        // Set title bar

        try {
            mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_search_trends_list);
            mDistrict = (EditText) v.findViewById(R.id.edt_search_district);


            mLayoutManager = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)


            Gson gson = new Gson();
            gson.fromJson(getContext().getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("markets_all", ""), AllMarkets.class);
            promoterItems =
                    (ArrayList<Promoter>) gson.fromJson(getContext().
                            getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("promoters_all", ""), PromoterObject.class)
                            .getPromoter();


            Log.v("check_list", promoterItems.size() + "");
            districtItems =
                    gson.fromJson(getContext().
                            getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("districts_all", ""), AllDistricts.class).getDistrict_items();


            mAdapter = new PromoterAdaper(promoterItems);

            mAdapter.setOnItemClickListener(new PromoterAdaper.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    Intent i = new Intent(getContext(), PromoterDetailActivity.class);
                    startActivity(i);
                }
            });

            mRecyclerView.setAdapter(mAdapter);


            final boolean isEditable = true;
            mDistrict.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (isEditable) {
                        v.setFocusable(true);
                        v.setFocusableInTouchMode(true);
                    } else {
                        mDistrict.setFocusable(false);
                    }

                    return false;
                }
            });

            mDistrict.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        hideKeyboard();
                    }
                }
            });


            mDistrict.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        //  int foo = Integer.parseInt(mPincode.getText().toString().trim());
                        Log.v("reach", "here");
                        try {
                            //  searchMarket(Integer.parseInt(mPincode.getText().toString().trim()));
                            //searchPromoter(getIdForDistrict(mDistrict.getText().toString().trim())) ;
                        } catch (NumberFormatException nue) {
                            nue.printStackTrace();
                        }
                        return true;
                    }
                    return false;
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        return v;
    }

   /* public void searchPromoter(int id)
    {
        Call<ArrayList<PromoterItem>> getpromoters = MyService.apiService.getpromoters(id);
        getpromoters.enqueue(new Callback<ArrayList<PromoterItem>>() {
            @Override
            public void onResponse(Call<ArrayList<PromoterItem>> call, Response<ArrayList<PromoterItem>> response) {
                Log.v("resonse",response.code()+"length"+response.body().size()) ;
                promoterItems.removeAll(promoterItems);
                promoterItems.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ArrayList<PromoterItem>> call, Throwable t) {
            }
        }) ;
    }
*/

    public int getIdForDistrict(String districtName)

    {

        for (DistrictItem districtItem : districtItems)

        {
            if (districtItem.getName().compareToIgnoreCase(districtName) == 0) {
                ;
                return Integer.parseInt(districtItem.getId());

            }

        }
        return 0;
        //for (int i= 0, i<districtItems.size(),i++){};
    }

    public void hideKeyboard() {
        //  View view = getContext().getCurrentFocus();
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
