package com.example.kurs;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Timer {
    private Timeline timeline;
    private int currentMinute;
    private String timeOfDay;

    public Timer() {
        timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> {
            currentMinute++;
            updateTimeOfDay();
            System.out.println("Current minute: " + currentMinute + ", Time of day: " + timeOfDay);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        currentMinute = 0;
        timeOfDay = "Ночь";
    }

    private void updateTimeOfDay() {
        if (currentMinute >= 0 && currentMinute < 6 * 60) {
            timeOfDay = "Ночь";
        } else if (currentMinute >= 6 * 60 && currentMinute < 12 * 60) {
            timeOfDay = "Утро";
        } else if (currentMinute >= 12 * 60 && currentMinute < 18 * 60) {
            timeOfDay = "День";
        } else {
            timeOfDay = "Вечер";
        }
        if (currentMinute >= 24 * 60) {
            currentMinute = 0;
        }
    }

    public int getCurrentMinute() {
        return currentMinute;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }
}