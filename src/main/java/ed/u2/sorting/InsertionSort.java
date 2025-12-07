package ed.u2.sorting;

import util.SortingMetrics;

/**
 * @author MikelMZ : Miguel Armas
 */
public final class InsertionSort {

    public static <T extends Comparable<T>> SortingMetrics sort(T[] array) {
        return sort(array, false);
    }

    public static <T extends Comparable<T>> SortingMetrics sort(T[] array, boolean trace) {
        long comparisons = 0;
        long assignments = 0;
        int externalIterations = 0;
        int internalIterations = 0;
        String header = "=== INSERTION SORT ===\n";

        long startTime = System.nanoTime();

        for (int i = 1; i < array.length; i++) {
            externalIterations++;
            T value = array[i];
            assignments++;

            int j = i - 1;

            comparisons++;

            while (j >= 0 && array[j].compareTo(value) > 0) {
                internalIterations++;
                comparisons++;

                array[j + 1] = array[j];
                assignments++; // Desplazamiento
                j--;
            }
            // Corregir el conteo extra del while cuando j < 0
            if (j < 0) {
                comparisons--;
            }

            array[j + 1] = value;
            assignments++;

            if (trace) {
                SortingUtils.showCurrentState(header, array, externalIterations, internalIterations,
                        (int) comparisons, (int) assignments, i, j + 1, "Insertando");
            }
        }

        long endTime = System.nanoTime();

        if (trace) {
            SortingUtils.showFinalResult(header, array, externalIterations, internalIterations,
                    (int) comparisons, (int) assignments);
        }

        return new SortingMetrics(comparisons, assignments, (endTime - startTime));
    }
}
