package com.company;

import java.util.ArrayList;

public class InfluencerInference {
    private String id;
    private  ArrayList<Cluster> scoreClusters = new ArrayList<>();
    private  ArrayList<Cluster> folowerCountClusters = new ArrayList<>();
    private  ArrayList<Cluster> engagementRateClusters = new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Cluster> getScoreClusters() {
        return scoreClusters;
    }

    public ArrayList<Cluster> getFolowerCountClusters() {
        return folowerCountClusters;
    }

    public ArrayList<Cluster> getEngagementRateClusters() {
        return engagementRateClusters;
    }
}
