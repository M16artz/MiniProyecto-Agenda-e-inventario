package ed.u2.sorting;

import ed.u2.model.Appointment;
import ed.u2.model.Item;
import ed.u2.model.Patient;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import ed.u2.model.PatientList;
import ed.u2.searchs.ArraySearch;
import util.CsvLoader;
import util.SortingMetrics;

/**
 * Clase principal para ejecutar la comparativa de ordenamiento. Implementa
 * metodología científica (R=10, Mediana, Warm-up).
 *
 * @author MikelMZ : Miguel Armas
 */
public class SortingDemo {

    // Rutas de los archivos
    private static final String PATH_CITAS_100 = "src/main/java/data/citas_100.csv";
    private static final String PATH_CITAS_CASI = "src/main/java/data/citas_100_casi_ordenadas.csv";
    private static final String PATH_PACIENTES = "src/main/java/data/pacientes_500.csv";
    private static final String PATH_INVENTARIO = "src/main/java/data/inventario_500_inverso.csv";

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Generar datasets al inicio para asegurar que existan
            System.out.println("Verificando datasets...");

            boolean continuar = true;
            while (continuar) {
                printMenu();
                int opcion = getValidInt("Seleccione una opción: ");

                switch (opcion) {
                    case 1:
                        runAllTests();
                        break;
                    case 2:
                        runTest1();
                        break;
                    case 3:
                        runTest2();
                        break;
                    case 4:
                        runTest3();
                        break;
                    case 5:
                        runTest4();
                        break;
                    case 6:
                        menuPacientes();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }

        } catch (Exception e) {
            System.err.println("Error fatal en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- MÉTODOS DE MENÚ Y UI ---
    private static void printMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("      BENCHMARK DE ORDENAMIENTO");
        System.out.println("=".repeat(40));
        System.out.println("1. Probar TODOS los Datasets");
        System.out.println("2. Dataset 1: Citas (Aleatorio - 100)");
        System.out.println("3. Dataset 2: Citas (Casi Ordenado - 100)");
        System.out.println("4. Dataset 3: Pacientes (Repetidos - 500)");
        System.out.println("5. Dataset 4: Inventario (Inverso - 500)");
        System.out.println("6. Módulo Pacientes (SLL)");
        System.out.println("0. Salir");
        System.out.println("-".repeat(40));
    }

    private static int getValidInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor ingrese un número.");
            System.out.print(prompt);
            scanner.next(); // Limpiar buffer
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea restante
        return num;
    }

    // --- LOGICA DE EJECUCIÓN DE PRUEBAS ---
    private static void runAllTests() {
        System.out.println("\n>>> EJECUTANDO TODOS LOS CASOS DE PRUEBA <<<");
        runTest1();
        runTest2();
        runTest3();
        runTest4();
    }

    private static void runTest1() {
        System.out.println("\n--- TEST 1: Citas (100 registros, Aleatorio) ---");
        try {
            Appointment[] citas = CsvLoader.loadCitas(PATH_CITAS_100);
            ejecutarPruebas(citas, "Citas Aleatorias");
        } catch (Exception e) {
            handleError(e, PATH_CITAS_100);
        }
    }

    private static void runTest2() {
        System.out.println("\n--- TEST 2: Citas (100 registros, Casi Ordenado) ---");
        try {
            Appointment[] citas = CsvLoader.loadCitas(PATH_CITAS_CASI);
            ejecutarPruebas(citas, "Citas Casi Ordenadas");
        } catch (Exception e) {
            handleError(e, PATH_CITAS_CASI);
        }
    }

    private static void runTest3() {
        System.out.println("\n--- TEST 3: Pacientes (500 registros, Repetidos) ---");
        try {
            List<Patient> lista = CsvLoader.loadPacientes(PATH_PACIENTES);
            Patient[] pacientes = lista.toArray(new Patient[0]);
            ejecutarPruebas(pacientes, "Pacientes");
        } catch (Exception e) {
            handleError(e, PATH_PACIENTES);
        }
    }

    private static void runTest4() {
        System.out.println("\n--- TEST 4: Inventario (500 registros, Inverso) ---");
        try {
            Item[] inventario = CsvLoader.loadInventario(PATH_INVENTARIO);
            ejecutarPruebas(inventario, "Inventario");
        } catch (Exception e) {
            handleError(e, PATH_INVENTARIO);
        }
    }

    private static <T extends Comparable<T>> void ejecutarPruebas(T[] datosOriginales, String nombreDataset) {
        if (datosOriginales == null || datosOriginales.length == 0) {
            System.out.println("El dataset está vacío o no se pudo cargar.");
            return;
        }

        System.out.println("Registros cargados: " + datosOriginales.length);
        System.out.println("Iniciando medición estadística (R=10, descartando 3 de calentamiento)...");

        // Calculamos métricas usando la MEDIANA de 10 repeticiones
        // Esto cumple con el requisito de "Instrumentación"
        SortingMetrics mBubble = obtenerMedianaMetrics(datosOriginales, "Bubble");
        SortingMetrics mSelection = obtenerMedianaMetrics(datosOriginales, "Selection");
        SortingMetrics mInsertion = obtenerMedianaMetrics(datosOriginales, "Insertion");

        // Datos
        System.out.println("\nResultados (MEDIANA) para: " + nombreDataset);
        System.out.println("+-----------+--------------+-------------+--------------+--------------+");
        System.out.println("-----------------------Bubble sort---------------------");
        System.out.println("Comparaciones: \t" + mBubble.comparisons);
        System.out.println("Movimientos:      \t" + mBubble.assignments);
        System.out.println("Tiempo:              \t\t" + mBubble.executionTimeNs);

        System.out.println("-----------------------Selection sort------------------");
        System.out.println("Comparaciones: \t" + mSelection.comparisons);
        System.out.println("Movimientos:      \t" + mSelection.assignments);
        System.out.println("Tiempo:              \t\t" + mSelection.executionTimeNs);

        System.out.println("-----------------------Insertion sort------------------");
        System.out.println("Comparaciones: \t" + mInsertion.comparisons);
        System.out.println("Movimientos:      \t" + mInsertion.assignments);
        System.out.println("Tiempo:              \t\t" + mInsertion.executionTimeNs);
        System.out.println("+-----------+--------------+-------------+--------------+--------------+");

        System.out.println("\n¿Quieres realizar búsquedas sobre el dataset ordenado? (s/n): ");
        String r = scanner.nextLine().trim().toLowerCase();

        if (r.equals("s")){
            T[] arr = Arrays.copyOf(datosOriginales, datosOriginales.length);
            Arrays.sort(arr);
            menuBusqueda(arr, nombreDataset);
        }
    }

    /**
     * Ejecuta el algoritmo 10 veces, descarta las 3 primeras y calcula la
     * mediana.
     */
    private static <T extends Comparable<T>> SortingMetrics obtenerMedianaMetrics(T[] original, String algoritmo) {
        int R = 10; // Total repeticiones
        int WARMUP = 3; // Repeticiones a descartar
        List<Long> tiempos = new ArrayList<>();
        SortingMetrics lastMetric = null;

        for (int i = 0; i < R; i++) {
            T[] copia = Arrays.copyOf(original, original.length);

            // Sugerencia de la JVM para limpiar memoria antes de medir
            System.gc();

            SortingMetrics m = null;
            switch (algoritmo) {
                case "Bubble":
                    m = BubbleSort.sort(copia, false);
                    break;
                case "Selection":
                    m = SelectionSort.sort(copia, false);
                    break;
                case "Insertion":
                    m = InsertionSort.sort(copia, false);
                    break;
            }

            // Solo guardamos tiempos después del calentamiento
            if (i >= WARMUP) {
                tiempos.add(m.executionTimeNs);
            }
            lastMetric = m;
            if (i == (R - 1)) {
                preguntarVisualizacion(copia);
            }
        }

        Collections.sort(tiempos);
        long medianaTiempo = tiempos.get(tiempos.size() / 2);

        return new SortingMetrics(lastMetric.comparisons, lastMetric.assignments, medianaTiempo);
    }

    private static <T> void preguntarVisualizacion(T[] arrayOrdenado) {
        System.out.print("\n¿Presentar los arreglos ordenados? (s/n): ");
        String respuesta = scanner.nextLine().trim().toLowerCase();

        if (respuesta.equals("s")) {
            System.out.println("\n--- MUESTRA DEL ARREGLO ORDENADO ---");
            printArray(arrayOrdenado);
        }
    }

    private static <T> void printArray(T[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("[%3d] %s%n", i + 1, array[i].toString());
        }
    }



    private static <T extends Comparable<T>> void menuBusqueda(T[] array, String nombreDataset){
        boolean vivo = true;

        while (vivo){
            System.out.println("\n--- BUSQUEDAS: " + nombreDataset );
            System.out.println("1. Búsqueda Binaria");
            System.out.println("2. Límite inferior");
            System.out.println("3. Límite superior");
            System.out.println("4. Encontrar todo");
            System.out.println("5. Reporte de duplicados");
            System.out.println("0. Menú principal");
            System.out.println("Opción: ");
            int op = getValidInt("");

            switch (op){
                case 1 -> buscarBinary(array);
                case 2 -> buscarLower(array);
                case 3 -> buscarUpper(array);
                case 4 -> buscarAll(array);
                case 5 -> reporteDuplicados(array);
                case 0 -> vivo = false;
            }
        }
    }

    private static void menuPacientes() {
        try {
            System.out.println("\n--- PACIENTES (SLL) ---");

            PatientList lista = new PatientList();
            List<Patient> cargados = CsvLoader.loadPacientes(PATH_PACIENTES);

            for (Patient p : cargados) lista.add(p);

            boolean vivo = true;
            while (vivo) {
                System.out.println("\n1. Buscar PRIMERO por apellido");
                System.out.println("2. Buscar ÚLTIMO por apellido");
                System.out.println("3. Listar pacientes con prioridad 1");
                System.out.println("0. Volver");
                int op = getValidInt("Opción: ");

                switch (op) {
                    case 1 -> {
                        System.out.print("Apellido: ");
                        String a = scanner.nextLine();
                        System.out.println("Resultado: " + lista.findFirst(a));
                    }
                    case 2 -> {
                        System.out.print("Apellido: ");
                        String a = scanner.nextLine();
                        System.out.println("Resultado: " + lista.findLast(a));
                    }
                    case 3 -> {
                        System.out.println("Pacientes prioridad 1:");
                        lista.findAllPrioridad1().forEach(System.out::println);
                    }
                    case 0 -> vivo = false;
                }
            }

        } catch (Exception e) {
            System.out.println("Error cargando pacientes en SLL: " + e);
        }
    }

    private static <T extends Comparable<T>> void buscarBinary(T[] arr){
        System.out.println("Ingrese texto para buscar: ");
        String k = scanner.nextLine();

        for (T t : arr) {
            if (t.toString().contains(k)) {
                int pos = ArraySearch.binarySearch(arr, t);
                System.out.println("binarySearch → índice: " + pos);
                return;
            }
        }
        System.out.println("No encontrado.");
    }



    private static <T extends Comparable<T>> void buscarLower(T[] arr) {
        System.out.print("Ingrese texto para buscar: ");
        String k = scanner.nextLine();

        for (T t : arr) {
            if (t.toString().contains(k)) {
                int pos = ArraySearch.lowerBound(arr, t);
                System.out.println("lowerBound → " + pos);
                return;
            }
        }
        System.out.println("No encontrado.");
    }

    private static <T extends Comparable<T>> void buscarUpper(T[] arr) {
        System.out.print("Ingrese texto para buscar: ");
        String k = scanner.nextLine();

        for (T t : arr) {
            if (t.toString().contains(k)) {
                int pos = ArraySearch.upperBound(arr, t);
                System.out.println("upperBound → " + pos);
                return;
            }
        }
        System.out.println("No encontrado.");
    }

    private static <T extends Comparable<T>> void buscarAll(T[] arr) {
        System.out.print("Ingrese texto para buscar: ");
        String p = scanner.nextLine();

        List<Integer> res = ArraySearch.findAll(arr, x -> x.toString().contains(p));
        System.out.println("Índices: " + res);
    }

    private static <T extends Comparable<T>> void reporteDuplicados(T[] arr) {
        System.out.println("\n--- REPORTE DE DUPLICADOS ---");
        int i = 0;
        while (i < arr.length) {

            int lo = ArraySearch.lowerBound(arr, arr[i]);
            int hi = ArraySearch.upperBound(arr, arr[i]);

            int count = hi - lo + 1;
            if (count > 1) {
                System.out.println(arr[i] + " → repetido " + count + " veces");
            }
            i = hi + 1;
        }
    }

    private static void handleError(Exception e, String path) {
        System.err.println("\n ERROR CRÍTICO al cargar: " + path);
        if (e instanceof FileNotFoundException) {
            System.err.println("El archivo no existe. Asegúrate de haber ejecutado la opción 0 o DatasetGenerator primero.");
        } else {
            System.err.println("Detalle: " + e.getMessage());
        }
    }
}
