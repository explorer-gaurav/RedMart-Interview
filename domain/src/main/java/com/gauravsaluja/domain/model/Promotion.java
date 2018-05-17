
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class Promotion implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("savings_text")
    @Expose
    private String savingsText;
    @SerializedName("promo_label")
    @Expose
    private String promoLabel;
    @SerializedName("current_product_group_id")
    @Expose
    private Integer currentProductGroupId;
    @SerializedName("products")
    @Expose
    private List<PromotionProduct> products = null;
    private final static long serialVersionUID = 7396167109836434196L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSavingsText() {
        return savingsText;
    }

    public void setSavingsText(String savingsText) {
        this.savingsText = savingsText;
    }

    public String getPromoLabel() {
        return promoLabel;
    }

    public void setPromoLabel(String promoLabel) {
        this.promoLabel = promoLabel;
    }

    public Integer getCurrentProductGroupId() {
        return currentProductGroupId;
    }

    public void setCurrentProductGroupId(Integer currentProductGroupId) {
        this.currentProductGroupId = currentProductGroupId;
    }

    public List<PromotionProduct> getProducts() {
        return products;
    }

    public void setProducts(List<PromotionProduct> products) {
        this.products = products;
    }
}