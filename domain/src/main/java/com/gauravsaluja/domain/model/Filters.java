
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class Filters implements Serializable {

    @SerializedName("mfr_name")
    @Expose
    private String mfrName;
    @SerializedName("trending_frequency")
    @Expose
    private Integer trendingFrequency;
    @SerializedName("is_organic")
    @Expose
    private Integer isOrganic;
    @SerializedName("country_of_origin")
    @Expose
    private String countryOfOrigin;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("brand_uri")
    @Expose
    private String brandUri;
    @SerializedName("frequency")
    @Expose
    private Integer frequency;
    @SerializedName("festive_bbq")
    @Expose
    private String festiveBbq;
    @SerializedName("potluck")
    @Expose
    private String potluck;
    private final static long serialVersionUID = -7853844739958265754L;

    public String getMfrName() {
        return mfrName;
    }

    public void setMfrName(String mfrName) {
        this.mfrName = mfrName;
    }

    public Integer getTrendingFrequency() {
        return trendingFrequency;
    }

    public void setTrendingFrequency(Integer trendingFrequency) {
        this.trendingFrequency = trendingFrequency;
    }

    public Integer getIsOrganic() {
        return isOrganic;
    }

    public void setIsOrganic(Integer isOrganic) {
        this.isOrganic = isOrganic;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandUri() {
        return brandUri;
    }

    public void setBrandUri(String brandUri) {
        this.brandUri = brandUri;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getFestiveBbq() {
        return festiveBbq;
    }

    public void setFestiveBbq(String festiveBbq) {
        this.festiveBbq = festiveBbq;
    }

    public String getPotluck() {
        return potluck;
    }

    public void setPotluck(String potluck) {
        this.potluck = potluck;
    }
}