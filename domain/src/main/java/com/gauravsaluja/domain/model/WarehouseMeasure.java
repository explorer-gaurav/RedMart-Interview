
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class WarehouseMeasure implements Serializable {

    @SerializedName("vol_metric")
    @Expose
    private String volMetric;
    @SerializedName("wt")
    @Expose
    private Double wt;
    @SerializedName("wt_metric")
    @Expose
    private String wtMetric;
    @SerializedName("l")
    @Expose
    private Integer l;
    @SerializedName("w")
    @Expose
    private Integer w;
    @SerializedName("h")
    @Expose
    private Integer h;
    private final static long serialVersionUID = 7610646096990319256L;

    public String getVolMetric() {
        return volMetric;
    }

    public void setVolMetric(String volMetric) {
        this.volMetric = volMetric;
    }

    public Double getWt() {
        return wt;
    }

    public void setWt(Double wt) {
        this.wt = wt;
    }

    public String getWtMetric() {
        return wtMetric;
    }

    public void setWtMetric(String wtMetric) {
        this.wtMetric = wtMetric;
    }

    public Integer getL() {
        return l;
    }

    public void setL(Integer l) {
        this.l = l;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }
}