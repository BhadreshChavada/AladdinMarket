
package alladinmarket.com.alladinmarket.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryItem {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("subcategoryname")
    @Expose
    private String subcategoryname;
    @SerializedName("category")
    @Expose
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubcategoryname() {
        return subcategoryname;
    }

    public void setSubcategoryname(String subcategoryname) {
        this.subcategoryname = subcategoryname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
