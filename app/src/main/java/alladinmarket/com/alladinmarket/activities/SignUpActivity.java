package alladinmarket.com.alladinmarket.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.network.ApiClient;
import alladinmarket.com.alladinmarket.network.ApiInterface;
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

//        try {
//            uploadFile();
//        }
//            catch (NullPointerException ex)
//            {
//                ex.printStackTrace();
//            }
//        @Field("username") String username, @Field("email") String email,
//        @Field("password") String password, @Field("contact") String contact,
//        @Part("user_img") MultipartBody.Part file)

//        idProof = new TypedFile("multipart/form-data", new File(pathIdProofPic));
//        files.put("idProof", idProof);


        File file = new File(filePath);
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part fileData =
                MultipartBody.Part.createFormData("user_img", filePath, requestFile);





        Call<Void> call = apiService.register( RequestBody.create(
                MediaType.parse("multipart/form-data"), mName.getText().toString()),
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), mEmail.getText().toString()),
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), mPassword.getText().toString()),
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), mContactNo.getText().toString()), fileData);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.v("response", "error" + response.errorBody() + response.code() + response.message() + response.isSuccessful()
                );

                if (response.message().compareToIgnoreCase("ok") == 0) {
                    Intent i = new Intent(SignUpActivity.this, DrawerActivity.class);
      /*  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/


                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.v("reachhere", "yesagain");
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
//        File photoFile = null ;
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
//            // Create the File where the photo should go
//            // File photoFile = null;
//
//            try {
//
//
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//
//            }
//
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                /* uri = FileProvider.getUriForFile(getContext(),
//                        photoFile.getAbsolutePath(),
//                        photoFile);*/
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,getPhotoFileUri("pic.JPEG"));
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }

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


}
