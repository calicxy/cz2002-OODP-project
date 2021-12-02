package control;

import entity.*;
import helper.StringConverter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderManager {

    private final ArrayList<Order> openOrderList;
    private final ArrayList<Order> closedOrderList;

    public OrderManager(ArrayList<Order> openOrderList, ArrayList<Order> closedOrderList) {
        this.openOrderList = openOrderList;
        this.closedOrderList = closedOrderList;
    }

    public Order getOrder(int orderCategory, int orderIndex) {
        if (orderCategory == 0) {
            return openOrderList.get(orderIndex);
        }

        return closedOrderList.get(orderIndex);
    }

    public void createOrder(int tableNo, Staff staff, Customer customer) {

        int nextOrderNo = openOrderList.size() + closedOrderList.size() + 1;
        openOrderList.add(new Order(nextOrderNo, tableNo, staff, customer, LocalDateTime.now()));
    }

    public void closeOrder(int openOrderNo) {

        Order orderToClose = openOrderList.get(openOrderNo);
        orderToClose.setTimeClosed(LocalDateTime.now());
        printReceipt(orderToClose);
        updateOrderHistoryFile(orderToClose);
        closedOrderList.add(orderToClose);
        openOrderList.remove(openOrderNo);
    }

    public void updateOrderHistoryFile(Order orderToClose) {
        // Updating order.csv
        try {
            String data;
            FileWriter CSVWriter = new FileWriter("orderdata.csv", true);

            // Date closed, Bill, Items
            data = String.format("%s,%.2f", orderToClose.getTimeClosed(), orderToClose.getTotalBill());

            for (MenuItemLine line : orderToClose.getItemOrders()) {
                data = data.concat(String.format(",%d_%s", line.getQuantity(), line.getMenuItem().getName()));
            }

            for (MenuItemLine line : orderToClose.getSetOrders()) {
                data = data.concat(String.format(",%d_%s", line.getQuantity(), line.getPromoSet().getName()));
            }

            data = data.concat(",###\n");
            CSVWriter.append(data);
            CSVWriter.flush();
            CSVWriter.close();

        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public void printReceipt(Order order) {

        double total = 0;
        double serviceCharge;
        double gst;
        double memberDiscount = 0;

        System.out.println();
        System.out.print("-----------------------------------------------\n");
        System.out.print("               St Regis Singapore              \n");
        System.out.print("               Shinji by Kanesaka              \n");

        System.out.printf("Server: %-22s Date: %s     \n", order.getStaff().getName(), StringConverter.dateToString(order.getTimeClosed()));
        System.out.printf("Table: %-23d Time: %s     \n", order.getTableNo(), StringConverter.timeToString(order.getTimeClosed()));
        System.out.print("-----------------------------------------------\n");

        for (MenuItemLine line : order.getItemOrders()) {
            System.out.printf("%-2d %-37s %6.2f\n", line.getQuantity(), line.getMenuItem().getName(), line.calculatePrice());
            total += line.calculatePrice();
        }

        for (MenuItemLine line : order.getSetOrders()) {
            System.out.printf("%-2d %-37s %6.2f\n", line.getQuantity(), line.getPromoSet().getName(), line.calculatePrice());
            total += line.calculatePrice();
        }

        serviceCharge = total * 0.1;
        gst = total * 0.07;

        System.out.print("-----------------------------------------------\n");
        System.out.printf("                             Subtotal:  $%6.2f\n", total);
        System.out.printf("                   10%% Service Charge:  $%6.2f\n", serviceCharge);
        System.out.printf("                               7%% GST:  $%6.2f\n", gst);

        if (order.getCustomer().getMembership()) {
            memberDiscount = total * 0.05;
            System.out.printf("                 5%% Members' Discount: -$%6.2f\n", memberDiscount);
        }

        System.out.printf("                          GRAND TOTAL:  $%6.2f\n", total + serviceCharge + gst - memberDiscount);
        System.out.print("***********************************************\n");
        System.out.print("*        Thank you for dining with us!        *\n");
        System.out.print("***********************************************\n");

        order.setBill(total + serviceCharge + gst - memberDiscount);
    }

    public void printOrdersInfo(boolean printOpenOrders) {

        int i = 1;
        System.out.println("\n--- LIST OF ORDERS ---");

        if (printOpenOrders) {
            for (Order order : openOrderList) {
                System.out.printf("(%-2d) ", i++);
                order.printOrderInfo(true);
            }
        } else {
            for (Order order : closedOrderList) {
                System.out.printf("(%-2d) ", i++);
                order.printOrderInfo(false);
            }
        }
    }

    public void addRemoveItems(int openOrderIndex, MenuItem item, int quantity) {
        openOrderList.get(openOrderIndex).addItemToOrder(item, quantity);
    }

    // Overloaded method for PromoSet
    public void addRemoveItems(int openOrderIndex, PromoSet set, int quantity) {
        openOrderList.get(openOrderIndex).addSetToOrder(set, quantity);
    }

    public int getListSize(boolean getOpenOrderSize) {
        if (getOpenOrderSize) {
            return openOrderList.size();
        }
        return closedOrderList.size();
    }
}
