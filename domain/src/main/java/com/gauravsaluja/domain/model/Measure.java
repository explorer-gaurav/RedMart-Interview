
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class Measure implements Serializable {

    @SerializedName("wt_or_vol")
    @Expose
    private String wtOrVol;
    @SerializedName("size")
    @Expose
    private Integer size;
    private final static long serialVersionUID = -7275069044992001811L;

    public String getWtOrVol() {
        return wtOrVol;
    }

    public void setWtOrVol(String wtOrVol) {
        this.wtOrVol = wtOrVol;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}