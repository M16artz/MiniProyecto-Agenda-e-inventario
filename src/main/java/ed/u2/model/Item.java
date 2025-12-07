package ed.u2.model;

/**
 * @author MikelMZ : Miguel Armas
 */
public class Item implements Comparable<Item> {
    private String id;
    private String name;
    private int stock;

    public Item (String id, String insumo, int stock) {
        this.id = id;
        this.name = insumo;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getInsumo() { return name; }
    public int getStock() { return stock; }

    @Override
    public int compareTo(Item otro) {
        return Integer.compare(this.stock, otro.getStock());
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + stock;
    }
}