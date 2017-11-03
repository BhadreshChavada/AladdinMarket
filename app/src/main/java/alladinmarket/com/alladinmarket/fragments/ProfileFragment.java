package alladinmarket.com.alladinmarket.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import alladinmarket.com.alladinmarket.Manifest;
import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.activities.LoginActivity;
import alladinmarket.com.alladinmarket.adapters.FragmentPager;
import alladinmarket.com.alladinmarket.utils.Profile;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiService;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by nmn on 3/4/17.
 */

public class ProfileFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 10;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 11;
    private static final int CAMERA_REQUEST = 12;
    private static final int GALLERYCONSTANT = 13;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    FragmentPager adapter;

    CirclePageIndicator indicator;


    EditText name, Email, contact;
    String uID;
    Button btnSave;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ViewPager mViewPagerFragments;
    TabLayout mTabLayout;
    ImageView profile_image;

    private static final Integer[] IMAGES = {R.drawable.tyu, R.drawable.tyu, R.drawable.tyu, R.drawable.tyu};
    String[] tabTitles = {"BEST OFFERS", "CATEGORIES", "TOP STORIES"};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private String filePath = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        // Set title bar
        name = (EditText) v.findViewById(R.id.edit_name);
        Email = (EditText) v.findViewById(R.id.edit_text_email); // edit_text_contact_no
        contact = (EditText) v.findViewById(R.id.edit_text_contact_no);
        profile_image = (ImageView) v.findViewById(R.id.profile_image);
        btnSave = (Button) v.findViewById(R.id.btn_save);

        Log.v("chek", name.getText() + "");


        getProfileDetails();


        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                HashMap<String, String> map = new HashMap<>();
                map.put("uid",uID);
                map.put("username", name.getText().toString());
                map.put("email", Email.getText().toString());
                map.put("contact", contact.getText().toString());

                Call<Void> call;
                if (filePath != null) {

                    File file = new File(filePath);
                    // create RequestBody instance from file
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    MultipartBody.Part fileData =
                            MultipartBody.Part.createFormData("user_img", filePath, requestFile);
                    call = apiService.updateProfile(map, fileData);
                } else {
                    call = apiService.updateProfile(map);
                }

                Log.d("URL", String.valueOf(call.request().url()));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.v("response", "error" + response.errorBody() + response.code() + response.message() + response.isSuccessful()
                        );

                        if (response.message().compareToIgnoreCase("ok") == 0) {
                            Intent i = new Intent(getActivity(), LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.v("reachhere", "yesagain");
                    }
                });

            }
        });

        return v;
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result=Utility.checkPermission(MainActivity.this);
                if (items[item].equals("Take Photo")) {
                    // userChoosenTask="Take Photo";

                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    //   userChoosenTask="Choose from Library";

                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }

    private void cameraIntent() {


        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERYCONSTANT);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //mIntent = data;

        Bitmap mBitMap = null;
        Log.v("requestCode", requestCode + "");
        Log.v("resultCode", resultCode + "");

        switch (requestCode) {



            case GALLERYCONSTANT:
                // mFlag = true;
                Uri uri = null;

                if (resultCode == Activity.RESULT_OK ) {
                    try {
                        uri = data.getData();
                    } catch (NullPointerException nep) {
                        // Crashlytics.logException(nep);
                    }

                    if (uri != null) {

                        try {

                            mBitMap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);


                            profile_image.setImageBitmap(Bitmap.createScaledBitmap(mBitMap, 1366, 768, false));

                            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                            Uri tempUri = getImageUri(getActivity(), mBitMap);

                            // CALL THIS METHOD TO GET THE ACTUAL PATH
                            File finalFile = new File(getRealPathFromURI(tempUri));

                            filePath = finalFile.getAbsolutePath();

                            Log.d("FilePath1111", filePath);
                        } catch (Exception exception) {
                            // Crashlytics.logException(exception);
                            throw new RuntimeException("Stub!");
                        }
                    } else {
                        Toast.makeText(getActivity(), "can't get the image ", Toast.LENGTH_SHORT).show();
                    }


                }


                break;

            case CAMERA_REQUEST:
                if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    profile_image.setImageBitmap(photo);


                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getActivity(), photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));

                    filePath = finalFile.getAbsolutePath();

                }

            default:


        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }



    public void getProfileDetails() {


        Gson gs = new Gson();
        Profile profile1 =
                gs.fromJson(getContext().getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("profile_detail", ""),
                        Profile.class);
        uID = String.valueOf(profile1.getLoginObject().getID());
        if (profile1.getLoginObject().getUserNicename() != null)
            name.setText(profile1.getLoginObject().getUserNicename());
        else
            name.setHint("Full Name");

        if (profile1.getLoginObject().getUserEmail() != null)
            Email.setText(profile1.getLoginObject().getUserEmail());
        else
            Email.setHint("Email address");

        try {
            contact.setText(profile1.getLoginObject().getContactNo());
        } catch (NullPointerException e) {
            contact.setHint("Enter mobile no.");
        }

        if (profile1.getLoginObject().getProfileImg() != null)
            Picasso.with(getActivity()).load(profile1.getLoginObject().getProfileImg()).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(profile_image);

        Log.v("checkDetails", profile1.getLoginObject().getContactNo() + "checkDetails");


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }


    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_READ_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant

                return;
            }

            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant

                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
}
