package com.example.kurs;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.time.DayOfWeek;

public class ScheduleDialogController {
    @FXML
    private CheckBox mondayCheckBox;
    @FXML
    private CheckBox tuesdayCheckBox;
    @FXML
    private CheckBox wednesdayCheckBox;
    @FXML
    private CheckBox thursdayCheckBox;
    @FXML
    private CheckBox fridayCheckBox;
    @FXML
    private CheckBox saturdayCheckBox;
    @FXML
    private CheckBox sundayCheckBox;

    private Modeling modeling;
    private String roomName;
    private boolean[] schedule = new boolean[7]; // Хранит статусы для каждого дня

    @FXML
    private void saveSchedule() {
        if (modeling != null && roomName != null) {
        // Сохраняем выбор пользователя
        schedule[0] = mondayCheckBox.isSelected();
        schedule[1] = tuesdayCheckBox.isSelected();
        schedule[2] = wednesdayCheckBox.isSelected();
        schedule[3] = thursdayCheckBox.isSelected();
        schedule[4] = fridayCheckBox.isSelected();
        schedule[5] = saturdayCheckBox.isSelected();
        schedule[6] = sundayCheckBox.isSelected();

        // Устанавливаем расписание в модели
        modeling.setWeeklySchedule(DayOfWeek.MONDAY, roomName, schedule[0]);
        modeling.setWeeklySchedule(DayOfWeek.TUESDAY, roomName, schedule[1]);
        modeling.setWeeklySchedule(DayOfWeek.WEDNESDAY, roomName, schedule[2]);
        modeling.setWeeklySchedule(DayOfWeek.THURSDAY, roomName, schedule[3]);
        modeling.setWeeklySchedule(DayOfWeek.FRIDAY, roomName, schedule[4]);
        modeling.setWeeklySchedule(DayOfWeek.SATURDAY, roomName, schedule[5]);
        modeling.setWeeklySchedule(DayOfWeek.SUNDAY, roomName, schedule[6]);

        // Закрыть окно после сохранения
        mondayCheckBox.getScene().getWindow().hide();
        }
        else {
            System.err.println("Объект моделирования не инициализирован.");
        }
    }

    public void setModeling(Modeling modeling) {
        this.modeling = modeling;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean[] getSchedule() {
        return schedule; // Возвращает массив с расписанием
    }

    public void initializeSchedule(boolean[] existingSchedule) {
        // Инициализируем чекбоксы согласно текущему расписанию
        if (existingSchedule != null && existingSchedule.length == 7) {
            mondayCheckBox.setSelected(existingSchedule[0]);
            tuesdayCheckBox.setSelected(existingSchedule[1]);
            wednesdayCheckBox.setSelected(existingSchedule[2]);
            thursdayCheckBox.setSelected(existingSchedule[3]);
            fridayCheckBox.setSelected(existingSchedule[4]);
            saturdayCheckBox.setSelected(existingSchedule[5]);
            sundayCheckBox.setSelected(existingSchedule[6]);
        }
    }
}