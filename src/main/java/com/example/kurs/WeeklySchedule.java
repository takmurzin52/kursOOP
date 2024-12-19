package com.example.kurs;

import java.util.HashMap;
import java.util.Map;

public class WeeklySchedule {
    private Map<String, Map<String, Boolean>> schedule;
    private String[] daysOfWeek = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
    private int currentDayIndex = 0;

    public WeeklySchedule() {
        schedule = new HashMap<>();
        initializeSchedule();
    }

    private void initializeSchedule() {
        for (String day : daysOfWeek) {
            Map<String, Boolean> daySchedule = new HashMap<>();
            daySchedule.put("Гостиная", false);
            daySchedule.put("Рабочий кабинет", false);
            daySchedule.put("Кухня", false);
            daySchedule.put("Ванная", false);
            schedule.put(day, daySchedule);
        }
    }

    public void setPresence(String day, String room, boolean presence) {
        if (schedule.containsKey(day)) {
            schedule.get(day).put(room, presence);
        }
    }

    public boolean isPresence(String day, String room) {
        return schedule.get(day).getOrDefault(room, false);
    }

    public String getCurrentDay() {
        return daysOfWeek[currentDayIndex];
    }

    public void nextDay() {
        currentDayIndex = (currentDayIndex + 1) % daysOfWeek.length;
    }
}