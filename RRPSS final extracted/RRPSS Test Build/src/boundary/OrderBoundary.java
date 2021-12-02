package boundary;

import control.MenuManager;
import control.OrderManager;
import control.ReservationManager;
import control.StaffManager;
import entity.Reservation;
import helper.ScannerPlus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class OrderBoundary {

    private final MenuManager menuManager;
    private final OrderManager orderManager;
    private final StaffManager staffManager;
    private final ReservationManager reservationManager;
    private final ScannerPlus scannerPlus = new ScannerPlus();

    public OrderBoundary(MenuManager menuManager, OrderManager orderManager, StaffManager staffManager, ReservationManager reservationManager) {
        this.menuManager = menuManager;
        this.orderManager = orderManager;
        this.staffManager = staffManager;
        this.reservationManager = reservationManager;
    }

    public void init() {

        int choice;
        do {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("-         ORDER MANAGER        -");
            System.out.println("--------------------------------");
            System.out.println("[1] Create New Order");
            System.out.println("[2] Add/Remove Item from Order");
            System.out.println("[3] Print Order Details");
            System.out.println("[4] Close Order & Print Receipt");
            System.out.println("[5] Reprint Receipt");
            System.out.println("[6] Exit Order Manager");
            choice = scannerPlus.nextIntRange("Enter choice", 1, 6);

            switch (choice) {
                case 1 -> createOrder();
                case 2 -> addRemoveItems();
                case 3 -> printOrdersInfo();
                case 4 -> closeOrder();
                case 5 -> printReceipt();
                case 6 -> System.out.println("Returning...");
            }
        } while (choice != 6);

        System.out.println();
    }

    public void createOrder() {

        int reservationIndex;
        Reservation reservation;

        if (staffManager.getStaffListLength() == 0) {
            System.out.println("There are currently no available staff.");
            return;
        }

        if (reservationManager.getReservationListLength() == 0) {
            System.out.println("There are currently no active reservations.");
            return;
        }

        while (true) {
            reservationManager.printReservationList();
            reservationIndex = scannerPlus.nextIntRange("Select reservation to create order for (0 to abort)", 0, reservationManager.getReservationListLength()) - 1;

            if (reservationIndex == -1) {
                System.out.println("Aborting...");
                return;
            }

            reservation = reservationManager.getReservationList().get(reservationIndex);

            // Check if order is created too early
            if (ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getReservationTime()) >= 15) {
                System.out.println("Order can only be created maximum 15 minutes in advance.");
                continue;
            }
            break;
        }
        reservationManager.cancelReservation(reservation);

        staffManager.printStaffList();
        int staffIndex = scannerPlus.nextIntRange("Select waiter/waitress", 1, staffManager.getStaffListLength()) - 1;

        orderManager.createOrder(reservation.getTable().getTableId(), staffManager.getStaff(staffIndex), reservation.getCustomer());
    }

    public void addRemoveItems() {

        if (orderManager.getListSize(true) == 0) {
            System.out.println("There are currently no open orders.");
            return;
        }

        orderManager.printOrdersInfo(true);

        int orderIndex = scannerPlus.nextIntRange("Select order to add/remove items", 0, orderManager.getListSize(true)) - 1;

        while (true) {
            System.out.println();
            System.out.println("Please select category:");
            System.out.println("[1] Main Course");
            System.out.println("[2] Dessert");
            System.out.println("[3] Drink");
            System.out.println("[4] Promotional Set");

            int categoryNo = scannerPlus.nextIntRange("Select item category to add/remove items (0 to stop)", 0, 4) - 1;

            if (categoryNo == -1) {
                break;
            }

            menuManager.printMenu(categoryNo);
            int itemChoice = scannerPlus.nextIntRange("Select menu item", 0, menuManager.getMenuListLength(categoryNo)) - 1;
            int quantity = scannerPlus.nextInt("Enter quantity to add/remove");

            // Add/Remove PromoSets
            if (categoryNo == 3) {
                orderManager.addRemoveItems(orderIndex, menuManager.getPromoSet(itemChoice), quantity);
            } else {
                orderManager.addRemoveItems(orderIndex, menuManager.getMenuItem(categoryNo, itemChoice), quantity);
            }
        }
    }

    public void printOrdersInfo() {

        System.out.println("\n--- Order Categories ---");
        System.out.println("[1] Open Orders");
        System.out.println("[2] Closed Orders");
        int categoryToPrint = scannerPlus.nextIntRange("Select order category to print", 1, 2);

        if (categoryToPrint == 1) {

            if (orderManager.getListSize(true) == 0) {
                System.out.println("There are currently no open orders.");
                return;
            }

            orderManager.printOrdersInfo(true);

            int orderIndex = scannerPlus.nextIntRange("Select order to view contents (0 to skip)", 0, orderManager.getListSize(true)) - 1;

            if (orderIndex == -1) {
                return;
            }

            System.out.println("---------------------------");
            orderManager.getOrder(0, orderIndex).printOrderInfo(true);
            orderManager.getOrder(0, orderIndex).printOrderContents(true);
        } else {
            if (orderManager.getListSize(false) == 0) {
                System.out.println("There are currently no closed orders.");
                return;
            }

            orderManager.printOrdersInfo(false);

            int orderIndex = scannerPlus.nextIntRange("Select order to view contents (0 to skip)", 0, orderManager.getListSize(false)) - 1;

            if (orderIndex == -1) {
                return;
            }

            System.out.println("---------------------------");
            orderManager.getOrder(1, orderIndex).printOrderInfo(false);
            orderManager.getOrder(1, orderIndex).printOrderContents(false);
        }
    }

    public void closeOrder() {

        if (orderManager.getListSize(true) == 0) {
            System.out.println("There are currently no open orders.");
            return;
        }

        orderManager.printOrdersInfo(true);
        int orderIndex = scannerPlus.nextIntRange("Select order to close", 0, orderManager.getListSize(true)) - 1;
        orderManager.closeOrder(orderIndex);
    }

    public void printReceipt() {

        if (orderManager.getListSize(false) == 0) {
            System.out.println("There are currently no closed orders.");
            return;
        }

        orderManager.printOrdersInfo(false);
        int orderIndex = scannerPlus.nextIntRange("Select order to print receipt", 0, orderManager.getListSize(false)) - 1;
        orderManager.printReceipt(orderManager.getOrder(1, orderIndex));
    }
}
