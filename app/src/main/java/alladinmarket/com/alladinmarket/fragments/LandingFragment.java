package alladinmarket.com.alladinmarket.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.adapters.FragmentPager;
import alladinmarket.com.alladinmarket.adapters.ImageAdapter;
import alladinmarket.com.alladinmarket.network.pojo.ObjectImage.AllImages;
import alladinmarket.com.alladinmarket.utils.GetAlImages;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nmn on 3/4/17.
 */

public class LandingFragment extends Fragment{

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    FragmentPager adapter ;

    CirclePageIndicator indicator ;


    public static final String GET_IMAGE_URL="http://webkunj.com/demo/android_api/fetchTrendingImages.php";

    public GetAlImages getAlImages /*= new GetAlImages("")*/ ;

    public static final String BITMAP_ID = "BITMAP_ID";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ViewPager mViewPagerFragments ;


    static ArrayList <String> strings = new ArrayList<>();
    TabLayout mTabLayout ;

    Bitmap[] bmp = new Bitmap[10];

    private static final Integer[] IMAGES= {R.drawable.tyu,R.drawable.tyu,R.drawable.tyu,R.drawable.tyu};
    String[] tabTitles={"BEST OFFERS","CATEGORIES", "TOP STORIES"};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landingfragment, container, false);
        // Set title bar



        mPager = (ViewPager)v. findViewById(R.id.pager);
         indicator = (CirclePageIndicator)v.
                findViewById(R.id.indicator);

        mViewPagerFragments = (ViewPager)v.findViewById(R.id.vp_fragments);
        mTabLayout = (TabLayout)v.findViewById(R.id.tabs_sliding);

       getURLs();

        init();



//        indicator.setViewPager(mPager);


//        mRecyclerView.setLayoutManager(mLayoutManager);
        mTabLayout.setupWithViewPager(mViewPagerFragments);

        // specify an adapter (see also next example)

        adapter = new FragmentPager(getFragmentManager());


        //mRecyclerView.setAdapter(mAdapter);

        mViewPagerFragments.setAdapter(adapter);

      //  mTabLayout.setupWithViewPager(mViewPagerFragments);

        return v;
    }


    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Loading...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAlImages = new GetAlImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(),"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
               /* mPager.setAdapter(new ImageAdapter(getContext(),GetAlImages.bitmaps));
                indicator.setViewPager(mPager);*/

                mPager.setAdapter(new ImageAdapter(getContext(),bmp));
                indicator.setViewPager(mPager);
            }

            @Override
            protected Void doInBackground(Void... voids) {

                   // getAlImages.getAllImages();
                Gson gs = new Gson() ;
                ArrayList<String> allUrls = new ArrayList<>();
                String str = getContext().getSharedPreferences("MYPrefs",MODE_PRIVATE).getString("images_all","");
                AllImages allImages = gs.fromJson(str,AllImages.class);
                for (int i =0 ; i<allImages.getArrayList().size();i++)
                {
                 strings.add(allImages.getArrayList().get(i).getProduct_image());
                }


                    Log.v("checkBMPLENGTH",getBmps(strings).size()+""+strings.size()) ;
                    bmp = getBmps(strings).toArray(new Bitmap[strings.size()]);


                for(int i=0;i<IMAGES.length;i++)
                    ImagesArray.add(IMAGES[i]);


                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }


    public static void getImages(ArrayList <String> urls){

       strings = urls ;
        Log.v("checkCode",strings.toString()) ;

    }

    public ArrayList<Bitmap> getBmps(ArrayList <String> urls)
    {


        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

         ArrayList<Bitmap> bmps = new ArrayList<>();

        for (int i = 0; i<urls.size();i++)
        {
            try {
               String[] a = urls.toArray(new String[0]) ;
               URL url = new URL(a[i]);
                bmps.add(i,BitmapFactory.decodeStream(url.openConnection().getInputStream()));;
            }

            catch (Exception e)
            {
             e.getMessage() ;
            }
        }
       return bmps ;
    }

}
