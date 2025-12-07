package ed.u2.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author MikelMZ : Miguel Armas
 */
public class SortingUtils {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Convierte int[] (primitivo) a Integer[] (Objeto) para usar con los Sorts Genéricos.
     */
    public static Integer[] toObjectArray(int[] primitiveArray) {
        return Arrays.stream(primitiveArray).boxed().toArray(Integer[]::new);
    }

    /**
     * Genera una representación visual del arreglo.
     * Elige si debe dibujar barras (números) o lista (objetos).
     */
    public static <T> String arrayToString(T[] array) {
        if (array == null || array.length == 0) {
            return "Array vacío";
        }

        // Detectar si es un arreglo numérico para dibujar barras
        boolean isNumeric = array[0] instanceof Number;

        if (isNumeric) {
            return renderBars(array);
        } else {
            return renderList(array);
        }
    }

    private static <T> String renderBars(T[] array) {
        StringBuilder bars = new StringBuilder();
        
        // Encontrar el valor máximo para escalar las barras
        double maxValue = 1;
        for (T item : array) {
            if (item instanceof Number) {
                double val = ((Number) item).doubleValue();
                if (val > maxValue) maxValue = val;
            }
        }

        int maxBarWidth = 35;
        int maxValueWidth = String.valueOf((int) maxValue).length();

        for (int i = 0; i < array.length; i++) {
            int value = ((Number) array[i]).intValue();
            int barLength = (int) ((value * maxBarWidth) / maxValue);

            String bar = "█".repeat(Math.max(1, barLength));
            String valueStr = String.format("(%" + maxValueWidth + "d)", value);

            bars.append(String.format("%2d: %-" + maxBarWidth + "s %s",
                    i, bar, valueStr))
                    .append("\n");
        }
        return bars.toString();
    }

    // Lógica para objetos genéricos (Citas, Pacientes, etc.)
    private static <T> String renderList(T[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(String.format("%2d: %s\n", i, array[i].toString()));
        }
        return sb.toString();
    }

    public static void wait(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // --- MÉTODOS DE VISUALIZACIÓN GENÉRICOS (Unified) ---

    public static <T> void showCurrentState(String header, T[] array, int externalIt,
            int internalIt, int comp, int assign,
            int from, int to, String operation) {
        
        int totalIterations = externalIt + internalIt;
        int totalOperations = comp + assign;

        System.out.print("\n".repeat(30) + header
                + "Iteraciones: " + totalIterations + " (Ext: " + externalIt + ", Int: " + internalIt + ")"
                + "\nOperaciones: " + totalOperations + " (Comp: " + comp + ", Asig: " + assign + ")"
                + "\nIndices: " + from + " → " + to + "\n\n"
                + SortingUtils.arrayToString(array) // elige si usa barras o texto
                + "\n" + operation + ": " + from + " → " + to);
        System.out.flush();

        SortingUtils.wait(500); // pausa para visualizar
    }

    public static <T> void showFinalResult(String header, T[] array, int externalIt,
            int internalIt, int comp, int assign) {
        
        int totalIterations = externalIt + internalIt;
        int totalOperations = comp + assign;

        System.out.print("\n".repeat(30) + header
                + "=== COMPLETADO ===\n\n"
                + SortingUtils.arrayToString(array)
                + "\n¡Array completamente ordenado!"
                + "\n\n--- ESTADÍSTICAS ---"
                + "\nIteraciones del bucle externo: " + externalIt
                + "\nIteraciones del bucle interno: " + internalIt
                + "\nTotal iteraciones: " + totalIterations
                + "\nComparaciones realizadas: " + comp
                + "\nAsignaciones realizadas: " + assign
                + "\nTotal operaciones: " + totalOperations + "\n");
        System.out.flush();
    }

    public static int[] inputManualArray() {
        List<Integer> temp = new ArrayList<>();
        System.out.println("--- INGRESO MANUAL ---");
        System.out.println("Ingrese entre 2 y 15 números enteros. Escriba 'N' para terminar.");
        int count = 0;

        while (count < 15) {
            System.out.print("Valor " + (count + 1) + ": ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("N")) {
                if (count >= 2) break;
                else {
                    System.out.println("Debe ingresar al menos 2 números antes de terminar.");
                    continue;
                }
            }

            try {
                int number = Integer.parseInt(input);
                temp.add(number);
                count++;
                if (count == 15) {
                    System.out.println("Límite alcanzado.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número o 'N'.");
            }
        }

        if (temp.size() < 2) {
            System.out.println("Se requieren al menos 2 elementos. Usando valores por defecto.");
            return new int[]{5, 2, 8, 1, 9};
        }

        return temp.stream().mapToInt(i -> i).toArray();
    }

    public static int getIntInput(int min, int max) {
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.print("Entrada inválida. Por favor, ingrese un número: ");
                scanner.next();
            }
            int input = scanner.nextInt();
            if (input >= min && input <= max) return input;
            System.out.print("Número fuera de rango (" + min + "-" + max + "): ");
        }
    }
}