package pl.wilenskid.alghoritm.selection.impl;

import pl.wilenskid.alghoritm.selection.SelectionMethod;
import pl.wilenskid.core.Knapsack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static pl.wilenskid.utils.SubjectComparator.getSubjectComparator;

public class TournamentNoReturnSelectionMethod extends SelectionMethod {

    public TournamentNoReturnSelectionMethod() {
        super();
    }

    @Override
    public List<Knapsack> select(List<Knapsack> knapsacks, boolean isMax) {
        Comparator<Knapsack> subjectComparator = getSubjectComparator(isMax);
        List<Knapsack> subjectsCpy = new ArrayList<>(knapsacks);
        List<Knapsack> winners = new ArrayList<>();

        while (winners.size() != knapsacks.size()) {
            List<Knapsack> tournamentGroup = new ArrayList<>();

            while (tournamentGroup.size() != 2) {
                int index = rnd.nextInt(subjectsCpy.size());
                tournamentGroup.add(subjectsCpy.get(index));
            }

            tournamentGroup.sort(subjectComparator);
            Knapsack winner = tournamentGroup.get(0);
            winners.add(winner);
            subjectsCpy.remove(winner);
        }

        return winners;
    }
}
