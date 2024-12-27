package com.example.kurs;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

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
    private String roomName;
    private WeeklySchedule schedule;

    public void setData(String roomName, WeeklySchedule schedule) {
        this.roomName = roomName;
        this.schedule = schedule;
        loadSchedule();
    }

    private void loadSchedule() {
        for (DayOfWeek day : DayOfWeek.values()) {
            boolean presence = schedule.isPresence(day, roomName);
            getCheckBox(day).setSelected(presence);
        }
    }

    private CheckBox getCheckBox(DayOfWeek day) {
        switch (day) {
            case MONDAY: return mondayCheckBox;
            case TUESDAY: return tuesdayCheckBox;
            case WEDNESDAY: return wednesdayCheckBox;
            case THURSDAY: return thursdayCheckBox;
            case FRIDAY: return fridayCheckBox;
            case SATURDAY: return saturdayCheckBox;
            case SUNDAY: return sundayCheckBox;
            default: throw new IllegalArgumentException("Неподдерживаемый день: " + day);
        }
    }

    @FXML
    private void saveSchedule() {
        Map<DayOfWeek, Boolean> newSchedule = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            boolean presence = getCheckBox(day).isSelected();
            schedule.setPresence(day, roomName, presence);
        }
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) mondayCheckBox.getScene().getWindow();
        stage.close();
    }
}