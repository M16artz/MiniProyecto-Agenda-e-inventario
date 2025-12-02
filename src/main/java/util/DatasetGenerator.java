package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author MikelMZ : Miguel Armas
 */
public class DatasetGenerator {
    // Semilla fija 
    private static final Random random = new Random(42);
    
    // Lista de apellidos para generar datos realistas
    private static final String[] APELLIDOS = {
        "Garcia", "Lopez", "Martinez", "Rodriguez", "Fernandez", "Silva", "Perez", "Gomez", 
        "Torres", "Vargas", "Rios", "Mendoza", "Castillo", "Espinoza", "Romero", "Guerrero"
    };

    public static void generar() {
        System.out.println("Generando datasets...");
        
        List<String> citas = generarCitas(100);
        guardarCsv("citas_100.csv", "id;apellido;fechaHora", citas);
        
        // Ordenamos la lista anterior por fecha (posición 2 del string split)
        List<String> citasOrdenadas = new ArrayList<>(citas);
        citasOrdenadas.sort(Comparator.comparing(line -> line.split(";")[2]));
        
        // Aplicamos 5% de swaps (5 intercambios en 100 datos)
        aplicarSwaps(citasOrdenadas, 5); 
        guardarCsv("citas_100_casi_ordenadas.csv", "id;apellido;fechaHora", citasOrdenadas);
        
        // 3. pacientes_500.csv
        List<String> pacientes = generarPacientes(500);
        guardarCsv("pacientes_500.csv", "id;apellido;prioridad", pacientes);
        
        // 4. inventario_500_inverso.csv
        List<String> inventario = generarInventarioInverso(500);
        guardarCsv("inventario_500_inverso.csv", "id;insumo;stock", inventario);
        
        System.out.println("¡Datasets generados exitosamente en la carpeta raíz!");
    }

    private static List<String> generarCitas(int n) {
        List<String> lista = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        //se define la fecha inicial como 2025-Enero-1 8H0min
        LocalDateTime baseDate = LocalDateTime.of(2025, 1, 1, 8, 0);
        
        for (int i = 1; i <= n; i++) {
            String id = String.format("CITA-%03d", i);
            String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
            // Fecha aleatoria a lo largo de un año
            LocalDateTime fecha = baseDate.plusDays(random.nextInt(365))
                                          .plusHours(random.nextInt(10))
                                          .plusMinutes(random.nextInt(60));
            lista.add(id + ";" + apellido + ";" + fecha.format(fmt));
        }
        return lista;
    }

    private static void aplicarSwaps(List<String> lista, int cantidad) {
        for (int k = 0; k < cantidad; k++) {
            int i = random.nextInt(lista.size());
            int j = random.nextInt(lista.size());
            Collections.swap(lista, i, j);
        }
    }

    private static List<String> generarPacientes(int n) {
        List<String> lista = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            String id = String.format("PAC-%04d", i);
            String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
            int prioridad = random.nextInt(3) + 1; // 1, 2 o 3
            lista.add(id + ";" + apellido + ";" + prioridad);
        }
        return lista;
    }

    private static List<String> generarInventarioInverso(int n) {
        List<String> lista = new ArrayList<>();
        int stock = n; // Empieza en 500 y baja
        for (int i = 1; i <= n; i++) {
            String id = String.format("ITEM-%04d", i);
            String insumo = "Insumo_" + (char)('A' + random.nextInt(26)) + random.nextInt(100);
            lista.add(id + ";" + insumo + ";" + stock);
            stock--; // Orden estrictamente descendente
        }
        return lista;
    }

    private static void guardarCsv(String nombre, String header, List<String> datos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/main/java/data/" + nombre, StandardCharsets.UTF_8))) {
            pw.println(header);
            for (String linea : datos) {
                pw.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error escribiendo " + nombre + ": " + e.getMessage());
        }
    }
}
