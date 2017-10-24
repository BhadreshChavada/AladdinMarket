package alladinmarket.com.alladinmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.network.pojo.LoginEntity.LoginObject;
import alladinmarket.com.alladinmarket.utils.MyApplication;
import alladinmarket.com.alladinmarket.utils.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static alladinmarket.com.alladinmarket.services.MyService.apiServiceActual;

public class LoginActivity extends AppCompatActivity {

    private TextView signUp;
    private EditText password;
    private EditText email;


    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        email = (EditText) findViewById(R.id.edit_text_email);
        /*MaskedFormatter formatter = new MaskedFormatter("*************");*/

        password = (EditText) findViewById(R.id.edit_text_passowrd);
        // password.addTextChangedListener(new MaskedWatcher(formatter, password));


        //password.addTextChangedListener(new MaskWatcher("********"));
        signUp = (TextView) findViewById(R.id.tv_sign_up);
        setSupportActionBar(mToolbar);

        String s = "naman";
        String a = s;
        a.replace("m", "n");
        Log.v("check", s);
        s = s + "uiui";
        s = s.replace("u", "i");
        Log.v("check", s);
        Log.v("checka", a);
    }


    public void login(View v) {


        Log.v("reachhere", email.getText().toString() + "correct");
        if (email.getText().toString().compareToIgnoreCase("shopkeeper@gmail.com") == 0 &&
                password.getText().toString().compareToIgnoreCase("12345678") == 0) {
            MyApplication.sShopkeeper_flag = true;
            Intent i = new Intent(LoginActivity.this, DrawerActivity.class);
            startActivity(i);

        } else {
            Call<LoginObject> call = apiServiceActual.login(email.getText().toString().trim(), password.getText().toString().trim());
            call.enqueue(new Callback<LoginObject>() {
                @Override
                public void onResponse(Call<LoginObject> call, Response<LoginObject> response) {
                    Log.v("reachheresignup", response.code() + "" + response.body().getStatus() + response.message());
                    if (response.body().getStatus().compareToIgnoreCase("true") == 0) {

                        LoginObject loginObject = new LoginObject();
                        Profile profile = new Profile();
                        profile.setLoginObject(response.body().getData());

                        Gson gson = new Gson();
                        //  allMarkets.setMarket_items(response.body());
                        String profileDetail = gson.toJson(profile);
                        getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().putString("profile_detail", profileDetail).apply();
                        getSharedPreferences("MYPrefs", MODE_PRIVATE).edit().putString("UserId", String.valueOf(profile.getLoginObject().getID()));



                    /*Profile profile1 =
                            gson.fromJson(getSharedPreferences("MYPrefs", MODE_PRIVATE).getString("profile_detail", ""),
                                    Profile.class) ;

                    Log.v("checkDetails",profile1.getLoginObject().getUser_email()+"checkDetails"+profile1.getLoginObject()) ;
*/


                        Intent i = new Intent(LoginActivity.this, DrawerActivity.class);
                        startActivity(i);


                    } else {

                        Toast.makeText(LoginActivity.this, "Please enter correct credentials", Toast.LENGTH_LONG).show();
                    }
                }
      /*  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/


                @Override
                public void onFailure(Call<LoginObject> call, Throwable t) {
                    t.printStackTrace();
                    t.getCause();
                    t.getMessage();
                    t.getLocalizedMessage();
                    Toast.makeText(LoginActivity.this, "Network Server Error", Toast.LENGTH_LONG).show();

                }
            });

        }

    }


    public void signUp(View v) {


        Log.v("reachhere", email.getText().toString() + "correct");
        if (email.getText().toString().compareToIgnoreCase("shopkeeper@gmail.com") == 0/*&& password.getText().toString()=="12345678"*/) {
            MyApplication.sShopkeeper_flag = true;
        }

        Log.v("reachhere", "yesagain");
        Intent i = new Intent(this, SelectRole.class);
       /* i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);*/
        startActivity(i);


    }
}
