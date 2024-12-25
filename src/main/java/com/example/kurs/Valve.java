package com.example.kurs;

public class Valve {
    private String roomName;
    private int position; // 0 - закрыт, 1 - полуоткрыт, 2 - открыт

    // Конструктор с возможностью указать начальное положение
    public Valve(String roomName, int initialPosition) {
        this.roomName = roomName;
        setPosition(initialPosition); // Используем сеттер для валидации начальной позиции
    }

    public void setPosition(int position) {
        if (position < 0 || position > 2) {
            throw new IllegalArgumentException("Недопустимое значение для позиции клапана. Должно быть от 0 до 2.");
        }
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public String getPositionDescription() {
        switch (position) {
            case 0:
                return "Закрыт";
            case 1:
                return "Полуоткрыт";
            case 2:
                return "Открыт";
            default:
                return "Неизвестно";
        }
    }
}