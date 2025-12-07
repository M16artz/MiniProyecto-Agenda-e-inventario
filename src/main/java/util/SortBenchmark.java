package util;

import ed.u2.sorting.BubbleSort;
import ed.u2.sorting.InsertionSort;
import ed.u2.sorting.SelectionSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortBenchmark {
    public static <T extends Comparable<T>> SortingMetrics runMedian(T[] original, String algorithmName) {

        List<SortingMetrics> runs = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            T[] copy = Arrays.copyOf(original, original.length);

            SortingMetrics m;

            switch (algorithmName.toLowerCase()) {
                case "bubble":
                    m = BubbleSort.sort(copy, false);
                    break;
                case "insertion":
                    m = InsertionSort.sort(copy, false);
                    break;
                case "selection":
                default:
                    m = SelectionSort.sort(copy, false);
                    break;
            }

            runs.add(m);
        }

        // DESCARTAR LAS PRIMERAS 3
        runs = runs.subList(3, 10);

        // ORDENAR POR TIEMPO
        runs.sort(Comparator.comparingLong(SortingMetrics::getExecutionTimeNs));

        // MEDIANA
        return runs.get(runs.size()/2);
    }
}
