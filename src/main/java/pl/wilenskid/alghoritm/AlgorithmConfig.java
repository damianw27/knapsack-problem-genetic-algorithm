package pl.wilenskid.alghoritm;

import lombok.Builder;
import lombok.Getter;
import pl.wilenskid.alghoritm.crossing.CrossingType;
import pl.wilenskid.alghoritm.selection.SelectionType;
import pl.wilenskid.alghoritm.succession.SuccessionType;

@Getter
@Builder
public class AlgorithmConfig {
    private final SuccessionType successionType;
    private final SelectionType selectionType;
    private final CrossingType crossingType;
    private final double inversionProbability;
    private final double mutationProbability;
    private final double crossingProbability;
    private final int erasCount;
    private final boolean isMax;
}
