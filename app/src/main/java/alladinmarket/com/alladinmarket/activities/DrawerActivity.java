package alladinmarket.com.alladinmarket.activities;


import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.fragments.AboutFragment;
import alladinmarket.com.alladinmarket.fragments.LandingFragment;
import alladinmarket.com.alladinmarket.fragments.LandingFragment_shopkeepr;
import alladinmarket.com.alladinmarket.fragments.LogoutFragment;
import alladinmarket.com.alladinmarket.fragments.MyCartFragment;
import alladinmarket.com.alladinmarket.fragments.OrdersFragment;
import alladinmarket.com.alladinmarket.fragments.ProfileFragment;
import alladinmarket.com.alladinmarket.fragments.SearchManufacturersFragment;
import alladinmarket.com.alladinmarket.fragments.SearchProductFragment;
import alladinmarket.com.alladinmarket.utils.MyApplication;

public class DrawerActivity extends AppCompatActivity implements
        SearchView.OnQueryTextListener,LogoutFragment.DialogClickListener ,NavigationView.OnNavigationItemSelectedListener{


    private Toolbar mToolbar;
    private ImageView mShuffle, mWindow, mSaveIcon;
    private NavigationView mNavigationView ;
    private TextView mTextView;
     DrawerLayout drawer ;
    DialogFragment dialog ;


    public interface SearchQueryListener
    {
        public  void searchEntered (String query) ;


    }

   static  SearchQueryListener searchQueryListener ;
    public static void setOnSearchQueryListener (SearchQueryListener searhQueryListener)
    {
     searchQueryListener = searhQueryListener ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        setUpUi();

        mSaveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DrawerActivity.this,CartActivity.class);
                startActivity(i);
            }
        });

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();








        if (MyApplication.sShopkeeper_flag==true) {


            LandingFragment_shopkeepr fragment = new LandingFragment_shopkeepr();

            android.support.v4.app.FragmentTransaction  fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            mTextView.setText("UPLOAD PRODUCT");
        }

        else {


            LandingFragment fragment = new LandingFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            mTextView.setText("HOME");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setUpUi() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextView = (TextView) findViewById(R.id.tv_main_toolbar_title);

        // mTextView.setTextSize(15);

        setSupportActionBar(mToolbar);
        //mShuffle = (ImageView) findViewById(R.id.img_shuffle);
        mWindow = (ImageView) findViewById(R.id.img_window);
        mWindow.setVisibility(View.GONE);
        mSaveIcon = (ImageView) findViewById(R.id.img_save_icon);

        mSaveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =  new Intent(DrawerActivity.this,CartActivity.class);
                startActivity(i);
            }
        });
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        if (MyApplication.sShopkeeper_flag==true) {

            mNavigationView.getMenu().clear();
            mNavigationView.inflateMenu(R.menu.activity_main_drawer_shopekeeper);
        }
        mNavigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment ;
        android.support.v4.app.FragmentTransaction fragmentTransaction ;

        switch (item.getItemId()) {

            case R.id.products :

                fragment = new SearchProductFragment();

                fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                mTextView.setText("PRODUCTS");

                break;

            case R.id.Orders :
                fragment = new OrdersFragment();

                fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                mTextView.setText("ORDERS");
                break;


            case R.id.about :
                fragment = new AboutFragment();

                fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                mTextView.setText("About Us");
                break;
            case R.id.cart :
                fragment = new MyCartFragment() ;

                fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                mTextView.setText("MY CART");
                break;


            case R.id.home:

                if (MyApplication.sShopkeeper_flag==true) {


                    fragment = new LandingFragment_shopkeepr();

                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                    mTextView.setText("UPLOAD PRODUCT");
                }

                else
                {
                    fragment = new LandingFragment();
                    fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.commit();
                    mTextView.setText("HOME");
                }
                break;


            case R.id.notifications:
                fragment = new ProfileFragment();
                 fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                mTextView.setText("PROFILE");
               break;



            case R.id.search_Manufacturers:
                fragment = new SearchManufacturersFragment();
                fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                mTextView.setText("PROFILE");
                break;

            case R.id.logout:
               showNoticeDialog();
                mTextView.setText("LOGOUT");
                break;

            case R.id.rate_app :

                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                mTextView.setText("");
                break;

            case R.id.Share_via :
            Intent i = new Intent(android.content.Intent.ACTION_SEND);

            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
            i.putExtra(android.content.Intent.EXTRA_TEXT, "Look what I found on AladdinMarket .You should check" +
                    " out AladdinMarket latest app update! https://play.google.com/store/apps/details?id=aladdinmarket.com.aladdinmarket");
            startActivity(Intent.createChooser(i, "Share via"));


        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure? . You will be logged out from the current session ")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                      /*  Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);*/
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
       MenuItem searchItem = (MenuItem) menu.findItem(R.id.search);

       // SearchView searchView = (SearchView) searchItem.getActionView();
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(DrawerActivity.this.getComponentName()));
        }


        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.v("searchValueSubmit",query);
        searchQueryListener.searchEntered(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.v("searchValueChange",newText);
        searchQueryListener.searchEntered(newText);



        return true;
    }


    @Override
    public void positiveClick(Context c) {
        Log.v("Log","out") ;
  finish();
    }

    @Override
    public void negativeClick(Context c) {
        Log.v("still","loggedIn") ;
        dialog.dismiss();
    }

    public void showNoticeDialog() {
         dialog = new LogoutFragment ();
        LogoutFragment f = (LogoutFragment)dialog ;
        f.setDialogClickListener(this);
        dialog.show(getSupportFragmentManager(), "LogoutDialogFragment");
        dialog.setCancelable (true);


    }
}
