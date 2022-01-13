package pl.wilenskid.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.wilenskid.alghoritm.AlgorithmConfig;
import pl.wilenskid.alghoritm.AlgorithmsFactories;
import pl.wilenskid.alghoritm.crossing.CrossingType;
import pl.wilenskid.alghoritm.selection.SelectionType;
import pl.wilenskid.alghoritm.succession.SuccessionType;
import pl.wilenskid.core.Item;
import pl.wilenskid.core.ItemsSettings;
import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.RepairKnapsack;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GeneralWindow extends Application {

    private static final Random RANDOM = new Random();
    private static final int ITEMS_COUNT = 14;
    private static final double KNAPSACK_CAPACITY = 100;

    public GridPane mainTab;
    public GridPane configurationTab;
    public Item[] items;

    @FXML
    public TableView<Item> itemsTable;
    @FXML
    public LineChart<Integer, Double> evolutionChart;

    @FXML
    public void handleGenerateItems() {
        items = new Item[14];
        ItemsSettings itemsSettings = new ItemsSettings(10, 10);

        for (int i = 0; i < items.length; i++) {
            double price = RANDOM.nextInt(itemsSettings.getMaxItemPrice() * 100 + 1) / 100d;
            double weight = RANDOM.nextInt(itemsSettings.getMaxItemWeight() * 100 + 1) / 100d;
            boolean isTaken = RANDOM.nextBoolean();
            items[i] = new Item(price, weight, isTaken);
        }

        initializeItemsTableColumns();

        itemsTable.getItems().clear();
        itemsTable.getItems().addAll(items);
    }

    public void initializeItemsTableColumns() {
        ObservableList<TableColumn<Item, ?>> columns = itemsTable.getColumns();
        columns.clear();

        TableColumn<Item, String> weight = new TableColumn<>("Weight");
        weight.setCellValueFactory(new PropertyValueFactory<Item, String>("weight"));

        TableColumn<Item, String> price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<Item, String>("price"));

        TableColumn<Item, String> value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        columns.addAll(weight, price, value);
    }

    @FXML
    public void startApplication() {

        items = new Item[14];
        ItemsSettings itemsSettings = new ItemsSettings(10, 10);

        for (int i = 0; i < items.length; i++) {
            double price = RANDOM.nextInt(itemsSettings.getMaxItemPrice() * 100 + 1) / 100d;
            double weight = RANDOM.nextInt(itemsSettings.getMaxItemWeight() * 100 + 1) / 100d;
            boolean isTaken = RANDOM.nextBoolean();
            items[i] = new Item(price, weight, isTaken);
        }

        List<Knapsack> initialKnapsacksPopulation = new ArrayList<>();

        for (int j = 0; j < 100; j++) {
            pl.wilenskid.core.Knapsack knapsack = new pl.wilenskid.core.Knapsack(items, KNAPSACK_CAPACITY, null);
            initialKnapsacksPopulation.add(knapsack);
        }

        AlgorithmConfig algorithmConfig = AlgorithmConfig
                .builder()
                .successionType(SuccessionType.RANDOM_OF_ERA)
                .selectionType(SelectionType.TOURNAMENT_METHOD)
                .crossingType(CrossingType.TWO_SPOT)
                .inversionProbability(0.8)
                .mutationProbability(0.8)
                .crossingProbability(0.8)
                .erasCount(100)
                .isMax(true)
                .build();

        Map<Integer, List<Knapsack>> evolution = AlgorithmsFactories
                .getInstance()
                .getSuccessionMethodFactory()
                .getByType(algorithmConfig.getSuccessionType())
                .succession(initialKnapsacksPopulation, algorithmConfig);

        Map<Integer, Double> data = new HashMap<>();

        double max = Double.MIN_VALUE;

        for (int eraIndex : evolution.keySet()) {
            List<Knapsack> subjects = evolution.get(eraIndex);

            double meanAdaptationValue = subjects
                    .parallelStream()
//                    .map(RepairKnapsack::repair)
                    .map(Knapsack::calculateAdaptationValue)
                    .reduce(0d, Double::sum);

            if (max < meanAdaptationValue) {
                max = meanAdaptationValue;
            }

            data.put(eraIndex, meanAdaptationValue);
        }

        XYChart.Series<Integer, Double> series = new XYChart.Series<>();

        double finalMax = max;
        data.forEach((key, value) -> series.getData().add(new XYChart.Data<>(key, value / finalMax)));

        evolutionChart.getData().add(series);
    }

    @FXML
    public void clearChart() {
        evolutionChart.getData().clear();
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL generalView = getClass()
                .getClassLoader()
                .getResource("views/general.fxml");

        if (generalView == null) {
            return;
        }

        Parent root = FXMLLoader.load(generalView);
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
