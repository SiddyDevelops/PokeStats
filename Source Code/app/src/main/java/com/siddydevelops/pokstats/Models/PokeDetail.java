package com.siddydevelops.pokstats.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokeDetail {

    private String name;
    private String height;
    private String weight;

    @SerializedName("stats")
    @Expose
    private List<PokeStats> stats = null;

    public List<PokeStats> getStats() {
        return stats;
    }

    public void setStats(List<PokeStats> stats) {
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}