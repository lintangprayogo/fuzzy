package com.company;

import java.util.ArrayList;

public class InfluencerFuzzyInput {
    private String id;
    private  ArrayList<Cluster> folowerCountClusters = new ArrayList<>();
    private  ArrayList<Cluster> engagementRateClusters = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Cluster> getFolowerCountClusters() {
        return folowerCountClusters;
    }

    public ArrayList<Cluster> getEngagementRateClusters() {
        return engagementRateClusters;
    }
}
