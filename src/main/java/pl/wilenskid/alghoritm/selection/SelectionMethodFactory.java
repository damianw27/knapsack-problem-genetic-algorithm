package pl.wilenskid.alghoritm.selection;

import pl.wilenskid.alghoritm.selection.impl.*;
import pl.wilenskid.generic.GenericFactory;

public class SelectionMethodFactory extends GenericFactory<SelectionType, SelectionMethod> {

    public SelectionMethodFactory() {
        super();
        this.context.put(SelectionType.RANKING_METHOD, new RankingSelectionMethod());
        this.context.put(SelectionType.TOURNAMENT_METHOD, new TournamentSelectionMethod());
        this.context.put(SelectionType.ROULETTE_METHOD, new RouletteSelectionMethod());
        this.context.put(SelectionType.TOURNAMENT_NO_RETURN_METHOD, new TournamentNoReturnSelectionMethod());
        this.context.put(SelectionType.TOURNAMENT_3_METHOD, new Tournament3SelectionMethod());
    }

}
