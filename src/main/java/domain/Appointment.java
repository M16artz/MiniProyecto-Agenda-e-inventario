package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author MikelMZ : Miguel Armas
 */
public class Appointment implements Comparable<Appointment> {
    private String id;
    private String lastName;
    private LocalDateTime dateHour;

    // Formateador para parsear el String
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Appointment(String id, String apellido, String fechaHoraStr) {
        this.id = id;
        this.lastName = apellido;
        this.dateHour = LocalDateTime.parse(fechaHoraStr, FORMATTER);
    }

    public String getId() { return id; }
    public String getApellido() { return lastName; }
    public LocalDateTime getFechaHora() { return dateHour; }

    @Override
    public int compareTo(Appointment otra) {
        return this.dateHour.compareTo(otra.getFechaHora());
    }

    @Override
    public String toString() {
        return id + ";" + lastName + ";" + dateHour.toString();
    }
}