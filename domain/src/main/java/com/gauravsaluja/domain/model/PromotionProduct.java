
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class PromotionProduct implements Serializable {

    @SerializedName("images")
    @Expose
    private List<PromotionsImage> images = null;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("min_qty")
    @Expose
    private Integer minQty;
    private final static long serialVersionUID = -5400504023630511293L;

    public List<PromotionsImage> getImages() {
        return images;
    }

    public void setImages(List<PromotionsImage> images) {
        this.images = images;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getMinQty() {
        return minQty;
    }

    public void setMinQty(Integer minQty) {
        this.minQty = minQty;
    }
}