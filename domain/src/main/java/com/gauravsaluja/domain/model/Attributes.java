
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class Attributes implements Serializable {

    @SerializedName("dag")
    @Expose
    private List<Object> dag = null;
    private final static long serialVersionUID = 3453943663256805368L;

    public List<Object> getDag() {
        return dag;
    }

    public void setDag(List<Object> dag) {
        this.dag = dag;
    }
}