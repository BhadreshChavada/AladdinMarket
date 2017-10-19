
package alladinmarket.com.alladinmarket.network.pojo.ProductDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("post_content")
    @Expose
    private String post_content;
    @SerializedName("post_title")
    @Expose
    private String post_title;
    @SerializedName("post_excerpt")
    @Expose
    private String post_excerpt;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("seller_name")
    @Expose
    private String seller_name;
    @SerializedName("post_image")
    @Expose
    private String post_image;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("regular_price")
    @Expose
    private String regular_price;
    @SerializedName("gallery")
    @Expose
    private List<Gallery> gallery = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_excerpt() {
        return post_excerpt;
    }

    public void setPost_excerpt(String post_excerpt) {
        this.post_excerpt = post_excerpt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(String regular_price) {
        this.regular_price = regular_price;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

}
