package pl.wilenskid.alghoritm;

import lombok.Getter;
import pl.wilenskid.alghoritm.crossing.CrossingMethodFactory;
import pl.wilenskid.alghoritm.encoding.EncodingMethodFactory;
import pl.wilenskid.alghoritm.manipulation.ManipulationMethodFactory;
import pl.wilenskid.alghoritm.range.RangeMethodFactory;
import pl.wilenskid.alghoritm.selection.SelectionMethodFactory;
import pl.wilenskid.alghoritm.succession.SuccessionMethodFactory;

@Getter
public class AlgorithmsFactories {

    private final CrossingMethodFactory crossingMethodFactory;
    private final EncodingMethodFactory encodingMethodFactory;
    private final ManipulationMethodFactory manipulationMethodFactory;
    private final RangeMethodFactory rangeMethodFactory;
    private final SelectionMethodFactory selectionMethodFactory;
    private final SuccessionMethodFactory successionMethodFactory;

    public AlgorithmsFactories() {
        this.crossingMethodFactory = new CrossingMethodFactory();
        this.encodingMethodFactory = new EncodingMethodFactory();
        this.manipulationMethodFactory = new ManipulationMethodFactory();
        this.rangeMethodFactory = new RangeMethodFactory();
        this.selectionMethodFactory = new SelectionMethodFactory();
        this.successionMethodFactory = new SuccessionMethodFactory();
    }

    public static AlgorithmsFactories getInstance() {
        return new AlgorithmsFactories();
    }
}
