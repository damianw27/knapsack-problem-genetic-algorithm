package pl.wilenskid.ui;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import pl.wilenskid.alghoritm.crossing.CrossingType;
import pl.wilenskid.alghoritm.selection.SelectionType;
import pl.wilenskid.alghoritm.succession.SuccessionType;
import pl.wilenskid.core.ApplicationContext;
import pl.wilenskid.core.Item;
import pl.wilenskid.core.ItemBean;
import pl.wilenskid.core.ItemsSettings;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TabConfiguration {

    private static final Random RANDOM = new Random();

    public TableView<Item> itemsTable;
    public ComboBox<SuccessionType> successionTypeComboBox;
    public ComboBox<SelectionType> selectionTypeComboBox;
    public ComboBox<CrossingType> crossingTypeComboBox;
    public TextField inversionProbabilityTextField;
    public TextField mutationProbabilityTextField;
    public TextField crossingProbabilityTextField;
    public TextField erasCountTextField;
    public TextField capacityField;
    public CheckBox isLookingForMaxCheckBox;
    public Button exportItemsButton;

    @FXML
    public void initialize() {
        successionTypeComboBox
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> ApplicationContext.getInstance().getAlgorithmConfigBuilder().successionType(newValue));

        selectionTypeComboBox
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> ApplicationContext.getInstance().getAlgorithmConfigBuilder().selectionType(newValue));

        crossingTypeComboBox
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((options, oldValue, newValue) -> ApplicationContext.getInstance().getAlgorithmConfigBuilder().crossingType(newValue));

        inversionProbabilityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double probabilityValue = Double.parseDouble(newValue);

                if (probabilityValue > 1 || probabilityValue < 0) {
                    throw new IllegalArgumentException("Probability must be in range from 0 to 1");
                }

                inversionProbabilityTextField.setStyle(null);
                ApplicationContext.getInstance().getAlgorithmConfigBuilder().inversionProbability(probabilityValue);
            } catch (Exception e) {
                inversionProbabilityTextField.setStyle("-fx-text-box-border: red;");
            }
        });

        mutationProbabilityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double probabilityValue = Double.parseDouble(newValue);

                if (probabilityValue > 1 || probabilityValue < 0) {
                    throw new IllegalArgumentException("Probability must be in range from 0 to 1");
                }

                mutationProbabilityTextField.setStyle(null);
                ApplicationContext.getInstance().getAlgorithmConfigBuilder().mutationProbability(probabilityValue);
            } catch (Exception e) {
                mutationProbabilityTextField.setStyle("-fx-text-box-border: red;");
            }
        });

        crossingProbabilityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double probabilityValue = Double.parseDouble(newValue);

                if (probabilityValue > 1 || probabilityValue < 0) {
                    throw new IllegalArgumentException("Probability must be in range from 0 to 1");
                }

                crossingProbabilityTextField.setStyle(null);
                ApplicationContext.getInstance().getAlgorithmConfigBuilder().crossingProbability(probabilityValue);
            } catch (Exception e) {
                crossingProbabilityTextField.setStyle("-fx-text-box-border: red;");
            }
        });

        erasCountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int erasCount = Integer.parseInt(newValue);

                if (erasCount < 0) {
                    throw new IllegalArgumentException("Probability must be in range from 0 to 1");
                }

                erasCountTextField.setStyle(null);
                ApplicationContext.getInstance().getAlgorithmConfigBuilder().erasCount(erasCount);
            } catch (Exception e) {
                erasCountTextField.setStyle("-fx-text-box-border: red;");
            }
        });

        capacityField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double capacity = Double.parseDouble(newValue);

                if (capacity < 0) {
                    throw new IllegalArgumentException("Probability must be in range from 0 to 1");
                }

                capacityField.setStyle(null);
                ApplicationContext.getInstance().setCapacity(capacity);
            } catch (Exception e) {
                capacityField.setStyle("-fx-text-box-border: red;");
            }
        });

        isLookingForMaxCheckBox.indeterminateProperty().addListener((observableValue, oldValue, newValue) -> ApplicationContext.getInstance().getAlgorithmConfigBuilder().isMax(newValue));

        successionTypeComboBox.getItems().addAll(SuccessionType.values());
        successionTypeComboBox.getSelectionModel().select(0);
        selectionTypeComboBox.getItems().addAll(SelectionType.values());
        selectionTypeComboBox.getSelectionModel().select(0);
        crossingTypeComboBox.getItems().addAll(CrossingType.values());
        crossingTypeComboBox.getSelectionModel().select(0);
        inversionProbabilityTextField.setText("0.4");
        mutationProbabilityTextField.setText("0.3");
        crossingProbabilityTextField.setText("0.3");
        erasCountTextField.setText("100");
        isLookingForMaxCheckBox.setSelected(true);
    }

    @FXML
    public void handleGenerateItems() {
        Item[] items = new Item[14];
        ItemsSettings itemsSettings = new ItemsSettings(10, 10);

        for (int i = 0; i < items.length; i++) {
            double price = RANDOM.nextInt(itemsSettings.getMaxItemPrice() * 100 + 1) / 100d;
            double weight = RANDOM.nextInt(itemsSettings.getMaxItemWeight() * 100 + 1) / 100d;
            boolean isTaken = RANDOM.nextBoolean();
            items[i] = new Item(price, weight, isTaken);
        }

        ApplicationContext.getInstance().setItems(items);
        initializeItemsTableColumns();
        itemsTable.getItems().clear();
        itemsTable.getItems().addAll(items);
        exportItemsButton.setDisable(false);
    }

    @FXML
    public void importItems() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);

        if (file == null) {
            return;
        }

        String fileContent = readFile(file);
        Gson gson = new Gson();
        ItemBean[] itemsFromFile = gson.fromJson(fileContent, ItemBean[].class);
        Item[] items = new Item[itemsFromFile.length];

        for (int i = 0; i < itemsFromFile.length; i++) {
            items[i] = new Item(itemsFromFile[i], false);
        }

        if (items.length == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File empty");
            alert.setHeaderText("File empty");
            alert.setContentText("Provided file has no records. Please select file with data!");
            alert.showAndWait();
            return;
        }

        ApplicationContext.getInstance().setItems(items);
        initializeItemsTableColumns();
        itemsTable.getItems().clear();
        itemsTable.getItems().addAll(items);
        exportItemsButton.setDisable(true);
    }

    @FXML
    public void exportItems() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);

        try {
            Gson gson = new Gson();

            List<ItemBean> items = Arrays
                    .stream(ApplicationContext.getInstance().getItems())
                    .map(ItemBean::new)
                    .collect(Collectors.toList());

            String fileContent = gson.toJson(items);
            PrintWriter writer = new PrintWriter(file);
            writer.println(fileContent);
            writer.close();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("IO Error");
            alert.setHeaderText("Unable to save file");
            alert.setContentText("There is a problem with saving file at: " + file.getPath());
            showAlertWithException(alert, ex);
        }
    }

    private void initializeItemsTableColumns() {
        ObservableList<TableColumn<Item, ?>> columns = itemsTable.getColumns();
        columns.clear();
        TableColumn<Item, String> weight = new TableColumn<>("Weight");
        weight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        TableColumn<Item, String> price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Item, String> value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        columns.addAll(weight, price, value);
    }

    private String readFile(File file) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Path.of(file.getPath()), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("IO Error");
            alert.setHeaderText("Unable to read file");
            alert.setContentText("There was a problem with loading file content at: " + file.getPath());
            showAlertWithException(alert, e);
        }

        return contentBuilder.toString();
    }

    private void showAlertWithException(Alert alert, Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();
        Label label = new Label("The exception stacktrace was:");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

}
