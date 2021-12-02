package entity;

import helper.StringConverter;

import java.time.LocalDateTime;

/**
 * Represent a reservation made by a customer.
 * Every reservation will take in customer details,
 * reserved table, reserved timing and PAX.
 *
 * @author Wong Ying Xuan
 * @version 1.0
 * @since 24-10-2021
 */

public class Reservation implements Comparable<Reservation> {

    /**
     * Number of PAX for this reservation.
     */
    private final int pax;

    /**
     * Reserved timing for this reservation.
     */
    private final LocalDateTime reservationTime;

    /**
     * The details of the customer who made this reservation.
     */
    private final Customer customer;

    /**
     * NEW
     * Reserved table for this reservation.
     */
    private final Table reservedTable;

    /**
     * Create a new reservation by inputting customer details,
     * PAX, reserved timing and reserved table.
     *
     * @param pax             Number of PAX for this reservation
     * @param reservationTime Reserved timing for this reservation
     * @param table           Reserved table for this reservation
     */
    public Reservation(Customer customer, int pax, LocalDateTime reservationTime, Table table) {
        this.customer = customer;
        this.pax = pax;
        this.reservationTime = reservationTime;
        this.reservedTable = table;
    }

    /**
     * Function to get the customer of this reservation
     *
     * @return this reservation's customer
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * Function to get PAX number of this reservation
     *
     * @return this reservation's number of PAX
     */
    public int getPax() {
        return this.pax;
    }

    /**
     * Function to get reserved timing of this reservation
     *
     * @return this reservation's reserved timing
     */
    public LocalDateTime getReservationTime() {
        return this.reservationTime;
    }

    /**
     * NEW
     * Function to return the reserved table for this reservation
     *
     * @return this reservation's reserved table
     */
    public Table getTable() {
        return this.reservedTable;
    }

    /**
     * Function to print out the details of this reservation
     */
    public void printReservationDetail() {
        System.out.println();
        System.out.println("Reservation details as below:");
        System.out.printf("Customer name: %s\n", this.getCustomer().getName());
        System.out.printf("Contact no.: %d\n", this.getCustomer().getContactNo());
        System.out.printf("Membership: %s\n", this.getCustomer().getMembership());
        System.out.printf("Table ID: %2d\n", this.reservedTable.getTableId());
        System.out.printf("Reserved timing: %s %s\n", StringConverter.dateToString(this.getReservationTime()), StringConverter.timeToString(this.getReservationTime()));
        System.out.printf("Pax: %d\n", this.getPax());
    }

    public String dateTimeToString(LocalDateTime dateTime) {
        return String.format("%02d/%02d/%04d %02d:%02d", dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear(),
                dateTime.getHour(), dateTime.getMinute());
    }

    @Override
    public String toString() {
        return String.format("#%-3d %s %s %-20s %-8d %-6s %-2d",
                this.reservedTable.getTableId(), StringConverter.dateToString(this.reservationTime), StringConverter.timeToString(this.reservationTime),
                this.customer.getName(), this.customer.getContactNo(),
                this.customer.getMembership(), this.pax);
    }

    @Override
    public int compareTo(Reservation o) {
        return this.reservationTime.compareTo(o.reservationTime);
    }
}