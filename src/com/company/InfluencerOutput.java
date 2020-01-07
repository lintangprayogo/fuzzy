package com.company;

public class InfluencerOutput {
    private String id;
    private double result;

    public InfluencerOutput(String id, double result) {
        this.id = id;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return
                "{id = " + id + ','+
                "result =" + result +
                '}';
    }
}
