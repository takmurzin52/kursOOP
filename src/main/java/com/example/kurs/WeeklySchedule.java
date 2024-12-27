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

    public void setPresence(DayOfWeek day, String roomName, boolean presence) {
        if (schedule.containsKey(day)) {
            Map<String, Boolean> daySchedule = schedule.get(day);
            if (daySchedule != null) {
                daySchedule.put(roomName, presence); // Сохраняем обновление
            }
        }
    }

    public boolean isPresence(DayOfWeek day, String roomName) {
        Map<String, Boolean> daySchedule = schedule.get(day);
        return daySchedule != null && daySchedule.getOrDefault(roomName, false);
    }

    // Получение расписания для текущего дня
    public Map<String, Boolean> getDaySchedule(DayOfWeek day) {
        return schedule.getOrDefault(day, new HashMap<>());
    }

    public Map<DayOfWeek, Boolean> getRoomSchedule(String roomName) {
        Map<DayOfWeek, Boolean> roomSchedule = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek day : DayOfWeek.values()) {
            roomSchedule.put(day, schedule.get(day).getOrDefault(roomName, false));
        }
        return roomSchedule;
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
    public void setCurrentDay(DayOfWeek day) {
        this.currentDay = day;
    }
}