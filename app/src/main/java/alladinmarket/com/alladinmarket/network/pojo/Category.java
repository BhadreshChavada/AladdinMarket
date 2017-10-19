
package alladinmarket.com.alladinmarket.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("term_id")
    @Expose
    private Integer term_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("term_group")
    @Expose
    private Integer term_group;
    @SerializedName("term_taxonomy_id")
    @Expose
    private Integer term_taxonomy_id;
    @SerializedName("taxonomy")
    @Expose
    private String taxonomy;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("parent")
    @Expose
    private Integer parent;
    @SerializedName("filter")
    @Expose
    private String filter;

    public Integer getTerm_id() {
        return term_id;
    }

    public void setTerm_id(Integer term_id) {
        this.term_id = term_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getTerm_group() {
        return term_group;
    }

    public void setTerm_group(Integer term_group) {
        this.term_group = term_group;
    }

    public Integer getTerm_taxonomy_id() {
        return term_taxonomy_id;
    }

    public void setTerm_taxonomy_id(Integer term_taxonomy_id) {
        this.term_taxonomy_id = term_taxonomy_id;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

}
