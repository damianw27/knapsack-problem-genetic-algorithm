package pl.wilenskid.alghoritm.selection.impl;

import pl.wilenskid.alghoritm.selection.SelectionMethod;
import pl.wilenskid.core.Knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static pl.wilenskid.utils.SubjectComparator.getSubjectComparator;

public class RankingSelectionMethod extends SelectionMethod {

    public RankingSelectionMethod() {
        super();
    }

    @Override
    public List<Knapsack> select(List<Knapsack> subjects, boolean isMax) {
        Comparator<Knapsack> subjectComparator = getSubjectComparator(isMax);
        List<Knapsack> winners = new ArrayList<>();
        int testGroupSize = (int) (subjects.size() * TEST_GROUP_SIZE_FACTOR);

        Collections.shuffle(subjects);

        while (winners.size() < subjects.size()) {
            List<Knapsack> testGroup = new ArrayList<>();

            for (int i = 0; i < testGroupSize; i++) {
                int rndIndex = rnd.nextInt(subjects.size());
                testGroup.add(subjects.get(rndIndex));
            }

            if (testGroup.size() == 0) {
                continue;
            }

            testGroup.sort(subjectComparator);
            winners.add(testGroup.get(0));
        }

        return winners;
    }
}
