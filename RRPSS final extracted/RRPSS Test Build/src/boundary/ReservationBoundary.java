package boundary;

import control.ReservationManager;
import control.TableManager;
import entity.Customer;
import entity.Table;
import helper.Gender;
import helper.ScannerPlus;
import helper.StringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Boundary class which will be interacting with the users.
 * Contain functions: make, cancel and check reservations,
 * print table availability,
 * print expired/cancelled/settled reservations
 *
 * @author Wong Ying Xuan
 * @version 1.0
 * @since 24-10-2021
 */

public class ReservationBoundary {

    /**
     * Scanner which will be reading in all the inputs from user.
     */
    private final Scanner sc = new Scanner(System.in);
    private final ScannerPlus scannerPlus = new ScannerPlus();
    private final ReservationManager reservationManager;
    private final TableManager tableManager;

    public ReservationBoundary(ReservationManager reservationManager, TableManager tableManager) {
        this.reservationManager = reservationManager;
        this.tableManager = tableManager;
    }

    public void init() {

        int choice;
        do {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("-      RESERVATION MANAGER     -");
            System.out.println("--------------------------------");
            System.out.println("[1] Make Reservation");
            System.out.println("[2] Check Reservations");
            System.out.println("[3] Cancel Reservation");
            System.out.println("[4] Check Table Availability");
            System.out.println("[5] Exit Reservation Manager");
            choice = scannerPlus.nextIntRange("Select choice", 1, 5);

            switch (choice) {
                case 1 -> makeReservation();
                case 2 -> checkReservation();
                case 3 -> cancelReservation();
                case 4 -> printTableAvailability();
                case 5 -> System.out.println("Returning...\n");
            }
        } while (choice != 5);
    }

    /**
     * Function to make reservation, it will ask input of customer's details,
     * PAX and reserved timing. After that, it will make reservation and
     * print out reservation details if it's successful.
     */
    public void makeReservation() {

        LocalDateTime timing = getDateTime();

        if (timing.isBefore(LocalDateTime.now())) {
            System.out.println("Reservation cannot be made for a period in the past!");
            return;
        }

        System.out.print("Enter customer name :: ");
        String name = sc.nextLine();

        int contact = scannerPlus.nextInt("Enter customer contact no.");

        Gender custGender;
        System.out.println("Please select gender.");
        System.out.println("[1] Male");
        System.out.println("[2] Female");
        System.out.println("[3] Others");
        custGender = switch (scannerPlus.nextIntRange("Select choice", 1, 3)) {
            case 1 -> Gender.MALE;
            case 2 -> Gender.FEMALE;
            default -> Gender.OTHERS;
        };

        boolean isMember = scannerPlus.nextBoolean("Enter membership (true/false)");
        int pax = scannerPlus.nextIntRange("Enter pax",2,10);

        Customer customer = new Customer(name, contact, isMember, custGender);
        reservationManager.createReservation(customer, pax, timing);
    }

    /**
     * Function to check reservations, it's divided into 5 components.
     * (1) Print all reservations by tables
     * (2) Check specific reservations by inputting customer's details and his/her reserved timing
     * (3) Check current reservation given a table ID
     * (4) Check all active reservations by inputting table ID
     * (5) Check current reservation by inputting table ID
     */
    public void checkReservation() {

        System.out.println();
        System.out.println("Available options:");
        System.out.println("[1] Print all reservations");
        System.out.println("[2] Find reservation by name");
        System.out.println("[3] Find reservation by table ID");

        switch (scannerPlus.nextIntRange("Select choice", 1, 3)) {
            case 1 -> reservationManager.printReservationList();
            case 2 -> {
                System.out.print("Enter customer name :: ");
                String name = sc.nextLine();
                reservationManager.findReservationByName(name);
            }
            case 3 -> {
                int tableId0 = scannerPlus.nextInt("Enter table ID");
                reservationManager.findReservationByTable(tableId0);
            }
        }
    }

    /**
     * Function to remove reservation if requested by customer.
     * It will ask input of customer's details and timing,
     * and remove that reservation if it ever existed.
     */
    public void cancelReservation() {

        reservationManager.printReservationList();

        int index = scannerPlus.nextIntRange("Select reservation to cancel (0 to abort)", 0, reservationManager.getReservationListLength()) - 1;

        if (index == -1) {
            System.out.println("Aborting...");
            return;
        }
        reservationManager.cancelReservation(index);
    }

    /**
     * Function to print out available table based on timing and PAX.
     */
    public void printTableAvailability() {
        LocalDateTime timing = getDateTime();

        if (timing.isBefore(LocalDateTime.now())) {
            System.out.println("Availability cannot be checked for a period in the past!");
            return;
        }

        int pax = scannerPlus.nextIntRange("Enter pax",2,10);

        ArrayList<Table> availableTable = tableManager.checkAvailableTable(timing, pax, reservationManager.getReservationList());

        if (availableTable.size() <= 0)
            System.out.printf("No tables for %d person(s) available at %s\n", pax, timing);
        else {
            System.out.printf("\nAvailable tables for %s %s:\n", StringConverter.dateToString(timing), StringConverter.timeToString(timing));

            for (Table table : availableTable) {
                System.out.printf("Table #%02d with capacity %d\n", table.getTableId(), table.getCapacity());
            }
        }
    }

    /**
     * Function to get date-time input from the user
     *
     * @return input timing with LocalDateTime format
     */
    public LocalDateTime getDateTime() {
        String date;

        while (true) {

            try {
                System.out.print("Enter 24h date/time (dd/MM/yyyy HH) :: ");
                date = sc.nextLine().concat(":00");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                return LocalDateTime.parse(date, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error. Please enter datetime in the correct format.\n");
            }
        }
    }
}