package ed.u2.searchs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author M16artz : Miguel Armas
 * @author RichardC022 : Richard Cajas
 * @author Mzero11 : Mateo Silva
 */
public class ArraySearch {

    public static <T extends Comparable<T>> int findFirst(T[] a, T key) {

        if (a == null || a.length == 0) return -1;

        for (int i = 0; i < a.length; i++) {
            // Usamos compareTo() para chequear la igualdad: compareTo(key) == 0
            if (a[i].compareTo(key) == 0) { 
                return i;
            }
        }
        return -1;
    }

    public static <T extends Comparable<T>> int findLast(T[] a, T key) {

        if (a == null || a.length == 0) return -1;

        int last = -1;
        for (int i = 0; i < a.length; i++) {
            // Usamos compareTo() para chequear la igualdad: compareTo(key) == 0
            if (a[i].compareTo(key) == 0) {
                last = i;
            }
        }
        return last;
    }

    public static <T> List<Integer> findAll(T[] a, Predicate<T> p) {
        List<Integer> results = new ArrayList<>();

        if (a == null) return results;

        for (int i = 0; i < a.length; i++) {
            // Se usa el predicado genérico Predicate<T>
            if (p.test(a[i])) { 
                results.add(i);
            }
        }
        return results;
    }

    public static <T extends Comparable<T>> int binarySearch(T[] array, T key) {
        if (array == null || array.length == 0) return -1;

        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            // Comparación: a[mid] == key -> compareTo(key) == 0
            if (array[mid].compareTo(key) == 0) {
                return mid;
            // Comparación: a[mid] < key -> compareTo(key) < 0
            } else if (array[mid].compareTo(key) < 0) {
                low = mid + 1;
            // Comparación: a[mid] > key -> compareTo(key) > 0
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

   public static <T extends Comparable<T>> int lowerBound(T[] array, T key) {
        if (array == null || array.length == 0) return -1;
        
        int low = 0;
        int high = array.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            
            // Comparación: arreglo[medio] < valor -> compareTo(valor) < 0
            if (array[mid].compareTo(key) < 0) { 
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        // Verificar si el índice encontrado realmente es el valor
        if (low < array.length && array[low].compareTo(key) == 0) {
            return low;
        }
        return -1;
    }

    public static <T extends Comparable<T>> int upperBound(T[] array, T key) {
        if (array == null || array.length == 0) return -1;

        int low = 0;
        int high = array.length;

        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (array[mid].compareTo(key) <= 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        if (low > 0 && array[low - 1].compareTo(key) == 0) {
            return low - 1; 
        }
        return -1;
    }
    
    /**
     * Búsqueda Secuencial Clásica con contador de Trazas (Comparaciones).
     * Sirve para comparar rendimiento contra el Centinela.
     */
    public static int busquedaSecuencialTrazas(int[] a, int key) {
        if (a == null || a.length == 0) return -1;
        
        int comparaciones = 0;
        
        for (int i = 0; i < a.length; i++) {
            // Contamos la verificación del bucle (i < a.length) que acaba de pasar
            comparaciones++; 
            
            // Contamos la verificación del dato (a[i] == key)
            comparaciones++; 
            
            if (a[i] == key) {
                System.out.println("   [Secuencial] Comparaciones Totales (Bucle + If): " + comparaciones);
                return i; 
            }
        }
        
        // Si llegamos aquí, el bucle hizo una última comparación (i < a.length)
        comparaciones++; 
        
        System.out.println("   [Secuencial] Comparaciones Totales (Bucle + If): " + comparaciones);
        return -1; 
    }
    
    public static int busquedaPorCentinelaTrazas(int[] arreglo, int valor) {
        if (arreglo == null || arreglo.length == 0) return -1;

        int[] auxiliar = new int[(arreglo.length + 1)];
        int comparaciones = 0;
        
        for (int i = 0; i < arreglo.length; i++) {
            auxiliar[i] = arreglo[i];
        }
        auxiliar[arreglo.length] = valor; 
        
        int i = 0;
        comparaciones++; 
        while (auxiliar[i] != valor) {
            comparaciones++;
            i++;
        }
        
        System.out.println("   [Centinela] Comparaciones realizadas: " + comparaciones);
        
        return i;
    }
}
