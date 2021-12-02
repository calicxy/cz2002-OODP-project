package control;

import entity.Customer;
import entity.Reservation;
import entity.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class ReservationManager {

    private final ArrayList<Reservation> reservationList;
    private final TableManager tableManager;

    // Constructor will read in the CSV files for reservations
    // Create two lists of active and expired reservations
    public ReservationManager(ArrayList<Reservation> reservationList, TableManager tableManager) {
        this.reservationList = reservationList;
        this.tableManager = tableManager;
    }

    public ArrayList<Reservation> getReservationList() {
        return this.reservationList;
    }

    public void findReservationByName(String name) {

        int counter = 1;

        for (Reservation re : reservationList) {
            if (re.getCustomer().getName().equalsIgnoreCase(name)) {
                System.out.println("---- T.NO DATE       TIME     NAME                 CONTACT  MEMBER PAX");
                System.out.println(String.format("(%02d) ", counter++) + re);
            }
        }
    }

    public void cancelReservation(Reservation re) {
        this.reservationList.remove(re);
    }

    // Function to make reservation
    public void createReservation(Customer customer, int pax, LocalDateTime timing) {
        Reservation reservation;
        Table reservedTable = tableManager.findTableForReservation(timing, pax, reservationList);

        if (reservedTable != null) {
            reservation = new Reservation(customer, pax, timing, reservedTable);
            reservationList.add(reservation);
            reservation.printReservationDetail();
        }
        else {
            System.out.print("Reservation for " + timing + " for " + pax + " person cannot be made!\n");
        }

        Collections.sort(reservationList);
    }

    // Function to cancel reservation if requested by customer
    public void cancelReservation(int index) {
        this.reservationList.remove(index);
    }

    public int getReservationListLength() {
        return reservationList.size();
    }

    // Function to print reservation list (either active/expired)
    public void printReservationList() {

        int counter = 1;
        System.out.println();

        System.out.println("---- T.NO DATE       TIME     NAME                 CONTACT  MEMBER PAX");
        for (Reservation re : reservationList) {
            System.out.println(String.format("(%-2d) ", counter++) + re);
        }
    }

    // Function to show all existing reservations for indicated table
    public void findReservationByTable(int tableId) {

        int counter = 1;
        System.out.println();

        for (Reservation re : reservationList) {
            if (re.getTable().getTableId() == tableId) {
                System.out.println("---- T.NO DATE       TIME     NAME                 CONTACT  MEMBER PAX");
                System.out.println(String.format("(%-2d) ", counter++) + re);
            }
        }
    }
}