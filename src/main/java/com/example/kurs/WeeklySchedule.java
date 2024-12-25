package com.example.kurs;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class WeeklySchedule {
    private Map<DayOfWeek, Map<String, Boolean>> schedule;
    private DayOfWeek currentDay;

    public WeeklySchedule() {
        schedule = new EnumMap<>(DayOfWeek.class);
        currentDay = DayOfWeek.MONDAY; // Начинаем с понедельника
        initializeSchedule();
    }

    private void initializeSchedule() {
        for (DayOfWeek day : DayOfWeek.values()) {
            Map<String, Boolean> daySchedule = new HashMap<>();
            daySchedule.put("Гостиная", false);
            daySchedule.put("Рабочий кабинет", false);
            daySchedule.put("Кухня", false);
            daySchedule.put("Ванная", false);
            schedule.put(day, daySchedule);
        }
    }

    public void setPresence(DayOfWeek day, String room, boolean presence) {
        if (schedule.containsKey(day)) {
            schedule.get(day).put(room, presence);
        }
    }

    public boolean isPresence(DayOfWeek day, String room) {
        if (schedule.containsKey(day)) {
            Map<String, Boolean> daySchedule = schedule.get(day);
            return daySchedule.getOrDefault(room, false);
        }
        return false;
    }

    // Получение расписания для текущего дня
    public Map<String, Boolean> getDaySchedule(DayOfWeek day) {
        return schedule.getOrDefault(day, new HashMap<>());
    }

    // Удобный метод для интеграции
    public boolean isPresenceScheduled(DayOfWeek day) {
        Map<String, Boolean> daySchedule = getDaySchedule(day);
        return daySchedule.values().stream().anyMatch(Boolean::booleanValue);
    }

    // Получение текущего дня недели
    public DayOfWeek getCurrentDay() {
        return currentDay;
    }
}