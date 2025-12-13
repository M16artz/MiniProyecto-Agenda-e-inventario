package util;

import ed.u2.model.Node;
import ed.u2.model.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientList {

    private Node head;

    // Agregar al final
    public void add(Patient p) {
        if (head == null) {
            head = new Node(p);
            return;
        }
        Node aux = head;
        while (aux.getNext() != null) {
            aux = aux.getNext();
        }
        aux.setNext(new Node(p));
    }

    // Buscar PRIMER paciente cuyo apellido coincide
    public Patient findFirst(String apellido) {
        Node aux = head;
        while (aux != null) {
            if (aux.getData().getApellido().equalsIgnoreCase(apellido)) {
                return aux.getData();
            }
            aux = aux.getNext();
        }
        return null;
    }

    // Buscar ULTIMO paciente cuyo apellido coincide
    public Patient findLast(String apellido) {
        Node aux = head;
        Patient last = null;

        while (aux != null) {
            if (aux.getData().getApellido().equalsIgnoreCase(apellido)) {
                last = aux.getData();
            }
            aux = aux.getNext();
        }
        return last;
    }

    // Listar todos los pacientes con prioridad 1
    public List<Patient> findAllPrioridad1() {
        List<Patient> result = new ArrayList<>();
        Node aux = head;

        while (aux != null) {
            if (aux.getData().getPrioridad() == 1) {
                result.add(aux.getData());
            }
            aux = aux.getNext();
        }

        return result;
    }

    public void printAll() {
        Node aux = head;
        while (aux != null) {
            System.out.println(aux.getData());
            aux = aux.getNext();
        }
    }

    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public Node getHead() {
        return head;
    }
    
    public boolean isEmpty() {
        return head == null;
    }

    public void setHead(Node head) {
        this.head = head;
    }

}
