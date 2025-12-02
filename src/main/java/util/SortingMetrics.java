package util;

/**
 * @author MikelMZ : Miguel Armas
 * Clase para retornar las métricas de rendimiento.
 */
public class SortingMetrics {
    public long comparisons;
    public long assignments; //swaps
    public long executionTimeNs;

    public SortingMetrics(long comparisons, long assignments, long executionTimeNs) {
        this.comparisons = comparisons;
        this.assignments = assignments;
        //divido para presentar en microsegundos y no en nanosegundos
        this.executionTimeNs = executionTimeNs;
    }

    @Override
    public String toString() {
        return String.format("Comp: %d | Mov: %d | Tiempo: %d \u00B5s", comparisons, assignments, executionTimeNs);
    }
}