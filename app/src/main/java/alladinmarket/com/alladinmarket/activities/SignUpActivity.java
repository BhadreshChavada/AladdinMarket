package alladinmarket.com.alladinmarket.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import alladinmarket.com.alladinmarket.Manifest;
import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.network.ApiClient;
import alladinmarket.com.alladinmarket.network.ApiInterface;
import alladinmarket.com.alladinmarket.network.pojo.MainRes;
import alladinmarket.com.alladinmarket.network.pojo.ServerResponse;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiService;

public class SignUpActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 15;
    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 20;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mContactNo;
    private EditText mName;
    private EditText mConfirmPassword;
    private CircleImageView mProfileImage;
    private String pathForUpload = null;
    ProgressDialog progressDialog = null;
    String filePath = null;
    static final int REQUEST_TAKE_PHOTO = 1888;
    private static final int CAMERA_REQUEST = 1000;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        mEmail = (EditText) findViewById(R.id.edit_text_email);
        mPassword = (EditText) findViewById(R.id.edit_text_passowrd);
        mContactNo = (EditText) findViewById(R.id.edit_text_contact_no);
        mName = (EditText) findViewById(R.id.edit_name);
        mConfirmPassword = (EditText) findViewById(R.id.edit_text_comfpassowrd);
        mProfileImage = (CircleImageView) findViewById(R.id.profile_image);

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }


    public void login(View v) {


        HashMap<String, String> map = new HashMap<>();
        map.put("username", mName.getText().toString());
        map.put("email", mEmail.getText().toString());
        map.put("password", mPassword.getText().toString());
        map.put("contact", mContactNo.getText().toString());
        map.put("role", "user");

        Call<MainRes> call;
        if (filePath != null) {
            File file = new File(filePath);
            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part fileData =
                    MultipartBody.Part.createFormData("user_img", filePath, requestFile);
            call = apiService.register(map, fileData);
        } else {
            call = apiService.register(map);
        }

        call.enqueue(new Callback<MainRes>() {
            @Override
            public void onResponse(Call<MainRes> call, Response<MainRes> response) {
                MainRes obj = response.body();
                if (obj.getStatus().equalsIgnoreCase("true")) {
                    Intent i = new Intent(SignUpActivity.this, DrawerActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                } else {
                    Toast.makeText(SignUpActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainRes> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
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

    private void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

    private void cameraIntent() {


        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getContext().getExternalFilesDir(Environment.getExternalStorageDirectory() + File.separator + "/Camera/");
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir     /* directory */

        );

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = image.getAbsolutePath();
        pathForUpload = mCurrentPhotoPath;

        return image;
    }

    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), "");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
                Log.d("", "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //mIntent = data;

        Bitmap mBitMap = null;
        Log.v("requestCode", requestCode + "");
        Log.v("resultCode", resultCode + "");

        switch (requestCode) {

            case 1888:

                String mPath;
                Uri takenPhotoUri = getPhotoFileUri("pic.JPEG");
                // by this point we have the camera photo on disk


                mPath = Environment.getExternalStorageDirectory() + File.separator + "/Camera/";
                pathForUpload = mPath;

                //     Uri newUri = Uri.fromFile(new File(mPath));
                if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
                    //    Uri uriCrop =  handleCropResult(data);

                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        mBitMap = BitmapFactory.decodeFile(takenPhotoUri.getPath(), options);
                        pathForUpload = takenPhotoUri.getPath();
                    } catch (Exception ioe) {
                        ioe.printStackTrace();
                    }
                    mProfileImage.setImageBitmap(mBitMap);

                    //  pathForUpload = takenPhotoUri.getPath() ;

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // Calculate inSampleSize


                    options.inJustDecodeBounds = false;


                }


                break;

            /*case 69:
                if (requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
                    uri = UCrop.getOutput(data);
                    if (uri != null) {
                        mEditingImage.setImageURI(uri);
                    }
                    break;
                }
                break;*/

            case 1:
                // mFlag = true;
                Uri uri = null;

                if (resultCode == Activity.RESULT_OK && requestCode == 1) {
                    try {
                        uri = data.getData();
                    } catch (NullPointerException nep) {
                        // Crashlytics.logException(nep);
                    }

                    if (uri != null) {

                        try {

                            mBitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);


                            mProfileImage.setImageBitmap(Bitmap.createScaledBitmap(mBitMap, 1366, 768, false));

                            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                            Uri tempUri = getImageUri(getApplicationContext(), mBitMap);

                            // CALL THIS METHOD TO GET THE ACTUAL PATH
                            File finalFile = new File(getRealPathFromURI(tempUri));

                            filePath = finalFile.getAbsolutePath();

                            Log.d("FilePath1111", filePath);
                        } catch (Exception exception) {
                            // Crashlytics.logException(exception);
                            throw new RuntimeException("Stub!");
                        }
                    } else {
                        Toast.makeText(this, "can't get the image ", Toast.LENGTH_SHORT).show();
                    }


                }


                break;

            case CAMERA_REQUEST:
                if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mProfileImage.setImageBitmap(photo);


                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), photo);

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
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void uploadFile() {

        progressDialog.show();

        File file = new File(pathForUpload);

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        ApiInterface getResponse = ApiClient.getClient().create(ApiInterface.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.v("Not uploaded", t.getMessage());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_READ_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant

                return;
            }

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
