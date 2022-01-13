package pl.wilenskid.alghoritm.manipulation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManipulationContext {
    private ManipulationType manipulationType;
    private double probability;
}
