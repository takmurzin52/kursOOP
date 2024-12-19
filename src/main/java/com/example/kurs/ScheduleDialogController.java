package com.example.kurs;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

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
    private boolean[] schedule = new boolean[7]; // Хранит статусы для каждого дня

    @FXML
    private void saveSchedule() {
        if (modeling != null) {
        // Сохраняем выбор пользователя
        schedule[0] = mondayCheckBox.isSelected();
        schedule[1] = tuesdayCheckBox.isSelected();
        schedule[2] = wednesdayCheckBox.isSelected();
        schedule[3] = thursdayCheckBox.isSelected();
        schedule[4] = fridayCheckBox.isSelected();
        schedule[5] = saturdayCheckBox.isSelected();
        schedule[6] = sundayCheckBox.isSelected();

        // Устанавливаем расписание в модели
        modeling.setWeeklySchedule("Понедельник", "Гостиная", schedule[0]);
        modeling.setWeeklySchedule("Вторник", "Гостиная", schedule[1]);
        modeling.setWeeklySchedule("Среда", "Гостиная", schedule[2]);
        modeling.setWeeklySchedule("Четверг", "Гостиная", schedule[3]);
        modeling.setWeeklySchedule("Пятница", "Гостиная", schedule[4]);
        modeling.setWeeklySchedule("Суббота", "Гостиная", schedule[5]);
        modeling.setWeeklySchedule("Воскресенье", "Гостиная", schedule[6]);

        // Закрыть окно после сохранения
        mondayCheckBox.getScene().getWindow().hide();
        }
        else {
            System.err.println("Modeling object is not initialized.");
        }
    }

    public void setModeling(Modeling modeling) {
        this.modeling = modeling;
    }

    public boolean[] getSchedule() {
        return schedule; // Возвращает массив с расписанием
    }
}