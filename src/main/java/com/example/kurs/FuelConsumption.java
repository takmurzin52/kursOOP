package com.example.kurs;

public class FuelConsumption {
    private double totalConsumption;

    public FuelConsumption() {
        this.totalConsumption = 0;
    }

    public void addConsumption(double consumption) {
        if (consumption >= 0) {  // Проверка на отрицательные значения
            totalConsumption += consumption;
        } else {
            System.out.println("Ошибка: расход топлива не может быть отрицательным.");
        }
    }

    public double getTotalConsumption() {
        return totalConsumption;
    }

    public void resetConsumption() {
        totalConsumption = 0;
    }
}