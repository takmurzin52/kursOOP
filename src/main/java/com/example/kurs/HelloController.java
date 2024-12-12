package com.example.kurs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Spinner<Integer> spinnerWorkRoom;

    @FXML
    private Spinner<Integer> spinnerLivingRoom;

    @FXML
    private Spinner<Integer> spinnerKitchen;

    @FXML
    private Spinner<Integer> spinnerBathroom;

    public void initialize() {
        // Устанавливаем индивидуальные настройки для каждого Spinner
        setupSpinner(spinnerWorkRoom, 16, 25, 16, 1);
        setupSpinner(spinnerLivingRoom, 19, 22, 19, 1);
        setupSpinner(spinnerKitchen, 17, 21, 17, 1);
        setupSpinner(spinnerBathroom, 24, 26, 24, 1);
    }

    private void setupSpinner(Spinner<Integer> spinner, int min, int max, int initialValue, int step) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue, step);
        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true); // Опционально, если нужно редактирование через текстовое поле
    }

    public int getSpinnerValue(Spinner<Integer> spinner) {
        // Получение текущего значения любого Spinner
        return spinner.getValue();
    }

    public void showScheduleDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScheduleDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("");
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // Фиксируем размер окна
            dialogStage.setResizable(false); // Запрет изменения размера

            // (Необязательно) Устанавливаем фиксированный размер окна
            dialogStage.setMinWidth(252); // Ширина окна
            dialogStage.setMinHeight(330); // Высота окна

            // Ожидание закрытия диалога
            dialogStage.showAndWait();

            // Получение данных расписания
            ScheduleDialogController controller = loader.getController();
            boolean[] schedule = controller.getSchedule();
            // Обработайте расписание (например, сохраните для комнаты)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}