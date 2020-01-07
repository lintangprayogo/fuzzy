package com.company;

public class InfluencerInput {
    private String id;
    private Double folowerCount;
    private double engagementRate;

    public InfluencerInput(String id, double folowerCount, double engagementRate) {
        this.id = id;
        this.folowerCount = folowerCount;
        this.engagementRate = engagementRate;
    }

    public String getId() {
        return id;
    }

    public Double getFolowerCount() {
        return folowerCount;
    }

    public double getEngagementRate() {
        return engagementRate;
    }

    @Override
    public String toString() {
        return "Influencer{" +
                "id='" + id + '\'' +
                ", folowerCount=" + folowerCount +
                ", engagementRate=" + engagementRate +
                '}';
    }
}
