package alladinmarket.com.alladinmarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import alladinmarket.com.alladinmarket.R;
import alladinmarket.com.alladinmarket.services.MyService;


public class MainActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, MyService.class);
        startService(i);

        RelativeLayout SplashScreenLayout  = (RelativeLayout)findViewById(R.id.activity_main);
        Intent intent = new Intent(this, LoginActivity.class);



        Animation animFadein = AnimationUtils.loadAnimation(this,R.anim.fade_in
        );



        SplashScreenLayout.startAnimation(animFadein);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
