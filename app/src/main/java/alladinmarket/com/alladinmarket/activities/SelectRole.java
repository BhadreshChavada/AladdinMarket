package alladinmarket.com.alladinmarket.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.utils.MyApplication;

public class SelectRole extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
    }



    public  void  startSignUpShopkeeper (View v)

    {
        MyApplication.sShopkeeper_flag =true ;

        Intent i = new Intent(SelectRole.this,SignUpShopKeepr.class);
        startActivity(i);
    }


    public  void  startSignUpUser (View v)

    {
        MyApplication.sShopkeeper_flag =false ;
        Intent i = new Intent(SelectRole.this,SignUpActivity.class);
        startActivity(i);
    }
}
