package ed.u2.searchs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import ed.u2.model.Node;
import ed.u2.model.Patient;

/**
 *
 * @author M16artz : Miguel Armas
 * @author RichardC022 : Richard Cajas
 * @author Mzero11 : Mateo Silva
 */
public class ListSearch {

    public static Node findFirst(Node head, Patient key) {
    Node current = head;
    while (current != null) {
        if (current.getData().compareTo(key) == 0) {
            return current;
        }
        current = current.getNext();
    }
    return null;
}


    public static Node findLast(Node head, Patient key) {
        if (head == null) {
            return null;
        }

        Node current = head;
        Node last = null;

        while (current != null) {
            if (current.getData().compareTo(key) == 0) {
                last = current;
            }
            current = current.getNext();
        }
        return last;
    }

    public static  List<Node> findAll(Node head, Predicate<Patient> p) {
        List<Node> result = new ArrayList<>();
        if (head == null) {
            return result;
        }

        Node current = head;
        while (current != null) {
            if (p.test(current.getData())) {
                result.add(current);
            }
            current = current.getNext();
        }
        return result;
    }
}
