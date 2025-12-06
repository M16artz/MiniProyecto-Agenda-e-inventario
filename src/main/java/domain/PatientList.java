package domain;

import java.util.ArrayList;
import java.util.List;

public class PatientList {
    private static class Node {
        Patient data;
        Node next;

        Node(Patient p) {
            this.data = p;
        }
    }

    private Node head;

    // Agregar al final
    public void add(Patient p) {
        if (head == null) {
            head = new Node(p);
            return;
        }
        Node aux = head;
        while (aux.next != null) {
            aux = aux.next;
        }
        aux.next = new Node(p);
    }

    // Buscar PRIMER paciente cuyo apellido coincide
    public Patient findFirst(String apellido) {
        Node aux = head;
        while (aux != null) {
            if (aux.data.getApellido().equalsIgnoreCase(apellido)) {
                return aux.data;
            }
            aux = aux.next;
        }
        return null;
    }

    // Buscar ULTIMO paciente cuyo apellido coincide
    public Patient findLast(String apellido) {
        Node aux = head;
        Patient last = null;

        while (aux != null) {
            if (aux.data.getApellido().equalsIgnoreCase(apellido)) {
                last = aux.data;
            }
            aux = aux.next;
        }
        return last;
    }

    // Listar todos los pacientes con prioridad 1
    public List<Patient> findAllPrioridad1() {
        List<Patient> out = new ArrayList<>();
        Node aux = head;

        while (aux != null) {
            if (aux.data.getPrioridad() == 1) {
                out.add(aux.data);
            }
            aux = aux.next;
        }

        return out;
    }

    public void printAll() {
        Node aux = head;
        while (aux != null) {
            System.out.println(aux.data);
            aux = aux.next;
        }
    }
}
