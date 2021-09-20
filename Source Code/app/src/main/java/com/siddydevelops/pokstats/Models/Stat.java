package com.siddydevelops.pokstats.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    @Override
//    public String toString() {
//        return "Stat{" +
//                "name='" + name + '\'' +
//                ", url='" + url + '\'' +
//                '}';
//    }

}
