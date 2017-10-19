package alladinmarket.com.alladinmarket.utils;

import alladinmarket.com.alladinmarket.network.pojo.LoginEntity.Data;
import alladinmarket.com.alladinmarket.network.pojo.LoginEntity.LoginObject;

/**
 * Created by nmn on 31/8/17.
 */

public class Profile {


    Data profileData ;

    public Data getLoginObject() {
        return profileData ;
    }

    public void setLoginObject(Data profileData) {
        this.profileData = profileData;
    }
}
