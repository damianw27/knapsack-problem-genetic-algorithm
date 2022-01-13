package pl.wilenskid.alghoritm.crossing.impl;

import pl.wilenskid.alghoritm.crossing.CrossingMethod;
import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.KnapsackPair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class BaseCrossingMethod implements CrossingMethod {
    protected final Random rnd;

    protected BaseCrossingMethod() {
        this.rnd = new Random();
    }

    public final List<Knapsack> cross(List<Knapsack> knapsacks, double probability) {
        List<Knapsack> ableToCross = getAbleToCross(knapsacks, probability);

        List<Knapsack> notableToCross = knapsacks
                .stream()
                .filter(element -> !ableToCross.contains(element))
                .collect(Collectors.toList());

        List<KnapsackPair> knapsackPairs = getSubjectPairsList(ableToCross);

        return getResult(ableToCross, notableToCross, knapsackPairs);
    }

    protected abstract List<Knapsack> getResult(List<Knapsack> ableToCross,
                                                List<Knapsack> notableToCross,
                                                List<KnapsackPair> knapsackPairs);

    public final Knapsack[] cross(Knapsack parent1, Knapsack parent2, int... indexes) {
        String firstParentBinaryString = parent1.getBinaryString();
        String secondParentBinaryString = parent2.getBinaryString();
        StringBuilder firstResultBinaryString = new StringBuilder();
        StringBuilder secondResultBinaryString = new StringBuilder();

        Arrays.sort(indexes);

        int indexOfChange = 0;
        boolean flip = false;

        for (int index = 0; index < firstParentBinaryString.length(); index++) {
            if (shouldFlip(index, indexOfChange, flip, indexes)) {
                indexOfChange++;
                flip = !flip;
            }

            if (flip) {
                firstResultBinaryString.append(secondParentBinaryString.charAt(index));
                secondResultBinaryString.append(firstParentBinaryString.charAt(index));
            } else {
                firstResultBinaryString.append(firstParentBinaryString.charAt(index));
                secondResultBinaryString.append(secondParentBinaryString.charAt(index));
            }

        }

        KnapsackPair parents = new KnapsackPair(parent1, parent2);

        return new Knapsack[]{
                new Knapsack(parent1, firstResultBinaryString.toString(), parents),
                new Knapsack(parent2, secondResultBinaryString.toString(), parents)
        };
    }

    public final List<Knapsack> getAbleToCross(List<Knapsack> knapsacks, double probability) {
        List<Knapsack> tmpKnapsack = new ArrayList<>(knapsacks);
        List<Knapsack> subjectsToCross = tmpKnapsack
                .stream()
                .filter(subject -> rnd.nextDouble() <= probability)
                .collect(Collectors.toList());

        if (subjectsToCross.size() % 2 != 0) {
            subjectsToCross.remove(rnd.nextInt(subjectsToCross.size()));
        }

        return subjectsToCross;
    }

    protected final List<KnapsackPair> getSubjectPairsList(List<Knapsack> knapsacks) {
        List<Knapsack> subjectsCpy = new ArrayList<>(knapsacks);
        List<KnapsackPair> pairs = new ArrayList<>();

        Collections.shuffle(subjectsCpy);

        for (int i = 0; i < knapsacks.size() / 2; i++) {
            Knapsack knapsack1 = subjectsCpy.get(i * 2);
            Knapsack knapsack2 = subjectsCpy.get(i * 2 + 1);
            pairs.add(new KnapsackPair(knapsack1, knapsack2));
        }

        return pairs;
    }

    private boolean shouldFlip(int index, int indexOfChange, boolean flip, int... indexes) {
        if (indexOfChange < indexes.length) {
            if (flip) {
                return index >= indexes[indexOfChange];
            } else {
                return index >= indexes[indexOfChange] + 1;
            }
        } else {
            return false;
        }
    }
}
