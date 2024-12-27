package com.example.kurs;

import com.example.kurs.Sensors.PresenceSensor;
import com.example.kurs.Sensors.TemperatureSensor;

public class Room {
    private final String name;
    private final double area;
    private final double constant;
    private int temperature;
    private final Valve valve;
    private final TemperatureSensor temperatureSensor;
    private final PresenceSensor presenceSensor;
    private int workTemperature;
    private int waitTemperature;
    private int M;
    private WeeklySchedule schedule; // Индивидуальное расписание

    public Room(String name, double area, double constant, int minTemperature, int maxTemperature, WeeklySchedule schedule) {
        this.name = name;
        this.area = area;
        this.constant = constant;
        this.temperature = 20;
        this.valve = new Valve(name, 0);
        this.temperatureSensor = new TemperatureSensor(name, minTemperature, maxTemperature);
        this.presenceSensor = new PresenceSensor(name, schedule);
        this.schedule = new WeeklySchedule(); // Создаём индивидуальное расписание
    }

    public WeeklySchedule getSchedule() {
        return schedule;
    }

    public void setWorkTemperature(int workTemperature) {
        this.workTemperature = workTemperature;
        calculateWaitTemperature();
    }

    public void setM(int M) {
        this.M = M;
        calculateWaitTemperature();
    }

    public void updateTemperature(String timeOfDay) {
        int k = getValveHeatCoefficient();
        double r = constant * k;
        double coefficient = getTemperatureChangeCoefficient(timeOfDay);
        temperature += (int) ((r - temperature) * coefficient); // Линейное изменение температуры
    }

    // Коэффициент передачи тепла от клапана
    private int getValveHeatCoefficient() {
        switch (valve.getPosition()) {
            case 1: return 2; // Полуоткрыт
            case 2: return 5; // Открыт
            default: return 0; // Закрыт
        }
    }

    private double getTemperatureChangeCoefficient(String timeOfDay) {
        // Коэффициент изменения температуры в зависимости от времени суток
        switch (timeOfDay) {
            case "Ночь": return 0.005;
            case "Утро": return 0.01;
            case "День": return 0.015;
            case "Вечер": return 0.01;
            default: return 0.01;
        }
    }

    private void calculateWaitTemperature() {
        this.waitTemperature = workTemperature - M;
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