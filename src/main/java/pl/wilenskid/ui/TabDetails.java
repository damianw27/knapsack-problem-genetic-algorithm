package pl.wilenskid.ui;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import pl.wilenskid.core.ApplicationContext;
import pl.wilenskid.core.Item;
import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.SelectItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TabDetails {
    public ComboBox<SelectItem<Integer>> eraSelect;
    public ComboBox<SelectItem<Integer>> bagSelect;
    public TableView<Item> bagItemsData;

    @FXML
    public void initialize() {
        initializeTableColumns();

        ApplicationContext.getInstance().setTabDetails(this);

        eraSelect
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> {
                    Integer selectedEraIndex = newValue.getValue();
                    ApplicationContext.getInstance().setSelectedEraIndex(selectedEraIndex);
                    populateTable();
                });

        bagSelect
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> {
                    Integer selectedEraIndex = newValue.getValue();
                    ApplicationContext.getInstance().setSelectedBagIndex(selectedEraIndex);
                    populateTable();
                });
    }

    @FXML
    public void populateTabData() {
        Map<Integer, List<Knapsack>> results = ApplicationContext.getInstance().getResults();

        if (results == null || results.size() == 0) {
            eraSelect.getItems().clear();
            bagSelect.getItems().clear();
            ApplicationContext.getInstance().setSelectedEraIndex(0);
            ApplicationContext.getInstance().setSelectedBagIndex(0);
            bagItemsData.getItems().clear();
            return;
        }

        List<SelectItem<Integer>> eras = new ArrayList<>(results.keySet())
                .stream()
                .map(era -> new SelectItem<>("Era nr. " + era, era))
                .collect(Collectors.toList());

        int selectedEraIndex = ApplicationContext.getInstance().getSelectedEraIndex();
        eraSelect.getItems().addAll(eras);
        eraSelect.getSelectionModel().select(selectedEraIndex);
        AtomicInteger bagIndex = new AtomicInteger(1);

        List<SelectItem<Integer>> bags = new ArrayList<>(results.get(selectedEraIndex))
                .stream()
                .map(bag -> new SelectItem<>("Bag nr. " + bagIndex.intValue(), bagIndex.getAndIncrement()))
                .collect(Collectors.toList());

        bagSelect.getItems().addAll(bags);
        bagSelect.getSelectionModel().select(ApplicationContext.getInstance().getSelectedBagIndex());
    }

    private void populateTable() {
        bagItemsData.getItems().clear();
        int selectedEraIndex = ApplicationContext.getInstance().getSelectedEraIndex();
        int selectedBagIndex = ApplicationContext.getInstance().getSelectedBagIndex();
        Map<Integer, List<Knapsack>> results = ApplicationContext.getInstance().getResults();
        Knapsack knapsack = results.get(selectedEraIndex).get(selectedBagIndex);
        Item[] items = knapsack.getItems();
        bagItemsData.getItems().addAll(items);
    }

    private void initializeTableColumns() {
        ObservableList<TableColumn<Item, ?>> columns = bagItemsData.getColumns();
        columns.clear();
        TableColumn<Item, String> weight = new TableColumn<>("Weight");
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        TableColumn<Item, String> price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Item, String> value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        TableColumn<Item, Boolean> isTaken = new TableColumn<>("Is Taken");
        isTaken.setCellValueFactory(itemStringCellDataFeatures -> new SimpleBooleanProperty(itemStringCellDataFeatures.getValue().isTaken()));

        columns.addAll(weight, price, value, isTaken);
    }
}
