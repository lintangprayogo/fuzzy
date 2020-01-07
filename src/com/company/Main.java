package com.company;

public class Main {

    public static void main(String[] args) {
          Fuzzy fuzzy = new Fuzzy();
          fuzzy.read("influencers.csv");
          fuzzy.fuzzification();
          fuzzy.Infrence();
          fuzzy.diFuzzyfication();
    }
}
