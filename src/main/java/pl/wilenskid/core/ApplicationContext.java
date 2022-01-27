package pl.wilenskid.core;

import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import pl.wilenskid.alghoritm.AlgorithmConfig;
import pl.wilenskid.ui.TabDetails;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ApplicationContext {

    public static ApplicationContext INSTANCE;

    private final AlgorithmConfig.AlgorithmConfigBuilder algorithmConfigBuilder;

    private TabDetails tabDetails;
    private Item[] items;
    private Map<Integer, List<Knapsack>> results;
    private int selectedEraIndex = 0;
    private int selectedBagIndex = 0;
    private double capacity = 10d;

    private ApplicationContext() {
        this.algorithmConfigBuilder = AlgorithmConfig.builder();
    }

    public static ApplicationContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApplicationContext();
        }

        return INSTANCE;
    }

}
