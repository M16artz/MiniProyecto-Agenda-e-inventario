package ed.u2.sorting;

import ed.u2.model.Appointment;
import ed.u2.model.Item;
import ed.u2.model.Node;
import ed.u2.model.Patient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import ed.u2.searchs.ArraySearch;
import util.ArrayValidator;
import util.CsvLoader;
import util.EvidenceTableGenerator;
import util.PatientList;
import util.SortingMetrics;

/**
 * Clase principal con flujo corregido:
 * - Pacientes solo se manejan como lista enlazada (no arreglo)
 * - No hay ordenamiento para pacientes
 *
 * @author MikelMZ : Miguel Armas
 */
public class SortingDemo {

    // Rutas de los archivos
    private static final String PATH_CITAS_100 = "src/main/java/data/citas_100.csv";
    private static final String PATH_CITAS_CASI = "src/main/java/data/citas_100_casi_ordenadas.csv";
    private static final String PATH_PACIENTES = "src/main/java/data/pacientes_500.csv";
    private static final String PATH_INVENTARIO = "src/main/java/data/inventario_500_inverso.csv";

    // Datos cargados al inicio
    private static Appointment[] citasAleatorias;
    private static Appointment[] citasCasiOrdenadas;
    private static PatientList pacientesLista; // Solo lista enlazada, NO arreglo
    private static Item[] inventarioInverso;
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // ============================================
            // 1. CARGAR TODOS LOS ARCHIVOS AL INICIO
            // ============================================
            System.out.println("============================================");
            System.out.println("   AGENDA E INVENTARIO INTELIGENTES - UNL");
            System.out.println("============================================");
            System.out.println("\nCargando archivos CSV...\n");
            
            cargarTodosLosArchivos();
            
            System.out.println("\nTodos los archivos cargados exitosamente!");
            
            // ============================================
            // 2. MENÚ PRINCIPAL
            // ============================================
            boolean continuar = true;
            while (continuar) {
                mostrarMenuPrincipal();
                int opcion = getValidInt("Seleccione una opción: ", 0, 4);
                
                switch (opcion) {
                    case 1:
                        procesarCitasAleatorias();
                        break;
                    case 2:
                        procesarCitasCasiOrdenadas();
                        break;
                    case 3:
                        procesarPacientes(); // Solo lista enlazada, no ordenamiento
                        break;
                    case 4:
                        procesarInventario();
                        break;
                    case 0:
                        System.out.println("\nSaliendo del sistema...");
                        continuar = false;
                        break;
                }
            }
            
            scanner.close();
            
        } catch (Exception e) {
            System.err.println("Error fatal en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ============================================
    // MÉTODOS DE CARGA DE ARCHIVOS (CORREGIDO)
    // ============================================
    private static void cargarTodosLosArchivos() throws Exception {
        // Cargar citas aleatorias
        System.out.print("Cargando citas aleatorias... ");
        citasAleatorias = CsvLoader.loadCitas(PATH_CITAS_100);
        System.out.println("✅ " + citasAleatorias.length + " registros");
        
        // Cargar citas casi ordenadas
        System.out.print("Cargando citas casi ordenadas... ");
        citasCasiOrdenadas = CsvLoader.loadCitas(PATH_CITAS_CASI);
        System.out.println("Listos " + citasCasiOrdenadas.length + " registros");
        
        // Cargar pacientes (SOLO lista enlazada, NO arreglo)
        System.out.print("Cargando pacientes (lista enlazada)... ");
        List<Patient> pacientesList = CsvLoader.loadPacientes(PATH_PACIENTES);
        
        // Crear lista enlazada de pacientes
        pacientesLista = new PatientList();
        for (Patient p : pacientesList) {
            pacientesLista.add(p);
        }
        System.out.println("Listos " + pacientesLista.size() + " registros (Singly Linked List)");
        
        // Cargar inventario
        System.out.print("Cargando inventario... ");
        inventarioInverso = CsvLoader.loadInventario(PATH_INVENTARIO);
        System.out.println("Listos " + inventarioInverso.length + " registros");
    }

    // ============================================
    // MENÚ PRINCIPAL (ACTUALIZADO)
    // ============================================
    private static void mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("        SELECCIONE EL DATASET A PROCESAR");
        System.out.println("=".repeat(60));
        System.out.println("1. Citas (100 registros, Aleatorio)");
        System.out.println("2. Citas (100 registros, Casi Ordenado)");
        System.out.println("3. Pacientes (500 registros, Solo Lista Enlazada)");
        System.out.println("4. Inventario (500 registros, Inverso)");
        System.out.println("0. Salir del programa");
        System.out.println("-".repeat(60));
        System.out.println("Nota: Pacientes se maneja solo como Singly Linked List (SLL)");
        System.out.println("No se aplica ordenamiento, solo búsquedas secuenciales");
    }

    // ============================================
    // PROCESAMIENTO DE CITAS ALEATORIAS (SIN CAMBIOS)
    // ============================================
    private static void procesarCitasAleatorias() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   EVALUACIÓN DE ORDENAMIENTO - CITAS ALEATORIAS");
        System.out.println("=".repeat(60));
        
        SortingMetrics[] metricas = ejecutarYMostrarOrdenamientos(citasAleatorias, "Citas Aleatorias");
        submenuBusquedasCitas(citasAleatorias, metricas, "Aleatorias");
    }
    
    // ============================================
    // PROCESAMIENTO DE CITAS CASI ORDENADAS (SIN CAMBIOS)
    // ============================================
    private static void procesarCitasCasiOrdenadas() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   EVALUACIÓN DE ORDENAMIENTO - CITAS CASI ORDENADAS");
        System.out.println("=".repeat(60));
        
        SortingMetrics[] metricas = ejecutarYMostrarOrdenamientos(citasCasiOrdenadas, "Citas Casi Ordenadas");
        submenuBusquedasCitas(citasCasiOrdenadas, metricas, "Casi Ordenadas");
    }

    // ============================================
    // PROCESAMIENTO DE PACIENTES (CORREGIDO - SIN ORDENAMIENTO)
    // ============================================
    private static void procesarPacientes() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   MÓDULO PACIENTES (Singly Linked List - SLL)");
        System.out.println("=".repeat(60));
        
        System.out.println("\nINFORMACIÓN DEL DATASET:");
        System.out.println("   Tipo: Singly Linked List (SLL)");
        System.out.println("   Total pacientes: " + pacientesLista.size());
        System.out.println("   Característica: Repetidos en apellidos");
        
        System.out.println("\nNOTA IMPORTANTE:");
        System.out.println("   Los pacientes se manejan SOLO como lista enlazada.");
        System.out.println("   No se aplica ordenamiento (la SLL mantiene orden de inserción).");
        System.out.println("   Solo se realizan búsquedas secuenciales.");
        
        // Ir directamente al submenú de búsquedas (sin ordenamiento)
        submenuBusquedasPacientes();
    }

    // ============================================
    // PROCESAMIENTO DE INVENTARIO 
    // ============================================
    private static void procesarInventario() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   EVALUACIÓN DE ORDENAMIENTO - INVENTARIO");
        System.out.println("=".repeat(60));
        
        SortingMetrics[] metricas = ejecutarYMostrarOrdenamientos(inventarioInverso, "Inventario Inverso");
        submenuBusquedasInventario(inventarioInverso, metricas);
    }

    // ============================================
    // MÉTODO PARA EJECUTAR ORDENAMIENTOS (NO USADO PARA PACIENTES)
    // ============================================
    private static <T extends Comparable<T>> SortingMetrics[] ejecutarYMostrarOrdenamientos(
            T[] datosOriginales, String nombreDataset) {
        
        System.out.println("\nEjecutando algoritmos de ordenación...");
        System.out.println("   Método: R=10 ejecuciones, descartando 3 de calentamiento");
        System.out.println("   Métrica: Mediana de tiempos\n");
        
        SortingMetrics mBubble = obtenerMedianaMetrics(datosOriginales, "Bubble");
        SortingMetrics mSelection = obtenerMedianaMetrics(datosOriginales, "Selection");
        SortingMetrics mInsertion = obtenerMedianaMetrics(datosOriginales, "Insertion");
        
        mostrarTablaResultados(nombreDataset, datosOriginales.length, 
                              mBubble, mSelection, mInsertion);
        
        EvidenceTableGenerator.generateSortingTable(
            nombreDataset, 
            datosOriginales.length,
            mBubble, mSelection, mInsertion
        );
        
        return new SortingMetrics[]{mBubble, mSelection, mInsertion};
    }

    // ============================================
    // SUBMENÚ PARA BÚSQUEDAS EN PACIENTES (SLL) - MEJORADO
    // ============================================
    private static void submenuBusquedasPacientes() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("   BÚSQUEDAS EN PACIENTES (Singly Linked List)");
            System.out.println("=".repeat(60));
            System.out.println("1. Encontrar primer paciente por apellido");
            System.out.println("2. Encontrar último paciente por apellido");
            System.out.println("3. Listar pacientes con prioridad 1");
            System.out.println("4. Mostrar estadísticas de la lista");
            System.out.println("5. Probar búsqueda secuencial con tiempo");
            System.out.println("6. Volver al menú principal");
            System.out.println("-".repeat(60));
            System.out.println("Tipo de búsqueda: SECUENCIAL (única opción para SLL)");
            System.out.println("   Complejidad: O(n) en el peor caso");
            
            int opcion = getValidInt("Seleccione una opción: ", 1, 6);
            
            switch (opcion) {
                case 1:
                    buscarPrimerPaciente();
                    break;
                case 2:
                    buscarUltimoPaciente();
                    break;
                case 3:
                    listarPrioridad1();
                    break;
                case 4:
                    mostrarEstadisticasPacientes();
                    break;
                case 5:
                    probarBusquedaSecuencial();
                    break;
                case 6:
                    continuar = false;
                    break;
            }
        }
    }
    
    // ============================================
    // MÉTODOS DE BÚSQUEDA EN PACIENTES
    // ============================================
    
    private static void buscarPrimerPaciente() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   BUSCAR PRIMER PACIENTE POR APELLIDO");
        System.out.println("=".repeat(50));
        
        System.out.print("Ingrese apellido a buscar: ");
        String apellido = scanner.nextLine().trim();
        
        if (apellido.isEmpty()) {
            System.out.println("Debe ingresar un apellido válido");
            return;
        }
        
        System.out.println("\nIniciando búsqueda secuencial en SLL...");
        long inicio = System.nanoTime();
        Patient encontrado = pacientesLista.findFirst(apellido);
        long fin = System.nanoTime();
        long tiempo = fin - inicio;
        
        System.out.println("\nRESULTADOS DE BÚSQUEDA:");
        System.out.println("   Tipo: Búsqueda secuencial en SLL");
        System.out.println("   Tiempo: " + tiempo + " nanosegundos");
        System.out.println("   Tamaño lista: " + pacientesLista.size() + " elementos");
        
        if (encontrado != null) {
            System.out.println("\nPRIMER PACIENTE ENCONTRADO:");
            System.out.println("   ID: " + encontrado.getId());
            System.out.println("   Apellido: " + encontrado.getApellido());
            System.out.println("   Prioridad: " + encontrado.getPrioridad());
            System.out.println("   Posición: Primera ocurrencia en la lista");
        } else {
            System.out.println("\nNo se encontró ningún paciente con apellido: " + apellido);
            System.out.println("   La búsqueda revisó todos los " + pacientesLista.size() + " elementos");
        }
    }
    
    private static void buscarUltimoPaciente() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   BUSCAR ÚLTIMO PACIENTE POR APELLIDO");
        System.out.println("=".repeat(50));
        
        System.out.print("Ingrese apellido a buscar: ");
        String apellido = scanner.nextLine().trim();
        
        if (apellido.isEmpty()) {
            System.out.println("Debe ingresar un apellido válido");
            return;
        }
        
        System.out.println("\nIniciando búsqueda secuencial en SLL...");
        System.out.println("   (Debe recorrer toda la lista para encontrar el último)");
        
        long inicio = System.nanoTime();
        Patient encontrado = pacientesLista.findLast(apellido);
        long fin = System.nanoTime();
        long tiempo = fin - inicio;
        
        System.out.println("\nRESULTADOS DE BÚSQUEDA:");
        System.out.println("   Tipo: Búsqueda secuencial en SLL (hasta el final)");
        System.out.println("   Tiempo: " + tiempo + " nanosegundos");
        System.out.println("   Tamaño lista: " + pacientesLista.size() + " elementos");
        
        if (encontrado != null) {
            System.out.println("\nÚLTIMO PACIENTE ENCONTRADO:");
            System.out.println("   ID: " + encontrado.getId());
            System.out.println("   Apellido: " + encontrado.getApellido());
            System.out.println("   Prioridad: " + encontrado.getPrioridad());
            System.out.println("   Posición: Última ocurrencia en la lista");
        } else {
            System.out.println("\n❌ No se encontró ningún paciente con apellido: " + apellido);
        }
    }
    
    private static void listarPrioridad1() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   LISTAR PACIENTES CON PRIORIDAD 1 (URGENTES)");
        System.out.println("=".repeat(50));
        
        System.out.println("\nBuscando pacientes con prioridad 1...");
        
        long inicio = System.nanoTime();
        List<Patient> urgentes = pacientesLista.findAllPrioridad1();
        long fin = System.nanoTime();
        long tiempo = fin - inicio;
        
        System.out.println("\nRESULTADOS DE BÚSQUEDA:");
        System.out.println("   Tipo: Búsqueda con predicado (prioridad == 1)");
        System.out.println("   Tiempo: " + tiempo + " nanosegundos");
        System.out.println("   Total pacientes urgentes encontrados: " + urgentes.size());
        System.out.println("   Porcentaje: " + 
            String.format("%.1f", pacientesLista.size() > 0 ? 
                (urgentes.size() * 100.0 / pacientesLista.size()) : 0) + "%");
        
        if (!urgentes.isEmpty()) {
            System.out.println("\nPACIENTES URGENTES (prioridad 1):");
            System.out.println("┌─────┬────────────┬────────────┬──────────┬────────────────────┐");
            System.out.println("│ No. │ ID         │ Apellido   │ Prioridad│ Tiempo de búsqueda │");
            System.out.println("├─────┼────────────┼────────────┼──────────┼────────────────────┤");
            
            for (int i = 0; i < Math.min(15, urgentes.size()); i++) {
                Patient p = urgentes.get(i);
                System.out.printf("│ %3d │ %-10s │ %-10s │ %8d │ %18s ns │\n",
                    i + 1, p.getId(), p.getApellido(), p.getPrioridad(), tiempo);
            }
            System.out.println("└─────┴────────────┴────────────┴──────────┴────────────────────┘");
            
            if (urgentes.size() > 15) {
                System.out.println("   ... y " + (urgentes.size() - 15) + " pacientes más");
            }
            
            // Mostrar distribución por apellido
            System.out.println("\nDISTRIBUCIÓN POR APELLIDO (urgentes):");
            java.util.Map<String, Integer> conteoApellidos = new java.util.HashMap<>();
            for (Patient p : urgentes) {
                String apellido = p.getApellido();
                conteoApellidos.put(apellido, conteoApellidos.getOrDefault(apellido, 0) + 1);
            }
            
            for (java.util.Map.Entry<String, Integer> entry : conteoApellidos.entrySet()) {
                if (entry.getValue() > 1) {
                    System.out.println("   " + entry.getKey() + ": " + entry.getValue() + " pacientes");
                }
            }
        } else {
            System.out.println("\nNo hay pacientes con prioridad 1 en la lista");
        }
    }
    
    private static void mostrarEstadisticasPacientes() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   ESTADÍSTICAS DE LA LISTA DE PACIENTES");
        System.out.println("=".repeat(50));
        
        System.out.println("\nINFORMACIÓN GENERAL:");
        System.out.println("   Total pacientes: " + pacientesLista.size());
        System.out.println("   ¿Lista vacía?: " + (pacientesLista.size() == 0 ? "Sí" : "No"));
        
        // Calcular estadísticas avanzadas
        System.out.println("\nDISTRIBUCIÓN POR PRIORIDAD:");
        
        int[] conteoPrioridad = new int[6]; // Índices 1-5
        int totalApellidosUnicos = 0;
        java.util.Set<String> apellidosUnicos = new java.util.HashSet<>();
        
        // Recorrer la lista para estadísticas
        Node actual = pacientesLista.getHead();
        int posicion = 0;
        long tiempoRecorridoInicio = System.nanoTime();
        
        while (actual != null) {
            Patient p = actual.getData();
            int prioridad = p.getPrioridad();
            
            // Conteo por prioridad
            if (prioridad >= 1 && prioridad <= 5) {
                conteoPrioridad[prioridad]++;
            }
            
            // Apellidos únicos
            apellidosUnicos.add(p.getApellido());
            
            actual = actual.getNext();
            posicion++;
        }
        
        long tiempoRecorridoFin = System.nanoTime();
        totalApellidosUnicos = apellidosUnicos.size();
        
        System.out.println("   Tiempo de recorrido completo: " + 
            (tiempoRecorridoFin - tiempoRecorridoInicio) + " ns");
        
        // Mostrar distribución por prioridad
        for (int i = 1; i <= 5; i++) {
            double porcentaje = pacientesLista.size() > 0 ? 
                (conteoPrioridad[i] * 100.0 / pacientesLista.size()) : 0;
            System.out.printf("   Prioridad %d: %d pacientes (%.1f%%)\n",
                i, conteoPrioridad[i], porcentaje);
        }
        
        System.out.println("\nESTADÍSTICAS DE APELLIDOS:");
        System.out.println("   Apellidos únicos: " + totalApellidosUnicos);
        System.out.println("   Promedio pacientes por apellido: " + 
            String.format("%.2f", pacientesLista.size() > 0 ? 
                (double) pacientesLista.size() / totalApellidosUnicos : 0));
        
        // Mostrar apellidos más comunes (si hay datos)
        if (pacientesLista.size() > 0) {
            System.out.println("\nTOP 5 APELLIDOS MÁS REPETIDOS:");
            
            // Contar frecuencia de apellidos
            java.util.Map<String, Integer> frecuenciaApellidos = new java.util.HashMap<>();
            actual = pacientesLista.getHead();
            
            while (actual != null) {
                String apellido = actual.getData().getApellido();
                frecuenciaApellidos.put(apellido, 
                    frecuenciaApellidos.getOrDefault(apellido, 0) + 1);
                actual = actual.getNext();
            }
            
            // Ordenar por frecuencia
            List<java.util.Map.Entry<String, Integer>> listaFrecuencias = 
                new ArrayList<>(frecuenciaApellidos.entrySet());
            listaFrecuencias.sort((a, b) -> b.getValue().compareTo(a.getValue()));
            
            for (int i = 0; i < Math.min(5, listaFrecuencias.size()); i++) {
                java.util.Map.Entry<String, Integer> entry = listaFrecuencias.get(i);
                System.out.printf("   %d. %s: %d pacientes\n", 
                    i + 1, entry.getKey(), entry.getValue());
            }
        }
        
        System.out.println("\nESTRUCTURA DE DATOS:");
        System.out.println("   Tipo: Singly Linked List (SLL)");
        System.out.println("   Ventajas: Inserción O(1), flexibilidad");
        System.out.println("   Desventajas: Búsqueda O(n), acceso aleatorio no disponible");
        System.out.println("   Uso recomendado: Datos dinámicos con inserciones frecuentes");

    }
    
    private static void probarBusquedaSecuencial() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   PRUEBA DE BÚSQUEDA SECUENCIAL EN SLL");
        System.out.println("=".repeat(50));
        
        System.out.println("\nCOMPARATIVA DE BÚSQUEDAS SECUENCIALES:");
        
        // Probar con diferentes apellidos (algunos existentes, otros no)
        String[] apellidosPrueba = {"Gomez", "Perez", "Rodriguez", "Lopez", "NoExiste"};
        
        System.out.println("\n┌────────────────────┬────────────┬────────────┬──────────────┐");
        System.out.println("│ Apellido           │ Encontrado │ Tiempo (ns)│ Comparaciones│");
        System.out.println("├────────────────────┼────────────┼────────────┼──────────────┤");
        
        for (String apellido : apellidosPrueba) {
            // Buscar primer paciente
            long inicio = System.nanoTime();
            Patient encontrado = pacientesLista.findFirst(apellido);
            long fin = System.nanoTime();
            
            // Contar comparaciones (simulación)
            int comparaciones = 0;
            Node actual = pacientesLista.getHead();
            while (actual != null) {
                comparaciones++;
                if (actual.getData().getApellido().equalsIgnoreCase(apellido)) {
                    break;
                }
                actual = actual.getNext();
            }
            
            System.out.printf("│ %-18s │ %-10s │ %-10d │ %-12d │\n",
                apellido,
                encontrado != null ? "Sí" : "No",
                fin - inicio,
                comparaciones);
        }
        
        System.out.println("└────────────────────┴────────────┴────────────┴──────────────┘");
        
        System.out.println("\nCONCLUSIÓN:");
        System.out.println("   • Búsqueda secuencial en SLL tiene complejidad O(n)");
        System.out.println("   • Mejor caso: O(1) (primer elemento)");
        System.out.println("   • Peor caso: O(n) (último elemento o no encontrado)");
        System.out.println("   • No requiere orden previo");
        System.out.println("   • Simple pero ineficiente para listas grandes");
        
        System.out.println("\nLIMITACIONES DE SLL:");
        System.out.println("   • No se puede acceder por índice rápidamente");
        System.out.println("   • No se puede aplicar búsqueda binaria");
        System.out.println("   • Para búsquedas eficientes, considerar otras estructuras");

    }

    // ============================================
    // MÉTODOS AUXILIARES (SIN CAMBIOS)
    // ============================================
    
    private static <T extends Comparable<T>> SortingMetrics obtenerMedianaMetrics(
            T[] original, String algoritmo) {
        
        final int R = 10;
        final int WARMUP = 3;
        List<Long> tiempos = new ArrayList<>();
        SortingMetrics lastMetric = null;
        
        for (int i = 0; i < R; i++) {
            T[] copia = Arrays.copyOf(original, original.length);
            
            if (i == WARMUP) System.gc();
            
            SortingMetrics m = ejecutarAlgoritmo(copia, algoritmo);
            
            if (i >= WARMUP) {
                tiempos.add(m.executionTimeNs);
            }
            lastMetric = m;
        }
        
        Collections.sort(tiempos);
        long medianaTiempo = tiempos.get(tiempos.size() / 2);
        
        return new SortingMetrics(lastMetric.comparisons, lastMetric.assignments, medianaTiempo);
    }
    
    private static <T extends Comparable<T>> SortingMetrics ejecutarAlgoritmo(
            T[] array, String algoritmo) {
        
        switch (algoritmo) {
            case "Bubble":
                return BubbleSort.sort(array, false);
            case "Selection":
                return SelectionSort.sort(array, false);
            case "Insertion":
                return InsertionSort.sort(array, false);
            default:
                throw new IllegalArgumentException("Algoritmo desconocido: " + algoritmo);
        }
    }
    
    private static int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea
                if (num >= min && num <= max) {
                    return num;
                } else {
                    System.out.println("Número fuera de rango (" + min + "-" + max + "). Intente nuevamente.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor ingrese un número.");
                scanner.next(); // Limpiar buffer
            }
        }
    }
    
    private static void mostrarTablaResultados(String dataset, int n, 
            SortingMetrics bubble, SortingMetrics selection, SortingMetrics insertion) {
        
        System.out.println("\nRESULTADOS DE ORDENACIÓN - " + dataset.toUpperCase());
        System.out.println("   Tamaño del dataset: " + n + " elementos");
        System.out.println("┌─────────────────┬──────────────────┬──────────────────┬──────────────────┐");
        System.out.println("│ Algoritmo       │ Comparaciones    │ Movimientos      │ Tiempo (ns)      │");
        System.out.println("├─────────────────┼──────────────────┼──────────────────┼──────────────────┤");
        System.out.printf("│ Bubble Sort     │ %-16d │ %-16d │ %-16d │\n", 
            bubble.comparisons, bubble.assignments, bubble.executionTimeNs);
        System.out.printf("│ Selection Sort  │ %-16d │ %-16d │ %-16d │\n", 
            selection.comparisons, selection.assignments, selection.executionTimeNs);
        System.out.printf("│ Insertion Sort  │ %-16d │ %-16d │ %-16d │\n", 
            insertion.comparisons, insertion.assignments, insertion.executionTimeNs);
        System.out.println("└─────────────────┴──────────────────┴──────────────────┴──────────────────┘");
    }
    

    // ============================================
    // SUBMENÚ PARA BÚSQUEDAS EN INVENTARIO
    // ============================================
    private static void submenuBusquedasInventario(Item[] inventario, SortingMetrics[] metricas) {
        // Ordenar inventario por stock (ya está ordenado de la evaluación)
        Item[] inventarioOrdenado = Arrays.copyOf(inventario, inventario.length);
        Arrays.sort(inventarioOrdenado); // Ordena por stock (compareTo de Item)
        
        System.out.println("\nInventario ordenado por stock");
        System.out.println("   Stock mínimo: " + inventarioOrdenado[0].getStock());
        System.out.println("   Stock máximo: " + inventarioOrdenado[inventarioOrdenado.length-1].getStock());
        
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("   BÚSQUEDAS EN INVENTARIO (Ordenado por Stock)");
            System.out.println("=".repeat(60));
            System.out.println("1. Búsqueda binaria de stock específico");
            System.out.println("2. Buscar todos los items con stock bajo (≤ 10)");
            System.out.println("3. Buscar items con stock crítico (≤ 5)");
            System.out.println("4. Encontrar límites para un valor de stock");
            System.out.println("5. Volver al menú principal");
            System.out.println("-".repeat(60));
            
            int opcion = getValidInt("Seleccione una opción: ", 1, 5);
            
            switch (opcion) {
                case 1:
                    buscarStockEspecifico(inventarioOrdenado);
                    break;
                case 2:
                    buscarStockBajo(inventarioOrdenado, 10);
                    break;
                case 3:
                    buscarStockBajo(inventarioOrdenado, 5);
                    break;
                case 4:
                    buscarLimitesStock(inventarioOrdenado);
                    break;
                case 5:
                    continuar = false;
                    break;
            }
        }
    }
    
    private static void buscarStockEspecifico(Item[] inventarioOrdenado) {
        System.out.println("\nBÚSQUEDA BINARIA DE STOCK ESPECÍFICO");
        System.out.println("-".repeat(40));
        
        System.out.print("Ingrese valor de stock a buscar: ");
        int stockBuscado = getValidInt("", 0, Integer.MAX_VALUE);
        
        // Crear item de búsqueda
        Item busqueda = new Item("BUSQUEDA", "BUSQUEDA", stockBuscado);
        
        long inicio = System.nanoTime();
        int posicion = ArraySearch.binarySearch(inventarioOrdenado, busqueda);
        long fin = System.nanoTime();
        
        System.out.println("\n⏱ Tiempo de búsqueda: " + (fin - inicio) + " ns");
        
        if (posicion != -1) {
            System.out.println("\nITEM ENCONTRADO:");
            System.out.println("   Índice: " + posicion);
            System.out.println("   ID: " + inventarioOrdenado[posicion].getId());
            System.out.println("   Insumo: " + inventarioOrdenado[posicion].getInsumo());
            System.out.println("   Stock: " + inventarioOrdenado[posicion].getStock());
            
            // Mostrar items con el mismo stock (pueden haber duplicados)
            mostrarDuplicadosStock(inventarioOrdenado, stockBuscado, posicion);
        } else {
            System.out.println("\nNo se encontró ningún item con stock: " + stockBuscado);
            
            // Sugerir stocks cercanos
            sugerirStocksCercanos(inventarioOrdenado, stockBuscado);
        }
       
    }
    
    private static void buscarStockBajo(Item[] inventarioOrdenado, int limite) {
        System.out.println("\nITEMS CON STOCK " + (limite == 5 ? "CRÍTICO" : "BAJO") + " (≤ " + limite + ")");
        System.out.println("-".repeat(40));
        
        // Como el arreglo está ordenado por stock, podemos usar binary search
        Item limiteItem = new Item("LIMITE", "LIMITE", limite);
        
        // Encontrar upper bound para el límite (último item con stock ≤ limite)
        int upper = ArraySearch.upperBound(inventarioOrdenado, limiteItem);
        
        if (upper == -1) {
            // No hay items con stock ≤ limite, buscar el primer item con stock > limite
            int pos = 0;
            while (pos < inventarioOrdenado.length && inventarioOrdenado[pos].getStock() <= limite) {
                pos++;
            }
            upper = pos - 1;
        }
        
        if (upper >= 0) {
            System.out.println("\nALERTA: " + (upper + 1) + " items con stock " + 
                (limite == 5 ? "crítico" : "bajo"));
            
            System.out.println("\nLISTA DE ITEMS:");
            System.out.println("┌─────┬────────────┬──────────────────────┬───────┐");
            System.out.println("│ No. │ ID         │ Insumo               │ Stock │");
            System.out.println("├─────┼────────────┼──────────────────────┼───────┤");
            
            for (int i = 0; i <= Math.min(upper, 20); i++) {
                Item item = inventarioOrdenado[i];
                System.out.printf("│ %3d │ %-10s │ %-20s │ %5d │\n",
                    i + 1, item.getId(), item.getInsumo(), item.getStock());
            }
            System.out.println("└─────┴────────────┴──────────────────────┴───────┘");
            
            if (upper >= 20) {
                System.out.println("   ... y " + (upper - 19) + " más");
            }
        } else {
            System.out.println("\nNo hay items con stock ≤ " + limite);
        }
        
    }
    
    private static void buscarLimitesStock(Item[] inventarioOrdenado) {
        System.out.println("\nLÍMITES PARA VALOR DE STOCK");
        System.out.println("-".repeat(40));
        
        System.out.print("Ingrese valor de stock: ");
        int stock = getValidInt("", 0, Integer.MAX_VALUE);
        
        Item busqueda = new Item("BUSQUEDA", "BUSQUEDA", stock);
        
        int lower = ArraySearch.lowerBound(inventarioOrdenado, busqueda);
        int upper = ArraySearch.upperBound(inventarioOrdenado, busqueda);
        
        System.out.println("\nRESULTADOS PARA STOCK = " + stock);
        System.out.println("   Lower Bound: " + (lower != -1 ? lower : "No encontrado"));
        System.out.println("   Upper Bound: " + (upper != -1 ? upper : "No encontrado"));
        
        if (lower != -1 && upper != -1) {
            System.out.println("   Total items con ese stock: " + (upper - lower + 1));
            
            if (upper - lower + 1 <= 10) {
                System.out.println("\n📄 Items con stock = " + stock + ":");
                for (int i = lower; i <= upper; i++) {
                    System.out.println("   [" + i + "] " + inventarioOrdenado[i].toString());
                }
            }
        }
        
    }
    
    private static void mostrarDuplicadosStock(Item[] inventario, int stock, int posicion) {
        // Buscar hacia atrás y adelante para encontrar todos los duplicados
        int inicio = posicion;
        int fin = posicion;
        
        while (inicio > 0 && inventario[inicio - 1].getStock() == stock) {
            inicio--;
        }
        
        while (fin < inventario.length - 1 && inventario[fin + 1].getStock() == stock) {
            fin++;
        }
        
        if (fin > inicio) {
            System.out.println("\n" + (fin - inicio + 1) + " items tienen stock = " + stock);
            System.out.println("   Índices: " + inicio + " a " + fin);
        }
    }
    
    private static void sugerirStocksCercanos(Item[] inventario, int stockBuscado) {
        // Encontrar posición de inserción
        Item busqueda = new Item("BUSQUEDA", "BUSQUEDA", stockBuscado);
        int posicionInsercion = -Arrays.binarySearch(inventario, busqueda) - 1;
        
        if (posicionInsercion >= 0 && posicionInsercion < inventario.length) {
            System.out.println("\nSTOCKS CERCANOS DISPONIBLES:");
            
            if (posicionInsercion > 0) {
                System.out.println("   Stock menor más cercano: " + 
                    inventario[posicionInsercion - 1].getStock() + 
                    " (" + inventario[posicionInsercion - 1].getInsumo() + ")");
            }
            
            if (posicionInsercion < inventario.length) {
                System.out.println("   Stock mayor más cercano: " + 
                    inventario[posicionInsercion].getStock() + 
                    " (" + inventario[posicionInsercion].getInsumo() + ")");
            }
        }
    }
    
     // ============================================
    // SUBMENÚ PARA BÚSQUEDAS EN CITAS
    // ============================================
    private static void submenuBusquedasCitas(Appointment[] citas, SortingMetrics[] metricas, String tipo) {
        // Ordenar las citas para búsquedas
        Appointment[] citasOrdenadas = Arrays.copyOf(citas, citas.length);
        Arrays.sort(citasOrdenadas);
        
        System.out.println("\nArreglo ordenado por fecha-hora");
        System.out.println("   Primer elemento: " + citasOrdenadas[0].getFechaHora());
        System.out.println("   Último elemento: " + citasOrdenadas[citasOrdenadas.length-1].getFechaHora());
        
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("   BÚSQUEDAS EN CITAS " + tipo.toUpperCase());
            System.out.println("=".repeat(60));
            System.out.println("1. Búsqueda binaria por fecha-hora");
            System.out.println("2. Búsqueda en rango de fechas");
            System.out.println("3. Límites inferior/superior de una fecha");
            System.out.println("4. Comparar lower/upper bound");
            System.out.println("5. Volver al menú principal");
            System.out.println("-".repeat(60));
            
            int opcion = getValidInt("Seleccione una opción: ", 1, 5);
            
            switch (opcion) {
                case 1:
                    buscarBinariaCitas(citasOrdenadas);
                    break;
                case 2:
                    buscarRangoCitas(citasOrdenadas);
                    break;
                case 3:
                    mostrarLimitesCitas(citasOrdenadas);
                    break;
                case 4:
                    compararLimitesCitas(citasOrdenadas);
                    break;
                case 5:
                    continuar = false;
                    break;
            }
        }
    }
    
    private static void buscarBinariaCitas(Appointment[] citasOrdenadas) {
        System.out.println("\nBÚSQUEDA BINARIA POR FECHA-HORA");
        System.out.println("-".repeat(40));
        
        System.out.println("Formato de fecha: AAAA-MM-DDTHH:MM:SS");
        System.out.println("   Ejemplo: 2024-03-15T09:30:00");
        System.out.print("\nIngrese fecha-hora a buscar: ");
        String fechaStr = scanner.nextLine().trim();
        
        try {
            // Crear cita de búsqueda
            Appointment busqueda = new Appointment("BUSQUEDA", "BUSQUEDA", fechaStr);
            
            // Validar que el arreglo está ordenado
            if (!ArrayValidator.isSorted(citasOrdenadas)) {
                System.out.println("El arreglo no está ordenado. Ordenando...");
                Arrays.sort(citasOrdenadas);
            }
            
            int posicion = ArraySearch.binarySearch(citasOrdenadas, busqueda);
            
            if (posicion != -1) {
                System.out.println("\nCITA ENCONTRADA:");
                System.out.println("   Índice: " + posicion);
                System.out.println("   ID: " + citasOrdenadas[posicion].getId());
                System.out.println("   Apellido: " + citasOrdenadas[posicion].getApellido());
                System.out.println("   Fecha: " + citasOrdenadas[posicion].getFechaHora());
            } else {
                System.out.println("\nCita no encontrada para la fecha: " + fechaStr);
                
                // Sugerir fechas cercanas
                sugerirFechasCercanas(citasOrdenadas, busqueda);
            }
            
        } catch (Exception e) {
            System.out.println("Error: Formato de fecha inválido o error en búsqueda");
        }
        
    }
    
    private static void buscarRangoCitas(Appointment[] citasOrdenadas) {
        System.out.println("\nBÚSQUEDA EN RANGO DE FECHAS");
        System.out.println("-".repeat(40));
        
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        
        System.out.println("Formato: AAAA-MM-DDTHH:MM:SS");
        
        try {
            System.out.print("\nIngrese fecha INICIAL: ");
            String inicioStr = scanner.nextLine().trim();
            LocalDateTime inicio = LocalDateTime.parse(inicioStr, formatter);
            
            System.out.print("Ingrese fecha FINAL: ");
            String finStr = scanner.nextLine().trim();
            LocalDateTime fin = LocalDateTime.parse(finStr, formatter);
            
            if (inicio.isAfter(fin)) {
                System.out.println("Advertencia: Fecha inicial es posterior a la final");
                System.out.print("¿Invertir el orden? (s/n): ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                if (respuesta.equals("s")) {
                    LocalDateTime temp = inicio;
                    inicio = fin;
                    fin = temp;
                }
            }
            
            List<Appointment> resultados = ArraySearch.findAppointmentsInRange(
                citasOrdenadas, inicio, fin);
            
            System.out.println("\nRESULTADOS DEL RANGO:");
            System.out.println("   Desde: " + inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            System.out.println("   Hasta: " + fin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            
            if (resultados.isEmpty()) {
                System.out.println("\nNo se encontraron citas en ese rango");
            } else {
                System.out.println("\n" + resultados.size() + " citas encontradas:");
                System.out.println("┌─────┬────────────┬────────────┬─────────────────────────┐");
                System.out.println("│ No. │ ID         │ Apellido   │ Fecha-Hora              │");
                System.out.println("├─────┼────────────┼────────────┼─────────────────────────┤");
                
                for (int i = 0; i < resultados.size(); i++) {
                    Appointment cita = resultados.get(i);
                    System.out.printf("│ %3d │ %-10s │ %-10s │ %-23s │\n",
                        i + 1,
                        cita.getId(),
                        cita.getApellido(),
                        cita.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                }
                System.out.println("└─────┴────────────┴────────────┴─────────────────────────┘");
            }
            
        } catch (DateTimeParseException e) {
            System.out.println("Error: Formato de fecha inválido");
        }
        
    }
    
    private static void mostrarLimitesCitas(Appointment[] citasOrdenadas) {
        System.out.println("\nLÍMITES INFERIOR/SUPERIOR DE UNA FECHA");
        System.out.println("-".repeat(40));
        
        System.out.println("Formato: AAAA-MM-DDTHH:MM:SS");
        System.out.print("\nIngrese fecha: ");
        String fechaStr = scanner.nextLine().trim();
        
        try {
            Appointment busqueda = new Appointment("BUSQUEDA", "BUSQUEDA", fechaStr);
            
            int lower = ArraySearch.lowerBound(citasOrdenadas, busqueda);
            int upper = ArraySearch.upperBound(citasOrdenadas, busqueda);
            
            System.out.println("\nRESULTADOS PARA: " + fechaStr);
            System.out.println("   Lower Bound (primera ocurrencia o donde debería estar): " + 
                (lower != -1 ? lower : "No encontrado"));
            System.out.println("   Upper Bound (última ocurrencia): " + 
                (upper != -1 ? upper : "No encontrado"));
            
            if (lower != -1) {
                System.out.println("\nElemento en Lower Bound:");
                System.out.println("   " + citasOrdenadas[lower].toString());
            }
            
            if (upper != -1 && upper != lower) {
                System.out.println("\nElemento en Upper Bound:");
                System.out.println("   " + citasOrdenadas[upper].toString());
            }
            
            if (lower == -1 && upper == -1) {
                System.out.println("\nLa fecha no existe en el arreglo");
                sugerirFechasCercanas(citasOrdenadas, busqueda);
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    private static void compararLimitesCitas(Appointment[] citasOrdenadas) {
        System.out.println("\nCOMPARACIÓN LOWER/UPPER BOUND");
        System.out.println("-".repeat(40));
        
        System.out.println("Formato: AAAA-MM-DDTHH:MM:SS");
        System.out.print("\nIngrese fecha: ");
        String fechaStr = scanner.nextLine().trim();
        
        try {
            Appointment busqueda = new Appointment("BUSQUEDA", "BUSQUEDA", fechaStr);
            
            // Buscar con diferentes métodos
            int binary = ArraySearch.binarySearch(citasOrdenadas, busqueda);
            int lower = ArraySearch.lowerBound(citasOrdenadas, busqueda);
            int upper = ArraySearch.upperBound(citasOrdenadas, busqueda);
            
            System.out.println("\nCOMPARACIÓN DE MÉTODOS:");
            System.out.println("┌────────────────────┬──────────┬─────────────────────────────┐");
            System.out.println("│ Método             │ Índice   │ Significado                 │");
            System.out.println("├────────────────────┼──────────┼─────────────────────────────┤");
            System.out.printf("│ Binary Search      │ %-8d │ %-27s │\n", 
                binary, 
                binary == -1 ? "No encontrado" : "Cualquier ocurrencia");
            System.out.printf("│ Lower Bound        │ %-8d │ %-27s │\n", 
                lower, 
                lower == -1 ? "No encontrado" : "Primera ocurrencia");
            System.out.printf("│ Upper Bound        │ %-8d │ %-27s │\n", 
                upper, 
                upper == -1 ? "No encontrado" : "Última ocurrencia");
            System.out.println("└────────────────────┴──────────┴─────────────────────────────┘");
            
            if (lower != -1 && upper != -1 && lower != upper) {
                System.out.println("\nRANGO DE DUPLICADOS:");
                System.out.println("   Hay " + (upper - lower + 1) + " citas con la misma fecha");
                System.out.println("   Índices: " + lower + " a " + upper);
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }
    
    private static void sugerirFechasCercanas(Appointment[] citas, Appointment busqueda) {
        // Encontrar la posición donde debería estar
        int posicionInsercion = -Arrays.binarySearch(citas, busqueda) - 1;
        
        if (posicionInsercion > 0 && posicionInsercion <= citas.length) {
            System.out.println("\nFECHAS CERCANAS DISPONIBLES:");
            
            // Mostrar fechas anteriores
            if (posicionInsercion > 0) {
                System.out.println("   Anterior: " + citas[posicionInsercion-1].getFechaHora());
            }
            
            // Mostrar fechas posteriores
            if (posicionInsercion < citas.length) {
                System.out.println("   Posterior: " + citas[posicionInsercion].getFechaHora());
            }
        }
    }
}