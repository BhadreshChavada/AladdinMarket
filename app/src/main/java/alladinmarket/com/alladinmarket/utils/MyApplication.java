package alladinmarket.com.alladinmarket.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by nmn on 2/5/17.
 */

public class MyApplication extends Application {


    public static  boolean sShopkeeper_flag = false ;

    public static  boolean authenticated = false ;


    public static void showToastMessage (Context context , String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();

    }

}
