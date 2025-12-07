package ed.u2.model;

/**
 * @author MikelMZ : Miguel Armas
 */
public class Patient implements Comparable<Patient>{
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
    public int compareTo(Patient otro) {
        return this.lastName.compareTo(otro.getApellido());
    }

    @Override
    public String toString() {
        return id + ";" + lastName + ";" + priority;
    }
    
}
