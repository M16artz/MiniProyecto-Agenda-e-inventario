package util;

import domain.Appointment;
import domain.Item;
import domain.Patient;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MikelMZ : Miguel Armas
 */
public class CsvLoader {

    public static Appointment[] loadCitas(String filePath) throws IOException {
        List<Appointment> lista = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    // parts[0]=id, parts[1]=apellido, parts[2]=fechaHora
                    lista.add(new Appointment(parts[0], parts[1], parts[2]));
                }
            }
        }
        return lista.toArray(Appointment[]::new);
    }

   public static List<Patient> loadPacientes(String filePath) throws IOException {
    List<Patient> lista = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
        br.readLine();
        
        String line;
        
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length == 3) {
                Patient p = new Patient();
                p.setId(parts[0]);
                p.setApellido(parts[1]);
                // parts[2] es prioridad (int)
                p.setPrioridad(Integer.parseInt(parts[2]));
                lista.add(p);
            }
        }
    }
    return lista;
}

 public static Item[] loadInventario(String filePath) throws IOException {
        List<Item> lista = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    // parts[0]=id, parts[1]=insumo, parts[2]=stock
                    lista.add(new Item(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
        }
        return lista.toArray(new Item[0]);
    }
}