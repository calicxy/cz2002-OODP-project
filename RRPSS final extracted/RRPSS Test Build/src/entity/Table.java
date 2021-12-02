package entity;

public class Table {

    private final int tableId;
    private final int capacity;

    public Table(int ID, int capacity) {
        this.tableId = ID;
        this.capacity = capacity;
    }

    public int getTableId() {
        return this.tableId;
    }

    public int getCapacity() {
        return this.capacity;
    }
}