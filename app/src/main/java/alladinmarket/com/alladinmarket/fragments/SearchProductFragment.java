package alladinmarket.com.alladinmarket.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.activities.DrawerActivity;
import alladinmarket.com.alladinmarket.activities.SubcategoryActivity;
import alladinmarket.com.alladinmarket.adapters.CategoryAdaper;
import alladinmarket.com.alladinmarket.network.pojo.AllMarkets;
import alladinmarket.com.alladinmarket.network.pojo.Categories;
import alladinmarket.com.alladinmarket.network.pojo.Category;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchProductFragment extends Fragment implements CategoryAdaper.OnItemClickListener,
        DrawerActivity.SearchQueryListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //  private String queryString ;

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private CategoryAdaper mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Category> categories = new ArrayList<>();


    public SearchProductFragment() {
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
    public static SearchProductFragment newInstance(String param1, String param2) {
        SearchProductFragment fragment = new SearchProductFragment();
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
        View view = inflater.inflate(R.layout.fragment_search_product, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_search_product_list);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        try {
            Gson gson = new Gson();
            gson.fromJson(getContext().getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("categories_all", ""), AllMarkets.class);
            categories = (ArrayList<Category>) gson.fromJson(getContext().
                    getSharedPreferences("MYPrefs", MODE_PRIVATE).
                    getString("categories_all", ""), Categories.class).getCategory();


            mAdapter = new CategoryAdaper(getContext(), categories);


            DrawerActivity.setOnSearchQueryListener(this);
            mRecyclerView.setAdapter(mAdapter);
            //  mAdapter.setOnItemClickListener(new CategoryAdaper.OnItemClickListener())
            mAdapter.setOnItemClickListener(new CategoryAdaper.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {

                    try {
                        Log.d("CategorySize", String.valueOf(categories.size()));

                        Intent i = new Intent(getContext(), SubcategoryActivity.class);
                        Log.v("checkId", categories.get(position).getTermId() + "");
                        i.putExtra("selectedCategoryID", categories.get(position).getTermId());
                        startActivity(i);
                    } catch (NullPointerException e) {
                        Toast.makeText(getActivity(), "Try Again...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "Try Again...", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View itemView, int position) {

    }

    @Override
    public void searchEntered(String query) {
        Log.v("searchValueFragment", query);

        mAdapter.getFilter().filter((CharSequence) query);
        mAdapter.notifyDataSetChanged();
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
}
