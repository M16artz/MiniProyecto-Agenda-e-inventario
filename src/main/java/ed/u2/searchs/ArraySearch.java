package ed.u2.searchs;

import ed.u2.model.Appointment;
import java.time.LocalDateTime;
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

        if (a == null || a.length == 0) {
            return -1;
        }

        for (int i = 0; i < a.length; i++) {
            // Usamos compareTo() para chequear la igualdad: compareTo(key) == 0
            if (a[i].compareTo(key) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static <T extends Comparable<T>> int findLast(T[] a, T key) {

        if (a == null || a.length == 0) {
            return -1;
        }

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

        if (a == null) {
            return results;
        }

        for (int i = 0; i < a.length; i++) {
            // Se usa el predicado genérico Predicate<T>
            if (p.test(a[i])) {
                results.add(i);
            }
        }
        return results;
    }

    public static <T extends Comparable<T>> int binarySearch(T[] array, T key) {
        if (array == null || array.length == 0) {
            return -1;
        }

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
        if (array == null || array.length == 0) {
            return -1;
        }

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
        if (array == null || array.length == 0) {
            return -1;
        }

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
     * Busca citas dentro de un rango de fechas (inclusive)
     * Precondición: el arreglo debe estar ordenado por fecha
     */
    public static List<Appointment> findAppointmentsInRange(Appointment[] appointments, 
            LocalDateTime start, LocalDateTime end) {
        
        List<Appointment> result = new ArrayList<>();
        
        if (appointments == null || start == null || end == null || start.isAfter(end)) {
            return result;
        }
        
        // Usar binary search para encontrar el límite inferior
        int low = findLowerBoundForRange(appointments, start);
        if (low == -1) return result;
        
        // Recorrer desde low hasta encontrar el límite superior
        for (int i = low; i < appointments.length; i++) {
            LocalDateTime currentDate = appointments[i].getFechaHora();
            
            if (currentDate.isBefore(start)) {
                continue;
            }
            
            if (currentDate.isAfter(end)) {
                break;
            }
            
            result.add(appointments[i]);
        }
        
        return result;
    }
    
    private static int findLowerBoundForRange(Appointment[] appointments, LocalDateTime start) {
        int low = 0;
        int high = appointments.length;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            LocalDateTime midDate = appointments[mid].getFechaHora();
            
            if (midDate.compareTo(start) < 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        
        return (low < appointments.length) ? low : -1;
    }
    
    /**
     * Búsqueda por Centinela para arreglos genéricos usando Predicate
     */
    public static <T> int sentinelSearch(T[] array, Predicate<T> condition) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int lastIndex = array.length - 1;
        T lastElement = array[lastIndex];

        // Si el último elemento cumple la condición, retornar su índice
        if (condition.test(lastElement)) {
            return lastIndex;
        }

        // Guardar el último elemento original y colocar centinela
        array[lastIndex] = lastElement; // Se mantiene igual porque ya verificamos

        int i = 0;
        while (!condition.test(array[i])) {
            i++;
        }

        // Restaurar el último elemento
        array[lastIndex] = lastElement;

        // Si i llegó al último índice, no se encontró (excepto si era el centinela)
        return (i == lastIndex) ? -1 : i;
    }

    /**
     * Búsqueda por Centinela para objetos Comparable
     */
    public static <T extends Comparable<T>> int sentinelSearchComparable(T[] array, T key) {
        return sentinelSearch(array, element -> element.compareTo(key) == 0);
    }

}
