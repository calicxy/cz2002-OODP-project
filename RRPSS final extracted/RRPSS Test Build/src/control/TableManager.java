package control;

import entity.Reservation;
import entity.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TableManager {

    private static final int NO_OF_TABLES = 10;
    private static final int START_TIME = 10;
    private static final int END_TIME = 22;
    private static final Table[] tableList = new Table[NO_OF_TABLES];

    // Create 10 tables
    public TableManager() {
        int count = 0;
        for (int i = 0; i < NO_OF_TABLES; i++) {
            if (i % 2 == 0) {
                count += 2;
            }
            tableList[i] = new Table(i + 1, count);
        }
    }

    // Function to return the whole table object
    public Table getTable(int tableId, int capacity) {
        for (Table t : tableList) {
            if (t.getTableId() == tableId && t.getCapacity() == capacity) {
                return t;
            }
        }
        return null;
    }

    // Function to return the list of available table for an input timing
    public ArrayList<Table> checkAvailableTable(LocalDateTime timing, int pax, ArrayList<Reservation> activeReservations) {
        ArrayList<Table> tablesMatchPax = new ArrayList<>();
        ArrayList<Table> availableTable = new ArrayList<>();
        Table table;

        if ((timing.getHour() <= START_TIME) || (timing.getHour() >= END_TIME)) {
            return availableTable;
        }

        for (Table t : tableList) {
            if (t.getCapacity() >= pax) {
                tablesMatchPax.add(t);
            }
        }

        for (Table t : tablesMatchPax) {
            availableTable.add(t);

            for (Reservation re : activeReservations) {
                // For table to be reserved at that time, BOTH table ID and timing must be the same
                if ((re.getTable() == t)  &&  (re.getReservationTime().isEqual(timing))) {
                    availableTable.remove(t);
                }
            }
        }

        return availableTable;
    }

    // Function to allocate table for reservation
    public Table findTableForReservation(LocalDateTime timing, int pax, ArrayList<Reservation> activeReservations) {
        ArrayList<Table> availableTable = checkAvailableTable(timing, pax, activeReservations);

        if (availableTable.size() <= 0)
            return null;

        // Get the available table with smallest capacity
        return availableTable.get(0);
    }
}