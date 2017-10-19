package alladinmarket.com.alladinmarket.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.activities.DrawerActivity;
import alladinmarket.com.alladinmarket.activities.ShopKeepersActivity;
import alladinmarket.com.alladinmarket.adapters.CategoryAdaper;
import alladinmarket.com.alladinmarket.adapters.MarketAdaper;
import alladinmarket.com.alladinmarket.adapters.SearchShopkeeperAdaper;
import alladinmarket.com.alladinmarket.network.ApiClient;
import alladinmarket.com.alladinmarket.network.ApiInterface;
import alladinmarket.com.alladinmarket.network.pojo.AllMarkets;
import alladinmarket.com.alladinmarket.network.pojo.Market_item;
import alladinmarket.com.alladinmarket.services.MyService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchMarketFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchMarketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchMarketFragment extends Fragment implements MarketAdaper.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;





    private ArrayList<Market_item> market_items = new ArrayList<>();


    private RecyclerView mRecyclerView;
    private MarketAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MarketAdaper.OnItemClickListener mItemClickListener ;
    private EditText mPincode ;

    private AppCompatSpinner mSpinner ;

    private String[] mStates = {"Delhi","Delhi","Delhi"} ;

    private OnFragmentInteractionListener mListener;

    public SearchMarketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchMarketFragment newInstance(String param1, String param2) {
        SearchMarketFragment fragment = new SearchMarketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_market, container, false) ;

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_markets);
        mSpinner = (AppCompatSpinner)view.findViewById(R.id.spinner_select_state);
        mPincode = (EditText)view.findViewById(R.id.tv_pincode);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        Gson gson = new Gson() ;
        gson.fromJson(getContext().getSharedPreferences("MYPrefs",MODE_PRIVATE).getString("markets_all",""), AllMarkets.class) ;
        market_items=   gson.fromJson(getContext().getSharedPreferences("MYPrefs",MODE_PRIVATE).getString("markets_all",""), AllMarkets.class).getMarket_items();



        mAdapter =new MarketAdaper(market_items) ;
        mRecyclerView.setAdapter(mAdapter);
        Log.v("checkCount",mAdapter.getItemCount()+"size"+market_items.size()) ;
        mSpinner.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,mStates));

        mAdapter.setOnItemClickListener(new MarketAdaper.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent i = new Intent(getContext(), ShopKeepersActivity.class);
                startActivity(i);
            }
        });
        final   boolean isEditable = true ;
        mPincode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isEditable){
                    v.setFocusable(true);
                    v.setFocusableInTouchMode(true);
                } else {
                    mPincode.setFocusable(false);
                }

                return false;
            }
        });

        mPincode.setOnFocusChangeListener(new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange (View view,boolean b){
            if (!b) {
                hideKeyboard();
            }
        }
        }) ;


        mPincode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                  //  int foo = Integer.parseInt(mPincode.getText().toString().trim());
                    Log.v("reach","here") ;
                    try {
                        searchMarket(Integer.parseInt(mPincode.getText().toString().trim()));
                    }
                    catch(NumberFormatException nue)
                    {
                    nue.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });




        return view ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public void searchMarket(int pincode)

    {
        Call<ArrayList<Market_item>> marketItemsCall = MyService.apiService.listMarkets_pincode(pincode);
                marketItemsCall.enqueue(new Callback<ArrayList<Market_item>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Market_item>> call, Response<ArrayList<Market_item>> response) {
                      Log.v("resonse",response.code()+"length"+response.body().size()) ;
                        market_items.removeAll(market_items);
                        market_items.addAll(response.body());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Market_item>> call, Throwable t) {

                    }
                }) ;

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Intent i = new Intent(getContext(), ShopKeepersActivity.class);
        startActivity(i);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void hideKeyboard() {
      //  View view = getContext().getCurrentFocus();
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
