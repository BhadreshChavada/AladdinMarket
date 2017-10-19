package alladinmarket.com.alladinmarket.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.adapters.FragmentPager;
import alladinmarket.com.alladinmarket.adapters.ImageAdapter;
import alladinmarket.com.alladinmarket.network.pojo.CartItems.CartItemObject;
import alladinmarket.com.alladinmarket.utils.Imageutils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiServiceActual;
import static android.app.Activity.RESULT_OK;

/**
 * Created by nmn on 3/4/17.
 */

public class LandingFragment_shopkeepr extends Fragment/* implements Imageutils.ImageAttachmentListener*/{

    ImageView iv_attachment1,iv_attachment2,iv_attachment3,iv_attachment4,iv_attachment5;

    Uri uri ;

    Button saveButton ;

    static final int REQUEST_TAKE_PHOTO = 1888;

    private static final int CAMERA_REQUEST = 1888;

    String mCurrentPhotoPath ;
    File  photoFile ;
    Bitmap mBitMap ;
    private Intent mIntent ;

    //For Image Attachment

    private Bitmap bitmap;
    private String file_name;

    Imageutils imageutils;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    FragmentPager adapter ;

    CirclePageIndicator indicator ;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ViewPager mViewPagerFragments ;
    TabLayout mTabLayout ;
    String path ;

    int RESULT_LOAD_IMAGE = 9 ;
    boolean taken = false ;
    boolean imgCapFlag = false ;

    AppCompatSpinner mSpinner ;

    private static final Integer[] IMAGES= {R.drawable.tyu,R.drawable.tyu,R.drawable.tyu,R.drawable.tyu};


    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("check","onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landingfragmentshopkeeper, container, false);

        Log.v("check","onCreateView");
        // Set title bar
        mSpinner = (AppCompatSpinner)v.findViewById(R.id.spinner_categories);
        String[] categories = {"category" , "category"} ;
        mSpinner.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,categories));
      //  mTabLayout.setupWithViewPager(mViewPagerFragments);

        imageutils =new Imageutils(getActivity());

        iv_attachment1=(ImageView)v.findViewById(R.id.img_selectedImage1);

        saveButton = (Button)v.findViewById(R.id.button_save);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });


        Button btn = (Button)v.findViewById(R.id.btn_select_image) ;

        Button btngallery = (Button)v.findViewById(R.id.btn_select_image_from_gallery) ;


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  imageutils.imagepicker(1);

                camera();
            }
        });

        btngallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  imageutils.imagepicker(1);

                gallery();
            }
        });
      /*  try {
            path = this.getArguments().getString("path");

            File image = new File(path, this.getArguments().getString("file_name"));
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);
            iv_attachment.setImageBitmap(bitmap);
        }

        catch (NullPointerException ne)
        {

        }*/
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v("check","onViewCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.v("check","onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("check","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("check","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("check","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("check","onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("check","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("check","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v("check","onDetach");
    }


    public void camera() {

        if (getContext().checkCallingOrSelfPermission("android.permission.CAMERA")== PackageManager.PERMISSION_DENIED)
        {
            Log.v("denied","check");
            //    EditingScreenFragmentPermissionsDispatcher.dispatchTakePictureIntentWithCheck(EditingScreenFragment.this);
            //this.showRationaleForCamera();
            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setTitle("Important Message").setMessage("You need to give camera permissions for this action");
            final AlertDialog dialog = builder.create();

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    final Intent in = new Intent();
                    in.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    in.addCategory(Intent.CATEGORY_DEFAULT);
                    in.setData(Uri.parse("package:" + getContext().getPackageName()));
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    in.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    getContext().startActivity(in);
                    //   dialog.dismiss();
                }
            });
            builder.show() ;
        }
        else {
            Log.v("allowed","check");
            dispatchTakePictureIntent();

        }

    }

    public void gallery()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        mIntent = data;
        Log.v("requestCode", requestCode + "");
        Log.v("resultCode", resultCode + "");

        switch (requestCode) {

            case 1888:


                Uri takenPhotoUri = getPhotoFileUri("pic.JPEG");
                // by this point we have the camera photo on disk


                String mPath = Environment.getExternalStorageDirectory() + File.separator + "/Camera/";
                //     Uri newUri = Uri.fromFile(new File(mPath));
                if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    //    Uri uriCrop =  handleCropResult(data);

                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        mBitMap = BitmapFactory.decodeFile(takenPhotoUri.getPath(), options);
                        //   CommonQuotesApplication.addBitmapToMemoryCache("bitmapToSend",mBitMap);
                    } catch (Exception ioe) {
                        ioe.printStackTrace();
                    }
                    iv_attachment1.setImageBitmap(mBitMap);
                    //mEditingImage.setImageBitmap(Bitmap.createScaledBitmap(mBitMap, mBitMap.getDensity(), (int) mBitMap.getDensity(), false));
                    //   bmp_current.setBitmap(mBitMap);


                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    // Calculate inSampleSize


                    options.inJustDecodeBounds = false;


                }


                break;


            case 1:

                if (resultCode == Activity.RESULT_OK && requestCode == 1) {
                    try {
                        uri = data.getData();
                    } catch (NullPointerException nep) {
                        // Crashlytics.logException(nep);
                    }

                    if (uri != null) {

                        try {

                            mBitMap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);


                            iv_attachment1.setImageBitmap(Bitmap.createScaledBitmap(mBitMap, 512, 512, false));

                        } catch (Exception exception) {
                            //Crashlytics.logException(exception);
                            throw new RuntimeException("Stub!");
                        }
                    } else {
                        Toast.makeText(getContext(), "can't get the image ", Toast.LENGTH_SHORT).show();
                    }


                }


                break;


        }
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

        Log.v("check","onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v("check","onCreate");
    }


    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            // File photoFile = null;

            try {


                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                /* uri = FileProvider.getUriForFile(getContext(),
                        photoFile.getAbsolutePath(),
                        photoFile);*/
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,getPhotoFileUri("pic.JPEG"));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getContext().getExternalFilesDir(Environment.getExternalStorageDirectory() + File.separator + "/Camera/");
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir     /* directory */

        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d("", "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public void uploadImage(){


        Call<Void> callImages = apiServiceActual.addProduct("","","","","","","","");
        callImages.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.v("checkAPICartItems","response_code"+response.isSuccessful());






                        /*Gson gson = new Gson();
                        allCategories.setCategory(response.body().getCategory());
                        //  allCategories.setMarket_items(response.body());
                        String allMarket = gson.toJson(allCategories);
                        getSharedPreferences("MYPrefs",MODE_PRIVATE).edit().putString("categories_all",allMarket).apply();*/
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("errorAPI","response_error"+"someErrorCategory");
            }
        });


    }
}
