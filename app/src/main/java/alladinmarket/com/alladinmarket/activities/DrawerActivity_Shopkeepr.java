package alladinmarket.com.alladinmarket.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.fragments.AboutFragment;
import alladinmarket.com.alladinmarket.fragments.LandingFragment;
import alladinmarket.com.alladinmarket.fragments.LandingFragment_shopkeepr;
import alladinmarket.com.alladinmarket.utils.Imageutils;

public class DrawerActivity_Shopkeepr extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        Imageutils.ImageAttachmentListener{


    private Toolbar mToolbar;
    private ImageView mShuffle, mWindow, mSaveIcon;
    private NavigationView mNavigationView ;
    private TextView mTextView;
     DrawerLayout drawer ;
    Imageutils imageutils;


    private Bitmap bitmap;
    private String file_name;

    String path ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        setUpUi();

        imageutils =new Imageutils(DrawerActivity_Shopkeepr.this);


        mSaveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DrawerActivity_Shopkeepr.this,CartActivity.class);
                startActivity(i);
            }
        });

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        Bundle bundle = new Bundle();
        bundle.putString("path", path);


        LandingFragment_shopkeepr fragment = new LandingFragment_shopkeepr();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void setUpUi() {


        mTextView = (TextView) findViewById(R.id.tv_main_toolbar_title);

        // mTextView.setTextSize(15);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //mShuffle = (ImageView) findViewById(R.id.img_shuffle);
        mWindow = (ImageView) findViewById(R.id.img_window);
        mSaveIcon = (ImageView) findViewById(R.id.img_save_icon);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);


        mNavigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment ;
        android.support.v4.app.FragmentTransaction fragmentTransaction ;

        switch (item.getItemId()) {




            case R.id.home:
                Bundle bundle = new Bundle();
                bundle.putString("path", path);
                bundle.putString("file_name", file_name);
                fragment = new AboutFragment();
                fragment.setArguments(bundle);
                fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                mTextView.setText("UPLOAD PRODUCT");
                break;


            case R.id.notifications:
                fragment = new LandingFragment();
                 fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
               break;


            case R.id.rate_app :

                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;



        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri) {
        this.bitmap=file;
        this.file_name=filename;
    //    iv_attachment.setImageBitmap(file);

        path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        imageutils.createImage(file,filename,path,false);

    }

}
