package com.example.kurs;

import com.example.kurs.Sensors.PresenceSensor;
import com.example.kurs.Sensors.TemperatureSensor;
import com.example.kurs.Valve;

public class Room {
    private String name;
    private int temperature;
    private Valve valve;
    private TemperatureSensor temperatureSensor;
    private PresenceSensor presenceSensor;
    private int workTemperature;
    private int waitTemperature;
    private int M;
    private double area;
    private double constant;

    public Room(String name, double area, double constant) {
        this.name = name;
        this.area = area;
        this.constant = constant;
        this.temperature = 20;
        this.valve = new Valve(name);
        this.temperatureSensor = new TemperatureSensor(name);
        this.presenceSensor = new PresenceSensor(name);
    }

    public void setWorkTemperature(int workTemperature) {
        this.workTemperature = workTemperature;
        this.waitTemperature = workTemperature - M;
    }

    public void setM(int M) {
        this.M = M;
        this.waitTemperature = workTemperature - M;
    }

    public void updateTemperature(String timeOfDay) {
        int k = valve.getPosition() == 0 ? 0 : valve.getPosition() == 1 ? 2 : 5;
        double r = constant * k;
        double coefficient = getTemperatureChangeCoefficient(timeOfDay);
        temperature += (r - temperature) * coefficient; // Линейное изменение температуры
    }

    private double getTemperatureChangeCoefficient(String timeOfDay) {
        // Коэффициент изменения температуры в зависимости от времени суток
        switch (timeOfDay) {
            case "Ночь":
                return 0.005;
            case "Утро":
                return 0.01;
            case "День":
                return 0.015;
            case "Вечер":
                return 0.01;
            default:
                return 0.01;
        }
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWorkTemperature() {
        return workTemperature;
    }

    public int getWaitTemperature() {
        return waitTemperature;
    }

    public boolean isPresenceDetected() {
        return presenceSensor.isPresenceDetected();
    }

    public void setValvePosition(int position) {
        valve.setPosition(position);
    }

    public int getValvePosition() {
        return valve.getPosition();
    }

    public String getName() {
        return name;
    }

    public double getArea() {
        return area;
    }

    public double getConstant() {
        return constant;
    }
}