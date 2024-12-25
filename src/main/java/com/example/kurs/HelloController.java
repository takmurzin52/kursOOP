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
        // Устанавливаем индивидуальные настройки для каждого Spinner
        setupSpinner(spinnerLivingRoom, 18, 24, 18, 1);
        setupSpinner(spinnerWorkRoom, 20, 25, 20, 1);
        setupSpinner(spinnerKitchen, 17, 23, 17, 1);
        setupSpinner(spinnerBathroom, 23, 27, 23, 1);
    }

    public void setModeling(Modeling modeling) {
        this.modeling = modeling;

        // Установить начальное значение после инициализации modeling
        if (dayOfWeekLabel != null) {
            dayOfWeekLabel.setText(modeling.getDayOfWeek());
        }
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
    private void showScheduleDialog(String roomName ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScheduleDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("Установить расписание: " + roomName);
            dialogStage.initModality(Modality.APPLICATION_MODAL);

            // Фиксируем размер окна
            dialogStage.setResizable(false); // Запрет изменения размера
            dialogStage.setMinWidth(252); // Ширина окна
            dialogStage.setMinHeight(330); // Высота окна

            ScheduleDialogController controller = loader.getController();
            controller.setModeling(modeling);
            controller.setRoomName(roomName); // Передаем имя комнаты в контроллер

            // Ожидание закрытия диалога
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openLivingRoomScheduleDialog() {
        showScheduleDialog("Гостиная");
    }
    @FXML
    private void openWorkRoomScheduleDialog() {
        showScheduleDialog("Рабочий кабинет");
    }
    @FXML
    private void openKitchenScheduleDialog() {
        showScheduleDialog("Кухня");
    }
    @FXML
    private void openBathroomScheduleDialog() {
        showScheduleDialog("Ванная");
    }

    @FXML
    private void startSimulation() {
        modeling.startSimulation(this);
        modeling.getTimer().startTimer();
        startSimulationButton.setDisable(true);
        stopSimulationButton.setDisable(false);
    }

    @FXML
    private void stopSimulation() {
        modeling.stopSimulation();
        modeling.getTimer().stopTimer(); // останавливаем таймер
        startSimulationButton.setDisable(false);
        stopSimulationButton.setDisable(true);
    }

    protected void updateUI() {
        updateRoomUI("Гостиная", workTemperatureLivingRoom, valveLivingRoom, presenceLivingRoom);
        updateRoomUI("Рабочий кабинет", workTemperatureWorkRoom, valveWorkRoom, presenceWorkRoom);
        updateRoomUI("Кухня", workTemperatureKitchen, valveKitchen, presenceKitchen);
        updateRoomUI("Ванная", workTemperatureBathroom, valveBathroom, presenceBathroom);

        // Обновление общих элементов интерфейса
        timeOfDayLabel.setText(modeling.getTimeOfDay());
        dayOfWeekLabel.setText(modeling.getTimer().getCurrentDay().name());
        fuelConsumptionLabel.setText("" + modeling.getTotalFuelConsumption());
    }

    private void updateRoomUI(String roomName, Label temperatureLabel, Circle valve, Label presenceLabel) {
        temperatureLabel.setText("Температура: " + modeling.getRoomTemperature(roomName) + "°C");
        valve.setFill(getValveColor(modeling.getValvePosition(roomName)));
        presenceLabel.setText("Присутствие людей: " + (modeling.isPresenceDetected(roomName) ? "+" : "-"));
    }

    private javafx.scene.paint.Color getValveColor(int position) {
        switch (position) {
            case 0: return javafx.scene.paint.Color.RED;
            case 1: return javafx.scene.paint.Color.ORANGE;
            case 2: return javafx.scene.paint.Color.GREEN;
            default: return javafx.scene.paint.Color.BLACK;
        }
    }
}