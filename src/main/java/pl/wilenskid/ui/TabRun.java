package pl.wilenskid.ui;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import pl.wilenskid.alghoritm.AlgorithmConfig;
import pl.wilenskid.alghoritm.AlgorithmsFactories;
import pl.wilenskid.alghoritm.succession.SuccessionType;
import pl.wilenskid.core.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TabRun {

    private static final double KNAPSACK_CAPACITY = 10d;

    public Button runButton;
    public Button saveResultsButton;
    public Button clearButton;
    public ComboBox<DisplayType> displayType;
    public LineChart<Integer, Double> evolutionChart;

    private Map<Integer, List<Knapsack>> evolution;

    @FXML
    public void initialize() {
        displayType
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> {
                    applyResultOnChart();
                });


        displayType.getItems().addAll(DisplayType.values());
        displayType.getSelectionModel().selectFirst();
    }

    @FXML
    public void runApplication() {
        Item[] items = ApplicationContext.getInstance().getItems();

        if (items == null || items.length == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No items");
            alert.setContentText("It's looks like application have no items context. Please generate or load items in configuration tab.");
            alert.showAndWait();
            return;
        }

        List<Knapsack> initialKnapsacksPopulation = new ArrayList<>();
        double capacity = ApplicationContext.getInstance().getCapacity();

        for (int j = 0; j < 100; j++) {
            Knapsack knapsack = new Knapsack(items, capacity, null);
            initialKnapsacksPopulation.add(knapsack);
        }

        AlgorithmConfig algorithmConfig = ApplicationContext
                .getInstance()
                .getAlgorithmConfigBuilder()
                .build();

        evolution = AlgorithmsFactories
                .getInstance()
                .getSuccessionMethodFactory()
                .getByType(algorithmConfig.getSuccessionType())
                .succession(initialKnapsacksPopulation, algorithmConfig);

        applyResultOnChart();
        ApplicationContext.getInstance().setResults(evolution);
        ApplicationContext.getInstance().getTabDetails().populateTabData();
    }

    @FXML
    public void clearChart() {
        evolutionChart.getData().clear();
        clearButton.setDisable(true);
        saveResultsButton.setDisable(true);
        ApplicationContext.getInstance().setResults(null);
        ApplicationContext.getInstance().getTabDetails().populateTabData();
    }

    @FXML
    public void saveResults() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);

        Map<Integer, List<KnapsackBean>> toSave = new HashMap<>();

        for (Map.Entry<Integer, List<Knapsack>> entry : evolution.entrySet()) {
            List<KnapsackBean> knapsackBeans = entry
                    .getValue()
                    .stream()
                    .map(KnapsackBean::new)
                    .collect(Collectors.toList());

            toSave.put(entry.getKey(), knapsackBeans);
        }

        try {
            Gson gson = new Gson();
            String fileContent = gson.toJson(toSave);
            PrintWriter writer = new PrintWriter(file);
            writer.println(fileContent);
            writer.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void applyResultOnChart() {
        if (evolution == null || evolution.size() == 0) {
            return;
        }

        Map<Integer, Double> result = new HashMap<>();
        DisplayType selectedItem = displayType.getSelectionModel().getSelectedItem();

        for (int eraIndex : evolution.keySet()) {
            List<Knapsack> subjects = evolution.get(eraIndex);

            double meanAdaptationValue = subjects
                    .parallelStream()
                    .map(RepairKnapsack::repair)
                    .map(knapsack -> calculateKnapsack(knapsack, selectedItem))
                    .reduce(0d, Double::sum);

            result.put(eraIndex, meanAdaptationValue / subjects.size());
        }

        evolutionChart.getData().clear();
        XYChart.Series<Integer, Double> series = new XYChart.Series<>();
        result.forEach((integer, aDouble) -> series.getData().add(new XYChart.Data<>(integer, aDouble)));
        evolutionChart.getData().add(series);
        clearButton.setDisable(false);
        saveResultsButton.setDisable(false);
    }

    private double calculateKnapsack(Knapsack knapsack, DisplayType displayType) {
        switch (displayType) {
            case VALUE:
                return knapsack.calculateAdaptationValue();
            case WEIGHT:
                return knapsack.calculateItemsWeight();
            case PRICE:
                return knapsack.calculateItemsPrice();
        }

        return 0;
    }

}
