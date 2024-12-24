package com.example.kurs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Spinner<Integer> spinnerLivingRoom;
    @FXML
    private Spinner<Integer> spinnerWorkRoom;
    @FXML
    private Spinner<Integer> spinnerKitchen;
    @FXML
    private Spinner<Integer> spinnerBathroom;
    @FXML
    private Slider sliderLivingRoom;
    @FXML
    private Slider sliderWorkRoom;
    @FXML
    private Slider sliderKitchen;
    @FXML
    private Slider sliderBathroom;
    @FXML
    private Label workTemperatureLivingRoom;
    @FXML
    private Label workTemperatureWorkRoom;
    @FXML
    private Label workTemperatureKitchen;
    @FXML
    private Label workTemperatureBathroom;
    @FXML
    private Circle valveLivingRoom;
    @FXML
    private Circle valveWorkRoom;
    @FXML
    private Circle valveKitchen;
    @FXML
    private Circle valveBathroom;
    @FXML
    private Label presenceLivingRoom;
    @FXML
    private Label presenceWorkRoom;
    @FXML
    private Label presenceKitchen;
    @FXML
    private Label presenceBathroom;
    @FXML
    private Button startSimulationButton;
    @FXML
    private Button stopSimulationButton;
    @FXML
    private Label timeOfDayLabel;
    @FXML
    private Label dayOfWeekLabel;
    @FXML
    private Label fuelConsumptionLabel;

    private Modeling modeling;

    @FXML
    private void initialize() {
        modeling = new Modeling();
        modeling.initializeRooms();

        // Устанавливаем индивидуальные настройки для каждого Spinner
        setupSpinner(spinnerLivingRoom, 19, 22, 19, 1);
        setupSpinner(spinnerWorkRoom, 16, 25, 16, 1);
        setupSpinner(spinnerKitchen, 17, 21, 17, 1);
        setupSpinner(spinnerBathroom, 24, 26, 24, 1);

        // Устанавливаем начальное значение для dayOfWeekLabel
        dayOfWeekLabel.setText(modeling.getDayOfWeek());
    }

    private void setupSpinner(Spinner<Integer> spinner, int min, int max, int initialValue, int step) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue, step);
        spinner.setValueFactory(valueFactory);
        spinner.setEditable(true); // Опционально, если нужно редактирование через текстовое поле
    }

    @FXML
    private void setButtonAction() {
        int livingRoomWorkTemp = spinnerLivingRoom.getValue();
        int livingRoomM = (int) sliderLivingRoom.getValue();
        int workRoomWorkTemp = spinnerWorkRoom.getValue();
        int workRoomM = (int) sliderWorkRoom.getValue();
        int kitchenWorkTemp = spinnerKitchen.getValue();
        int kitchenM = (int) sliderKitchen.getValue();
        int bathroomWorkTemp = spinnerBathroom.getValue();
        int bathroomM = (int) sliderBathroom.getValue();

        modeling.setRoomParameters("Гостиная", livingRoomWorkTemp, livingRoomM);
        modeling.setRoomParameters("Рабочий кабинет", workRoomWorkTemp, workRoomM);
        modeling.setRoomParameters("Кухня", kitchenWorkTemp, kitchenM);
        modeling.setRoomParameters("Ванная", bathroomWorkTemp, bathroomM);
    }

    @FXML
    private void showScheduleDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScheduleDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("Установить расписание");
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // Фиксируем размер окна
            dialogStage.setResizable(false); // Запрет изменения размера
            dialogStage.setMinWidth(252); // Ширина окна
            dialogStage.setMinHeight(330); // Высота окна

            ScheduleDialogController controller = loader.getController();
            controller.setModeling(modeling);

            // Ожидание закрытия диалога
            dialogStage.showAndWait();

            boolean[] schedule = controller.getSchedule();
            // Обработайте расписание (например, сохраните для комнаты)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void startSimulation() {
        modeling.startSimulation(this);
    }

    @FXML
    private void stopSimulation() {
        modeling.stopSimulation();
    }

    protected void updateUI() {
        // Обновление UI на основе данных из модели
        workTemperatureLivingRoom.setText("Температура: " + modeling.getRoomTemperature("Гостиная") + "°C");
        workTemperatureWorkRoom.setText("Температура: " + modeling.getRoomTemperature("Рабочий кабинет") + "°C");
        workTemperatureKitchen.setText("Температура: " + modeling.getRoomTemperature("Кухня") + "°C");
        workTemperatureBathroom.setText("Температура: " + modeling.getRoomTemperature("Ванная") + "°C");

        valveLivingRoom.setFill(getValveColor(modeling.getValvePosition("Гостиная")));
        valveWorkRoom.setFill(getValveColor(modeling.getValvePosition("Рабочий кабинет")));
        valveKitchen.setFill(getValveColor(modeling.getValvePosition("Кухня")));
        valveBathroom.setFill(getValveColor(modeling.getValvePosition("Ванная")));

        presenceLivingRoom.setText("Присутствие людей: " + (modeling.isPresenceDetected("Гостиная") ? "+" : "-"));
        presenceWorkRoom.setText("Присутствие людей: " + (modeling.isPresenceDetected("Рабочий кабинет") ? "+" : "-"));
        presenceKitchen.setText("Присутствие людей: " + (modeling.isPresenceDetected("Кухня") ? "+" : "-"));
        presenceBathroom.setText("Присутствие людей: " + (modeling.isPresenceDetected("Ванная") ? "+" : "-"));

        timeOfDayLabel.setText(modeling.getTimeOfDay());
        dayOfWeekLabel.setText(modeling.getDayOfWeek());
        fuelConsumptionLabel.setText("" + modeling.getTotalFuelConsumption());
    }

    private javafx.scene.paint.Color getValveColor(int position) {
        switch (position) {
            case 0: return javafx.scene.paint.Color.RED;
            case 1: return javafx.scene.paint.Color.ORANGE;
            case 2: return javafx.scene.paint.Color.GREEN;
            default: return javafx.scene.paint.Color.BLACK;
        }
    }

    public void setModeling(Modeling modeling) {
        this.modeling = modeling;
    }
}