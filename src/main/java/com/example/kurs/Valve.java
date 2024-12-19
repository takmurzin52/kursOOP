package com.example.kurs;

public class Valve {
    private String roomName;
    private int position; // 0 - закрыт, 1 - полуоткрыт, 2 - открыт

    public Valve(String roomName) {
        this.roomName = roomName;
        this.position = 0;
    }

    public void setPosition(int position) {
        if (position >= 0 && position <= 2) {
            this.position = position;
        }
    }

    public int getPosition() {
        return position;
    }
}