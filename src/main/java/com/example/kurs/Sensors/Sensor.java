package com.example.kurs.Sensors;

public abstract class Sensor {
    private String roomName;

    public Sensor (String roomName) {
        this.roomName = roomName;
    }
    public abstract <T> T getValue();

    public String getRoomName() {
        return roomName;
    }
}