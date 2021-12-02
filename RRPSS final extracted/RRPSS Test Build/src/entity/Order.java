package entity;

import helper.StringConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

    private final int orderNo;
    private final int tableNo;
    private final Staff staff;
    private final Customer customer;
    private final LocalDateTime timeCreated;
    private final ArrayList<MenuItemLine> itemOrders;
    private final ArrayList<MenuItemLine> setOrders;
    private double totalBill;
    private LocalDateTime timeClosed;

    public Order(int orderNo, int tableNo, Staff staff, Customer customer, LocalDateTime timeCreated) {
        this.orderNo = orderNo;
        this.tableNo = tableNo;
        this.staff = staff;
        this.customer = customer;
        this.timeCreated = timeCreated;
        this.itemOrders = new ArrayList<>();
        this.setOrders = new ArrayList<>();
    }

    public void setBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public int getTableNo() {
        return this.tableNo;
    }

    public Staff getStaff() {
        return this.staff;
    }

    public double getTotalBill() {
        return this.totalBill;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public LocalDateTime getTimeClosed() {
        return this.timeClosed;
    }

    public void setTimeClosed(LocalDateTime timeClosed) {
        this.timeClosed = timeClosed;
    }

    public ArrayList<MenuItemLine> getItemOrders() {
        return this.itemOrders;
    }

    public ArrayList<MenuItemLine> getSetOrders() {
        return this.setOrders;
    }

    public void addItemToOrder(MenuItem item, int quantity) {

        // Check if item exists in order
        for (MenuItemLine line : itemOrders) {
            if (line.getMenuItem().equals(item)) {
                line.addQuantity(quantity);

                // Sanity check - items with 0 or lower quantity is automatically removed
                if (line.getQuantity() <= 0) {
                    itemOrders.remove(line);
                }
                return;
            }
        }

        if (quantity <= 0) {
            System.out.println("Cannot add new item with quantity lesser than 1!");
            return;
        }

        itemOrders.add(new MenuItemLine(item, quantity));
    }

    public void addSetToOrder(PromoSet set, int quantity) {

        // Check if item exists in order
        for (MenuItemLine line : setOrders) {
            if (line.getMenuItem().equals(set)) {
                line.addQuantity(quantity);

                // Sanity check - items with 0 or lower quantity is automatically removed
                if (line.getQuantity() <= 0) {
                    setOrders.remove(line);
                }
                return;
            }
        }

        if (quantity <= 0) {
            System.out.println("Cannot add new item with quantity lesser than 1!");
            return;
        }

        setOrders.add(new MenuItemLine(set, quantity));
    }

    public void printOrderInfo(boolean orderIsOpen) {

        if (orderIsOpen) {
            System.out.printf("Order #%d, Table #%d, %s - ", this.orderNo, this.tableNo, this.staff.getName());
            System.out.printf("Opened at %s %s\n", StringConverter.timeToString(this.timeCreated), StringConverter.dateToString(this.timeCreated));
        } else {
            System.out.printf("Order #%d, Table #%d, - ", this.orderNo, this.tableNo);
            System.out.printf("Closed at %s %s\n", StringConverter.timeToString(this.timeClosed), StringConverter.dateToString(this.timeClosed));
        }
    }

    public void printOrderContents(boolean orderIsOpen) {

        System.out.println("This order contains the following:");

        if (orderIsOpen) {
            for (MenuItemLine line : itemOrders) {
                System.out.printf("%-2dx %s%n", line.getQuantity(), line.getMenuItem().getName());
            }

            for (MenuItemLine line : setOrders) {
                System.out.printf("%-2dx %s%n", line.getQuantity(), line.getPromoSet().getName());
            }
        }
    }
}
