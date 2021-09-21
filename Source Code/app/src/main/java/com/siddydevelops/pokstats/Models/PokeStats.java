package com.siddydevelops.pokstats.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PokeStats {

//    private String effort;
//
//    public String getEffort() {
//        return effort;
//    }
//
//    public void setEffort(String effort) {
//        this.effort = effort;
//    }

    @SerializedName("base_stat")
    @Expose
    private Integer baseStat;
//    @SerializedName("effort")
//    @Expose
//    private Integer effort;
//    @SerializedName("stat")
//    @Expose
//    private Stat stat;

    public Integer getBaseStat() {
        return baseStat;
    }

    public void setBaseStat(Integer baseStat) {
        this.baseStat = baseStat;
    }

//    public Integer getEffort() {
//        return effort;
//    }
//
//    public void setEffort(Integer effort) {
//        this.effort = effort;
//    }

//    public Stat getStat() {
//        return stat;
//    }
//
//    public void setStat(Stat stat) {
//        this.stat = stat;
//    }


//    @Override
//    public String toString() {
//        return "PokeStats{" +
//                "baseStat=" + baseStat +
//                ", effort=" + effort +
//                ", stat=" + stat.toString() +
//                '}';
//    }

    @Override
    public String toString() {
        return baseStat.toString();
    }

}
