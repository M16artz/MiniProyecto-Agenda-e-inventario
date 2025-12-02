package domain;

/**
 * @author MikelMZ : Miguel Armas
 */
public class Paciente implements Comparable<Paciente>{
    private String id;
    private String lastName;
    private int priority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApellido() {
        return lastName;
    }

    public void setApellido(String apellido) {
        this.lastName = apellido;
    }

    public int getPrioridad() {
        return priority;
    }

    public void setPrioridad(int prioridad) {
        this.priority = prioridad;
    }

    @Override
    public int compareTo(Paciente otro) {
        return this.lastName.compareTo(otro.getApellido());
    }

    @Override
    public String toString() {
        return id + ";" + lastName + ";" + priority;
    }
    
}
