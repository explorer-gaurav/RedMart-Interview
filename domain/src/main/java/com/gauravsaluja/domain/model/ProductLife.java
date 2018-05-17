
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class ProductLife implements Serializable {

    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("metric")
    @Expose
    private String metric;
    @SerializedName("is_minimum")
    @Expose
    private Boolean isMinimum;
    @SerializedName("time_including_delivery")
    @Expose
    private Integer timeIncludingDelivery;
    private final static long serialVersionUID = -5018610088967321936L;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Boolean getIsMinimum() {
        return isMinimum;
    }

    public void setIsMinimum(Boolean isMinimum) {
        this.isMinimum = isMinimum;
    }

    public Integer getTimeIncludingDelivery() {
        return timeIncludingDelivery;
    }

    public void setTimeIncludingDelivery(Integer timeIncludingDelivery) {
        this.timeIncludingDelivery = timeIncludingDelivery;
    }
}