package ed.u2.sorting;

import util.SortingMetrics;

/**
 * @author MikelMZ : Miguel Armas
 */
public final class SelectionSort {

    public static <T extends Comparable<T>> SortingMetrics sort(T[] array) {
        return sort(array, false);
    }

    public static <T extends Comparable<T>> SortingMetrics sort(T[] array, boolean trace) {
        long comparisons = 0;
        long assignments = 0;
        int externalIterations = 0;
        int internalIterations = 0;
        String header = "=== SELECTION SORT ===\n";

        long startTime = System.nanoTime();

        for (int i = 0; i < array.length; i++) {
            externalIterations++;
            int minIndex = i;

            for (int j = i + 1; j < array.length; j++) {
                internalIterations++;
                comparisons++;

                if (array[j].compareTo(array[minIndex]) < 0) { //< 0 busca el menor
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                T temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
                assignments += 3; 

                if (trace) {
                    SortingUtils.showCurrentState(header, array, externalIterations, internalIterations,
                            (int)comparisons, (int)assignments, i, minIndex, "Swap");
                }
            }
        }

        long endTime = System.nanoTime();

        if (trace) {
            SortingUtils.showFinalResult(header, array, externalIterations, internalIterations,
                    (int)comparisons, (int)assignments);
        }

        return new SortingMetrics(comparisons, assignments, (endTime - startTime));
    }
}