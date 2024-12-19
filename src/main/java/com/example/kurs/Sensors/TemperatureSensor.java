package com.example.kurs.Sensors;

import java.util.Random;

public class TemperatureSensor extends Sensor {
    private Random random;

    public TemperatureSensor(String roomName) {
        super(roomName);
        random = new Random();
    }

    @Override
    public Integer getValue() {
        return 20 + random.nextInt(5) - 2; // Пример случайного значения температуры в диапазоне [18, 22]
    }
}