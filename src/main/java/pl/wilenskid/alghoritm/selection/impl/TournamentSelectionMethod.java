package pl.wilenskid.alghoritm.selection.impl;

import pl.wilenskid.alghoritm.selection.SelectionMethod;
import pl.wilenskid.core.Knapsack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static pl.wilenskid.utils.SubjectComparator.getSubjectComparator;

public class TournamentSelectionMethod extends SelectionMethod {

    public TournamentSelectionMethod() {
        super();
    }

    @Override
    public List<Knapsack> select(List<Knapsack> subjects, boolean isMax) {
        Comparator<Knapsack> subjectComparator = getSubjectComparator(isMax);
        List<Knapsack> subjectsCpy = new ArrayList<>(subjects);
        List<Knapsack> winners = new ArrayList<>();

        while (winners.size() != subjects.size()) {
            List<Knapsack> tournamentGroup = new ArrayList<>();

            while (tournamentGroup.size() != 2) {
                int index = rnd.nextInt(subjectsCpy.size());
                tournamentGroup.add(subjectsCpy.get(index));
            }

            tournamentGroup.sort(subjectComparator);
            winners.add(tournamentGroup.get(0));
        }

        return winners;
    }
}
