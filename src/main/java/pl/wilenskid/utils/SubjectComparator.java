package pl.wilenskid.utils;

import pl.wilenskid.core.Knapsack;

import java.util.Comparator;

public class SubjectComparator {
    public static Comparator<Knapsack> getSubjectComparator(boolean max) {
        return (current, next) -> max ? current.compareTo(next) : -current.compareTo(next);
    }
}
