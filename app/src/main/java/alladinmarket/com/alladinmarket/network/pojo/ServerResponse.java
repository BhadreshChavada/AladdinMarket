package alladinmarket.com.alladinmarket.network.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nmn on 31/5/17.
 */

public class ServerResponse {
    // variable name should be same as in the json response from php    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

   public String getMessage() {
        return message;
    }

   public boolean getSuccess() {
        return success;
    }
}