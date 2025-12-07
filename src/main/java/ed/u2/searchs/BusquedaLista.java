package ed.u2.searchs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import ed.u2.sorting.Node;

/**
 *
 * @author M16artz : Miguel Armas
 * @author RichardC022 : Richard Cajas
 * @author Mzero11 : Mateo Silva
 */
public class BusquedaLista {

    public static <T extends Comparable<T>> Node<T> findFirst(Node<T> cabeza, T key) {
        if (cabeza == null) {
            return null;
        }

        Node<T> actual = cabeza;
        while (actual != null) {
            if (actual.getData().compareTo(key) == 0) {
                return actual; // primera coincidencia
            }
            actual = actual.getNext();
        }
        return null;
    }

    public static <T extends Comparable<T>> Node<T> findLast(Node<T> head, T key) {
        if (head == null) {
            return null;
        }

        Node<T> current = head;
        Node<T> last = null;

        while (current != null) {
            if (current.getData().compareTo(key) == 0) {
                last = current;
            }
            current = current.getNext();
        }
        return last;
    }

    public static <T extends Comparable<T>> List<Node<T>> findAll(Node<T> head, Predicate<T> p) {
        List<Node<T>> result = new ArrayList<>();
        if (head == null) {
            return result;
        }

        Node<T> current = head;
        while (current != null) {
            if (p.test(current.getData())) {
                result.add(current);
            }
            current = current.getNext();
        }
        return result;
    }
}
