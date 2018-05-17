
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class DescriptionFields implements Serializable {

    @SerializedName("secondary")
    @Expose
    private List<Secondary> secondary = null;
    @SerializedName("primary")
    @Expose
    private List<Primary> primary = null;
    private final static long serialVersionUID = -5332683913815512005L;

    public List<Secondary> getSecondary() {
        return secondary;
    }

    public void setSecondary(List<Secondary> secondary) {
        this.secondary = secondary;
    }

    public List<Primary> getPrimary() {
        return primary;
    }

    public void setPrimary(List<Primary> primary) {
        this.primary = primary;
    }
}