package com.example.kurs;

import javafx.application.Platform;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class Modeling {
    private Map<String, Room> rooms;
    private WeeklySchedule weeklySchedule;
    private Timer timer;
    private FuelConsumption fuelConsumption;
    private boolean simulationRunning;

    public Modeling() {
        rooms = new HashMap<>();
        weeklySchedule = new WeeklySchedule();
        timer = new Timer();
        fuelConsumption = new FuelConsumption();
        simulationRunning = false;
        initializeRooms();
    }

    public Timer getTimer() {
        return timer;  // Возвращаем объект таймера
    }

    private void initializeRooms() {
        rooms.put("Гостиная", new Room("Гостиная", 20, 1.5, 18, 24, new WeeklySchedule()));
        rooms.put("Рабочий кабинет", new Room("Рабочий кабинет", 15, 1.2, 20, 25, new WeeklySchedule()));
        rooms.put("Кухня", new Room("Кухня", 10, 1.0, 17, 23, new WeeklySchedule()));
        rooms.put("Ванная", new Room("Ванная", 5, 0.8, 23, 27, new WeeklySchedule()));
    }

    public void setRoomParameters(String roomName, int workTemperature, int M) {
        Room room = rooms.get(roomName);
        if (room != null) {
            room.setWorkTemperature(workTemperature);
            room.setM(M);
        }
    }

    public void setWeeklySchedule(DayOfWeek day, String roomName, boolean presence) {
        Room room = rooms.get(roomName);
        if (room != null) {
            room.getSchedule().setPresence(day, roomName, presence);
        }
    }

    public WeeklySchedule getWeeklySchedule(String roomName) {
        Room room = rooms.get(roomName);
        return room != null ? room.getSchedule() : null;
    }

    public void startSimulation(HelloController controller) {
        simulationRunning = true;

        // Синхронизация текущего дня
        for (Room room : rooms.values()) {
            room.getSchedule().setCurrentDay(timer.getCurrentDay());
        }

        new Thread(() -> {
            while (simulationRunning) {
                for (Room room : rooms.values()) {
                    WeeklySchedule schedule = room.getSchedule();
                    boolean presence = schedule.isPresence(schedule.getCurrentDay(), room.getName());
                    int targetTemperature = presence ? room.getWorkTemperature() : room.getWaitTemperature();

                    room.setValvePosition(getValvePosition(room.getTemperature(), targetTemperature));
                    room.updateTemperature(timer.getTimeOfDay());

                    // Расчет потребления топлива
                    double consumption = room.getArea() * (room.getValvePosition() == 0 ? 0 : room.getValvePosition() == 1 ? 2 : 5);
                    fuelConsumption.addConsumption(consumption);

                    // Обновление UI информации о присутствии
                    boolean finalPresence = presence;
                    Platform.runLater(() -> controller.updatePresenceLabel(room.getName(), finalPresence));
                }
                // Обновление интерфейса
                Platform.runLater(controller::updateUI);

                // Увеличение времени (минуты)
                timer.incrementMinute(60);  // Увеличиваем время на 60 минут

                // Вывод текущего времени в консоль
                System.out.println("Текущая минута: " + timer.getCurrentMinute() + ", Время суток: " + timer.getTimeOfDay());

                // Переход к следующему дню
                if (timer.getCurrentMinute() >= 1440) {
                    timer.incrementDay();
                    timer.incrementMinute(-1440);

                    // Обновляем текущий день во всех расписаниях
                    for (Room room : rooms.values()) {
                        WeeklySchedule schedule = room.getSchedule();
                        schedule.setCurrentDay(timer.getCurrentDay());
                    }
                }

                // Ожидание одной симуляционной минуты (1 секунда реального времени)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public void stopSimulation() {
        simulationRunning = false;
    }

    public int getRoomTemperature(String roomName) {
        Room room = rooms.get(roomName);
        return room != null ? room.getTemperature() : 0;
    }

    public int getValvePosition(String roomName) {
        Room room = rooms.get(roomName);
        return room != null ? room.getValvePosition() : 0;
    }

    public boolean isPresenceDetected(String roomName) {
        Room room = rooms.get(roomName);
        return room != null && room.isPresenceDetected();
    }

    public String getTimeOfDay() {
        return timer.getTimeOfDay();
    }

    public String getDayOfWeek() {
        return weeklySchedule.getCurrentDay().toString();
    }

    public double getTotalFuelConsumption() {
        return fuelConsumption.getTotalConsumption();
    }

    private int getValvePosition(int currentTemperature, int targetTemperature) {
        if (currentTemperature < targetTemperature - 1) {
            return 2; // Открыть клапан
        } else if (currentTemperature > targetTemperature + 1) {
            return 0; // Закрыть клапан
        } else {
            return 1; // Полуоткрыть клапан
        }
    }
}