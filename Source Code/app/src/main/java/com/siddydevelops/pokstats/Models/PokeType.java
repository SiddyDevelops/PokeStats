package com.siddydevelops.pokstats.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokeType {

    @SerializedName("slot")
    @Expose
    private String slot;

    @SerializedName("type")
    @Expose
    private Type type = null;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return type.toString();
    }

}
