package com.example.kurs.Sensors;

public class TemperatureSensor extends Sensor {
    private int minTemperature;
    private int maxTemperature;

    public TemperatureSensor(String roomName, int minTemperature, int maxTemperature) {
        super(roomName);
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    @Override
    public Integer getValue() {
        // Генерация случайного значения температуры в заданном диапазоне
        return minTemperature + (int) (Math.random() * (maxTemperature - minTemperature + 1));
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        if (minTemperature <= maxTemperature) {
            this.minTemperature = minTemperature;
        } else {
            throw new IllegalArgumentException("Минимальная температура не может быть больше максимальной.");
        }
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        if (maxTemperature >= minTemperature) {
            this.maxTemperature = maxTemperature;
        } else {
            throw new IllegalArgumentException("Максимальная температура не может быть меньше минимальной.");
        }
    }
}