package com.example.kurs;

import javafx.animation.Timeline;
import java.time.DayOfWeek;

public class Timer {
    private Timeline timeline;
    private int currentMinute;
    private String timeOfDay;
    private int timeAcceleration;
    private DayOfWeek currentDay;

    public void startTimer() {
        this.timeAcceleration = 60;
        currentMinute = 0;  // Инициализация на 0
        timeOfDay = "Ночь";  // Начальное значение времени суток
        currentDay = DayOfWeek.MONDAY;
    }

    private void updateTimeOfDay() {
        if (currentMinute >= 0 && currentMinute < 6 * 60) {
            timeOfDay = "Ночь";
        } else if (currentMinute >= 6 * 60 && currentMinute < 12 * 60) {
            timeOfDay = "Утро";
        } else if (currentMinute >= 12 * 60 && currentMinute < 18 * 60) {
            timeOfDay = "День";
        } else if (currentMinute >= 18 * 60 && currentMinute < 24 * 60) {
            timeOfDay = "Вечер";
        }
        if (currentMinute >= 24 * 60) {
            currentMinute = 0;
            timeOfDay = "Ночь";
            incrementDay();
        }
    }

    public void incrementMinute(int minutes) {
        currentMinute += minutes;
        updateTimeOfDay();
    }

    public void incrementDay() {
        // Переход к следующему дню недели, начиная с понедельника
        currentDay = currentDay.plus(1);
    }

    public int getCurrentMinute() {
        return currentMinute;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public DayOfWeek getCurrentDay() {
        if (currentDay == null) {
            // Логика по умолчанию, если текущий день не установлен
            currentDay = DayOfWeek.MONDAY;
        }
        return currentDay;  // Возвращаем текущий день недели
    }

    public void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}