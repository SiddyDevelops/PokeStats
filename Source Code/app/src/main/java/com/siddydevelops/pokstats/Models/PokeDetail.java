package com.siddydevelops.pokstats.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PokeDetail {

    private int id;
    private String name;
    private String height;
    private String weight;

    @SerializedName("types")
    @Expose
    private List<PokeType> types = null;

    @SerializedName("stats")
    @Expose
    private List<PokeStats> stats = null;

    public List<PokeType> getTypes() {
        return types;
    }

    public void setTypes(List<PokeType> types) {
        this.types = types;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
