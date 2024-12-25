package com.example.kurs.Sensors;

import com.example.kurs.WeeklySchedule;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class PresenceSensor extends Sensor {
    private WeeklySchedule schedule;

    public PresenceSensor(String roomName, WeeklySchedule schedule) {
        super(roomName);
        this.schedule = schedule;
    }

    @Override
    public Boolean getValue() {
        // Получаем текущий день недели
        DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
        // Определяем присутствие на основе расписания
        return schedule.isPresenceScheduled(currentDay);
    }

    public boolean isPresenceDetected() {
        return getValue();
    }
}