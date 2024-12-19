package com.example.kurs;

public class FuelConsumption {
    private double totalConsumption;

    public FuelConsumption() {
        this.totalConsumption = 0;
    }

    public void addConsumption(double consumption) {
        totalConsumption += consumption;
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }
}