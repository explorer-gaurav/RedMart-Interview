
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class Warehouse implements Serializable {

    @SerializedName("measure")
    @Expose
    private WarehouseMeasure measure;
    private final static long serialVersionUID = -5908643484135416636L;

    public WarehouseMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(WarehouseMeasure measure) {
        this.measure = measure;
    }
}