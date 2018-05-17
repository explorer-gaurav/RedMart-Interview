
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class Slot implements Serializable {

    @SerializedName("from")
    @Expose
    private String from;
    private final static long serialVersionUID = 6909972452788312056L;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}