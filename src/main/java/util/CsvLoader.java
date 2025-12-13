package util;

import ed.u2.model.Appointment;
import ed.u2.model.Item;
import ed.u2.model.Patient;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MikelMZ : Miguel Armas
 * Clase mejorada con validación robusta de CSV
 */
public class CsvLoader {

    public static Appointment[] loadCitas(String filePath) throws IOException {
        List<Appointment> lista = new ArrayList<>();
        int lineNumber = 0;
        int errors = 0;
        
        System.out.println("Cargando citas desde: " + filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String header = br.readLine();
            lineNumber++;
            
            if (header == null || !header.contains(";")) {
                throw new IOException("Encabezado inválido o archivo vacío: " + filePath);
            }
            
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                if (line.trim().isEmpty()) {
                    continue; // Saltar líneas vacías
                }
                
                try {
                    String[] parts = line.split(";");
                    if (parts.length != 3) {
                        System.err.println("Línea " + lineNumber + ": Formato incorrecto (esperaba 3 campos): " + line);
                        errors++;
                        continue;
                    }
                    
                    // Validar campos
                    String id = parts[0].trim();
                    String apellido = parts[1].trim();
                    String fechaStr = parts[2].trim();
                    
                    if (id.isEmpty()) {
                        System.err.println("Línea " + lineNumber + ": ID vacío");
                        errors++;
                        continue;
                    }
                    
                    if (apellido.isEmpty()) {
                        System.err.println("Línea " + lineNumber + ": Apellido vacío");
                        errors++;
                        continue;
                    }
                    
                    // Validar formato de fecha
                    try {
                        LocalDateTime.parse(fechaStr);
                    } catch (DateTimeParseException e) {
                        System.err.println("Línea " + lineNumber + ": Fecha inválida '" + fechaStr + "': " + e.getMessage());
                        errors++;
                        continue;
                    }
                    
                    lista.add(new Appointment(id, apellido, fechaStr));
                    
                } catch (Exception e) {
                    System.err.println("Línea " + lineNumber + ": Error procesando línea - " + e.getMessage());
                    errors++;
                }
            }
            
            if (errors > 0) {
                System.err.println("Advertencia: " + errors + " errores encontrados al cargar " + filePath);
            }
            
            if (lista.isEmpty()) {
                System.err.println("Advertencia: No se cargaron citas desde " + filePath);
            }
            
        } catch (IOException e) {
            throw new IOException("Error al leer archivo " + filePath + ": " + e.getMessage(), e);
        }
        
        System.out.println("Citas cargadas exitosamente: " + lista.size() + " registros");
        return lista.toArray(new Appointment[0]);
    }

    public static List<Patient> loadPacientes(String filePath) throws IOException {
        List<Patient> lista = new ArrayList<>();
        int lineNumber = 0;
        int errors = 0;
        
        System.out.println("Cargando pacientes desde: " + filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String header = br.readLine();
            lineNumber++;
            
            if (header == null || !header.contains(";")) {
                throw new IOException("Encabezado inválido o archivo vacío: " + filePath);
            }
            
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    String[] parts = line.split(";");
                    if (parts.length != 3) {
                        System.err.println("Línea " + lineNumber + ": Formato incorrecto (esperaba 3 campos): " + line);
                        errors++;
                        continue;
                    }
                    
                    String id = parts[0].trim();
                    String apellido = parts[1].trim();
                    String prioridadStr = parts[2].trim();
                    
                    if (id.isEmpty()) {
                        System.err.println("Línea " + lineNumber + ": ID vacío");
                        errors++;
                        continue;
                    }
                    
                    if (apellido.isEmpty()) {
                        System.err.println("Línea " + lineNumber + ": Apellido vacío");
                        errors++;
                        continue;
                    }
                    
                    // Validar prioridad
                    int prioridad;
                    try {
                        prioridad = Integer.parseInt(prioridadStr);
                        if (prioridad < 1 || prioridad > 5) {
                            System.err.println("Línea " + lineNumber + ": Prioridad inválida (debe ser 1-5): " + prioridad);
                            errors++;
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Línea " + lineNumber + ": Prioridad no es un número: " + prioridadStr);
                        errors++;
                        continue;
                    }
                    
                    Patient p = new Patient();
                    p.setId(id);
                    p.setApellido(apellido);
                    p.setPrioridad(prioridad);
                    lista.add(p);
                    
                } catch (Exception e) {
                    System.err.println("Línea " + lineNumber + ": Error procesando línea - " + e.getMessage());
                    errors++;
                }
            }
            
            if (errors > 0) {
                System.err.println("Advertencia: " + errors + " errores encontrados al cargar " + filePath);
            }
            
            if (lista.isEmpty()) {
                System.err.println("Advertencia: No se cargaron pacientes desde " + filePath);
            }
            
        } catch (IOException e) {
            throw new IOException("Error al leer archivo " + filePath + ": " + e.getMessage(), e);
        }
        
        System.out.println("Pacientes cargados exitosamente: " + lista.size() + " registros");
        return lista;
    }

    public static Item[] loadInventario(String filePath) throws IOException {
        List<Item> lista = new ArrayList<>();
        int lineNumber = 0;
        int errors = 0;
        
        System.out.println("Cargando inventario desde: " + filePath);
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String header = br.readLine();
            lineNumber++;
            
            if (header == null || !header.contains(";")) {
                throw new IOException("Encabezado inválido o archivo vacío: " + filePath);
            }
            
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    String[] parts = line.split(";");
                    if (parts.length != 3) {
                        System.err.println("Línea " + lineNumber + ": Formato incorrecto (esperaba 3 campos): " + line);
                        errors++;
                        continue;
                    }
                    
                    String id = parts[0].trim();
                    String insumo = parts[1].trim();
                    String stockStr = parts[2].trim();
                    
                    if (id.isEmpty()) {
                        System.err.println("Línea " + lineNumber + ": ID vacío");
                        errors++;
                        continue;
                    }
                    
                    if (insumo.isEmpty()) {
                        System.err.println("Línea " + lineNumber + ": Nombre de insumo vacío");
                        errors++;
                        continue;
                    }
                    
                    // Validar stock
                    int stock;
                    try {
                        stock = Integer.parseInt(stockStr);
                        if (stock < 0) {
                            System.err.println("Línea " + lineNumber + ": Stock negativo no permitido: " + stock);
                            errors++;
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Línea " + lineNumber + ": Stock no es un número: " + stockStr);
                        errors++;
                        continue;
                    }
                    
                    lista.add(new Item(id, insumo, stock));
                    
                } catch (Exception e) {
                    System.err.println("Línea " + lineNumber + ": Error procesando línea - " + e.getMessage());
                    errors++;
                }
            }
            
            if (errors > 0) {
                System.err.println("Advertencia: " + errors + " errores encontrados al cargar " + filePath);
            }
            
            if (lista.isEmpty()) {
                System.err.println("Advertencia: No se cargaron items desde " + filePath);
            }
            
        } catch (IOException e) {
            throw new IOException("Error al leer archivo " + filePath + ": " + e.getMessage(), e);
        }
        
        System.out.println("Inventario cargado exitosamente: " + lista.size() + " registros");
        return lista.toArray(new Item[0]);
    }
    
    /**
     * Método auxiliar para validar que un archivo existe y es legible
     */
    public static boolean validateFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            return br.readLine() != null;
        } catch (IOException e) {
            return false;
        }
    }
}