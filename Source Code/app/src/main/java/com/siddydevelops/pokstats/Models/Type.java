package com.siddydevelops.pokstats.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
