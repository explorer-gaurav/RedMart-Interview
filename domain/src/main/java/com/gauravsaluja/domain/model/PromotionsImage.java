
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class PromotionsImage implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    private final static long serialVersionUID = -2871307216328185679L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}