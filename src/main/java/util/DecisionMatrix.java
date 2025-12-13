package util;

public class DecisionMatrix {
    
    public enum DataCharacteristics {
        RANDOM, NEARLY_SORTED, REVERSE_SORTED, MANY_DUPLICATES, SMALL, LARGE
    }
    
    public static String recommendSortingAlgorithm(DataCharacteristics characteristics, int size) {
        StringBuilder recommendation = new StringBuilder();
        recommendation.append("Recomendación basada en:\n");
        recommendation.append("- Características: ").append(characteristics).append("\n");
        recommendation.append("- Tamaño: ").append(size).append("\n\n");
        
        switch (characteristics) {
            case NEARLY_SORTED:
                recommendation.append("✓ INSERTION SORT (O(n) en casi ordenados)\n");
                recommendation.append("✗ Evitar SELECTION (siempre O(n²))\n");
                recommendation.append("✗ Evitar BUBBLE (innecesario si ya casi ordenado)\n");
                break;
                
            case REVERSE_SORTED:
                recommendation.append("✓ SELECTION SORT (movimientos fijos O(n))\n");
                recommendation.append("✗ Evitar INSERTION (O(n²) en inversos)\n");
                recommendation.append("✗ Evitar BUBBLE (O(n²) y muchos swaps)\n");
                break;
                
            case RANDOM:
                if (size < 50) {
                    recommendation.append("✓ INSERTION SORT (bueno para pequeños)\n");
                } else {
                    recommendation.append("✓ SELECTION SORT (predecible O(n²))\n");
                    recommendation.append("○ BUBBLE SORT (peor caso igual que selección)\n");
                }
                break;
                
            case MANY_DUPLICATES:
                recommendation.append("✓ INSERTION SORT (estable y eficiente con dups)\n");
                recommendation.append("✓ SELECTION SORT (también estable)\n");
                recommendation.append("✗ BUBBLE puede hacer swaps innecesarios\n");
                break;
                
            default:
                recommendation.append("SELECTION SORT para consistencia\n");
        }
        
        return recommendation.toString();
    }
    
    public static String recommendSearchAlgorithm(boolean isSorted, int size, 
            boolean needFirst, boolean needLast, boolean needRange) {
        
        StringBuilder recommendation = new StringBuilder();
        recommendation.append("Recomendación de búsqueda:\n");
        
        if (isSorted && size > 10) {
            recommendation.append("✓ BINARY SEARCH (O(log n) - rápido)\n");
            if (needFirst || needLast) {
                recommendation.append("✓ LOWER/UPPER BOUND para duplicados\n");
            }
            if (needRange) {
                recommendation.append("✓ BÚSQUEDA POR RANGO usando binary search\n");
            }
        } else {
            recommendation.append("✓ SEQUENTIAL SEARCH (O(n) - arreglo no ordenado)\n");
            if (needFirst) {
                recommendation.append("✓ findFirst()\n");
            }
            if (needLast) {
                recommendation.append("✓ findLast() o centinela\n");
            }
            if (size < 100) {
                recommendation.append("✓ SENTINEL SEARCH para evitar comparación de índice\n");
            }
        }
        
        recommendation.append("\nPara Linked Lists (SLL):\n");
        recommendation.append("✓ Solo búsqueda secuencial (O(n))\n");
        recommendation.append("✗ No usar binary search en SLL\n");
        
        return recommendation.toString();
    }
}