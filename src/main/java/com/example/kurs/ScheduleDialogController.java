package com.example.kurs;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class ScheduleDialogController {
    @FXML private CheckBox mondayCheckBox;
    @FXML private CheckBox tuesdayCheckBox;
    @FXML private CheckBox wednesdayCheckBox;
    @FXML private CheckBox thursdayCheckBox;
    @FXML private CheckBox fridayCheckBox;
    @FXML private CheckBox saturdayCheckBox;
    @FXML private CheckBox sundayCheckBox;

    private boolean[] schedule = new boolean[7]; // Хранит статусы для каждого дня

    public void saveSchedule() {
        // Сохраняем выбор пользователя
        schedule[0] = mondayCheckBox.isSelected();
        schedule[1] = tuesdayCheckBox.isSelected();
        schedule[2] = wednesdayCheckBox.isSelected();
        schedule[3] = thursdayCheckBox.isSelected();
        schedule[4] = fridayCheckBox.isSelected();
        schedule[5] = saturdayCheckBox.isSelected();
        schedule[6] = sundayCheckBox.isSelected();
        // Закрыть окно после сохранения
        mondayCheckBox.getScene().getWindow().hide();
    }

    public boolean[] getSchedule() {
        return schedule; // Возвращает массив с расписанием
    }
}