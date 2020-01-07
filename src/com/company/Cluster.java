package com.company;

public class Cluster {
    private String label;
    private double value;

    public Cluster(String label, double value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{"+label+":"+value+"}";
    }
}
