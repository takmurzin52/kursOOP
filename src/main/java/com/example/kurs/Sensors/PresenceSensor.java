package com.example.kurs.Sensors;

import java.util.Random;

public class PresenceSensor extends Sensor {
    private Random random;

    public PresenceSensor(String roomName) {
        super(roomName);
        random = new Random();
    }

    @Override
    public Boolean getValue() {
        return random.nextBoolean(); // Пример случайного значения присутствия
    }

    public boolean isPresenceDetected() {
        return (Boolean) getValue();
    }
}