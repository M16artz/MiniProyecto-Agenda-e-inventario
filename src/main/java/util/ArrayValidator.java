package util;

public class ArrayValidator {
    
    public static <T extends Comparable<T>> boolean isSorted(T[] array) {
        if (array == null || array.length <= 1) {
            return true;
        }
        
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }
    
    public static <T extends Comparable<T>> void validateSortedForBinarySearch(T[] array) {
        if (!isSorted(array)) {
            throw new IllegalStateException("El arreglo no está ordenado. " +
                "La búsqueda binaria requiere un arreglo ordenado previamente.");
        }
    }
}