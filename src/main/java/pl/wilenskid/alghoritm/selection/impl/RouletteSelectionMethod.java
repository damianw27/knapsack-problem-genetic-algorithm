package pl.wilenskid.alghoritm.selection.impl;

import pl.wilenskid.alghoritm.selection.SelectionMethod;
import pl.wilenskid.core.Knapsack;
import pl.wilenskid.core.KnapsackWithProbability;

import java.util.ArrayList;
import java.util.List;

public class RouletteSelectionMethod extends SelectionMethod {

    private final static double A = 10f;
    private final static double OMEGA = 20f * Math.PI;

    public RouletteSelectionMethod() {
        super();
    }

    @Override
    public List<Knapsack> select(List<Knapsack> knapsacks, boolean isMax) {
        double F = knapsacks
                .stream()
                .map(knapsack -> calculateSumPart(knapsack.calculateAdaptationValue()))
                .mapToDouble(value -> value)
                .sum();

        List<KnapsackWithProbability> subjectsWithProbability = isMax
                ? getProbabilitiesMax(knapsacks, F)
                : getProbabilitiesMin(knapsacks, F);

        List<Knapsack> result = new ArrayList<>();

        while (result.size() != knapsacks.size()) {
            double r = (rnd.nextDouble() + (1f - rnd.nextDouble())) / 2f;

            for (int i = 0; i < subjectsWithProbability.size(); i++) {
                Knapsack knapsack = subjectsWithProbability.get(i).getSubject();
                double currentDistribution = subjectsWithProbability.get(i).getDistribution();

                if (i == 0 && r <= currentDistribution) {
                    result.add(knapsack);
                    continue;
                }

                if (i - 1 < 0) {
                    continue;
                }

                double lastDistribution = subjectsWithProbability.get(i - 1).getDistribution();

                if (lastDistribution < r && r <= currentDistribution) {
                    result.add(knapsack);
                }
            }
        }

        return result;
    }

    private List<KnapsackWithProbability> getProbabilitiesMax(List<Knapsack> subjects, double F) {
        List<KnapsackWithProbability> subjectsWithProbability = new ArrayList<>();
        double[] probabilities = new double[subjects.size()];

        for (int subjectIndex = 0; subjectIndex < subjects.size(); subjectIndex++) {
            Knapsack subject = subjects.get(subjectIndex);
            double subjectEvaluation = subject.calculateAdaptationValue();
            double distribution = 0;
            double sumPart = calculateSumPart(subjectEvaluation);

            probabilities[subjectIndex] = sumPart / F;

            for (int probabilityIndex = 0; probabilityIndex < subjectIndex + 1; probabilityIndex++) {
                distribution += probabilities[probabilityIndex];
            }

            subjectsWithProbability.add(new KnapsackWithProbability(subject, probabilities[subjectIndex], distribution));
        }

        return subjectsWithProbability;
    }

    private List<KnapsackWithProbability> getProbabilitiesMin(List<Knapsack> subjects, double F) {
        List<KnapsackWithProbability> subjectsWithProbability = new ArrayList<>();

        double minF = F;
        double maxF = 0;

        for (Knapsack knapsack : subjects) {
            double subjectEvaluation = knapsack.calculateAdaptationValue();
            minF = Math.min(minF, subjectEvaluation);
            maxF = Math.max(maxF, subjectEvaluation);
        }

        double[] probabilities = new double[subjects.size()];

        for (int subjectIndex = 0; subjectIndex < subjects.size(); subjectIndex++) {
            Knapsack subject = subjects.get(subjectIndex);
            double subjectEvaluation = subject.calculateAdaptationValue();
            double distribution = 0;
            double sumPart = calculateSumPart((maxF - subjectEvaluation) + minF);

            probabilities[subjectIndex] = sumPart / F;

            for (int probabilityIndex = 0; probabilityIndex < subjectIndex + 1; probabilityIndex++) {
                distribution += probabilities[probabilityIndex];
            }

            subjectsWithProbability.add(new KnapsackWithProbability(subject, probabilities[subjectIndex], distribution));
        }

        return subjectsWithProbability;
    }

    private double calculateSumPart(double x) {
        return Math.pow(x, 2) - A * Math.cos(OMEGA * x);
    }

}
