package pl.wilenskid.alghoritm;

import lombok.Builder;
import lombok.Getter;
import pl.wilenskid.core.Item;
import pl.wilenskid.core.Knapsack;

import java.util.List;

@Getter
@Builder
public class GeneralPopulation {
    private final Item[] basicItems;
    private final List<List<Knapsack>> populations;
    private final AlgorithmConfig algorithmConfig;
}
