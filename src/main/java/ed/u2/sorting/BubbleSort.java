package ed.u2.sorting;

import util.SortingMetrics;

/**
 * @author MikelMZ : Miguel Armas
 */
public final class BubbleSort {

    public static <T extends Comparable<T>> SortingMetrics sort(T[] array) {
        return sort(array, false);
    }

    public static <T extends Comparable<T>> SortingMetrics sort(T[] array, boolean trace) {
        int externalIterations = 0;
        int internalIterations = 0;
        int comparisons = 0;
        int assignments = 0;

        String header = "=== BUBBLE SORT (Generic) ===\n";
        long startTime = System.nanoTime();
        for (int i = 0; i < array.length; i++) {
            externalIterations++;
            boolean swapped = false;

            if (trace) {
                SortingUtils.showCurrentState(header, array, externalIterations, internalIterations,
                        comparisons, assignments, i, 0, "Inicio pasada " + (i + 1));
            }

            for (int j = 0; j < array.length - 1 - i; j++) {
                internalIterations++;
                comparisons++;

                if (array[j].compareTo(array[j + 1]) > 0) {

                    T temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;

                    assignments += 3;
                    swapped = true;

                    if (trace) {
                        SortingUtils.showCurrentState(header, array, externalIterations, internalIterations,
                                comparisons, assignments, j, j + 1, "Intercambiando");
                    }
                }
            }

            if (trace) {
                SortingUtils.showCurrentState(header, array, externalIterations, internalIterations,
                        comparisons, assignments, i, -1, "Fin pasada " + (i + 1) + (swapped ? "" : " - Sin cambios"));
            }

            if (!swapped) {
                break;
            }
        }

        long endTime = System.nanoTime();
        return new SortingMetrics(comparisons, assignments, (endTime - startTime));
    }

}
