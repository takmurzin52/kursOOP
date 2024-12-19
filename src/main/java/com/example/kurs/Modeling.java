package com.example.kurs;

import com.example.kurs.Room;

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

    void initializeRooms() {
        rooms.put("Гостиная", new Room("Гостиная", 20, 1.5));
        rooms.put("Рабочий кабинет", new Room("Рабочий кабинет", 15, 1.2));
        rooms.put("Кухня", new Room("Кухня", 10, 1.0));
        rooms.put("Ванная", new Room("Ванная", 5, 0.8));
    }

    public void setRoomParameters(String roomName, int workTemperature, int M) {
        Room room = rooms.get(roomName);
        if (room != null) {
            room.setWorkTemperature(workTemperature);
            room.setM(M);
        }
    }

    public void setWeeklySchedule(String day, String room, boolean presence) {
        weeklySchedule.setPresence(day, room, presence);
    }

    public void startSimulation() {
        simulationRunning = true;
        new Thread(() -> {
            while (simulationRunning) {
                for (Room room : rooms.values()) {
                    boolean presence = room.isPresenceDetected() || weeklySchedule.isPresence(timer.getTimeOfDay(), room.getName());
                    int targetTemperature = presence ? room.getWorkTemperature() : room.getWaitTemperature();
                    room.setValvePosition(getValvePosition(room.getTemperature(), targetTemperature));
                    room.updateTemperature(timer.getTimeOfDay());
                    double consumption = room.getArea() * (room.getValvePosition() == 0 ? 0 : room.getValvePosition() == 1 ? 2 : 5);
                    fuelConsumption.addConsumption(consumption);
                }
                try {
                    Thread.sleep(60000); // Ожидание 1 минуты
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Переход к следующему дню
                weeklySchedule.nextDay();
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
        return weeklySchedule.getCurrentDay();
    }

    public double getTotalFuelConsumption() {
        return fuelConsumption.getTotalConsumption();
    }

    private int getValvePosition(double currentTemperature, int targetTemperature) {
        if (currentTemperature < targetTemperature - 1) {
            return 2; // Открыть клапан
        } else if (currentTemperature > targetTemperature + 1) {
            return 0; // Закрыть клапан
        } else {
            return 1; // Полуоткрыть клапан
        }
    }
}