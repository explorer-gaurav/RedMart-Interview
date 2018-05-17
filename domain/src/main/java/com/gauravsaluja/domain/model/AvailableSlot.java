
package com.gauravsaluja.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 */

public class AvailableSlot implements Serializable {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("slots")
    @Expose
    private List<Slot> slots = null;
    private final static long serialVersionUID = 2741182868445851335L;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }
}