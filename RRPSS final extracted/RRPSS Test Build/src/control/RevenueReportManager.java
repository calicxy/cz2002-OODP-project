package control;

import entity.PastOrderData;
import helper.CSVParserOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RevenueReportManager {

    private ArrayList<PastOrderData> pastOrderDataList;

    public RevenueReportManager() {
    }

    public void printDay(boolean currentDay) {

        pastOrderDataList = CSVParserOrder.readFromCSV("orderdata.csv");

        Map<String, Integer> Day = new HashMap<>();
        double totalRevenue = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (pastOrderDataList.size() == 0) {
            System.out.println("No report can be found.");
            return;
        }

        if (currentDay) {
            System.out.println();
            System.out.print("-----------------------------------------------\n");
            LocalDateTime now = LocalDateTime.now();
            String thisDay = dtf.format(now);
            System.out.printf("              Date: %s               \n", thisDay);
            for (int i = pastOrderDataList.size(); i > 0; i--) {
                PastOrderData order = pastOrderDataList.get(i - 1);
                String orderDate = order.getTimeClosed().format(dtf);
                if (thisDay.equals(orderDate)) {
                    for (String item : order.getItemLine()) {
                        String[] itemInfo = item.split("_");
                        if (Day.containsKey(itemInfo[1])) { // item exists in map
                            Day.put(itemInfo[1], Day.get(itemInfo[1]) + Integer.parseInt(itemInfo[0]));
                        } else {
                            Day.put(itemInfo[1], Integer.parseInt(itemInfo[0]));
                        }
                    }
                    totalRevenue += order.getBill();
                } else {
                    System.out.println("Item Name:                       Quantity Sold:");
                    for (String key : Day.keySet()) {
                        System.out.printf("%-40s%7d\n", key, Day.get(key));
                    }
                    System.out.printf("Total Revenue:                       %10.2f%n", totalRevenue);
                    System.out.print("-----------------------------------------------\n");
                    System.out.println();
                    return;
                }
            }
        } else {
            String date = "";
            for (PastOrderData order : pastOrderDataList) {
                if (date.equals("")) {
                    System.out.println();
                    System.out.print("-----------------------------------------------\n");
                    date = order.getTimeClosed().format(dtf);
                    System.out.printf("              Date: %s               \n", date);
                } else if (!(date.equals(order.getTimeClosed().format(dtf)))) { // next day
                    System.out.println("Item Name:                       Quantity Sold:");
                    for (String key : Day.keySet()) {
                        System.out.printf("%-40s%7d\n", key, Day.get(key));
                    }
                    System.out.printf("Total Revenue:                       %10.2f%n", totalRevenue);
                    System.out.print("-----------------------------------------------\n");
                    date = order.getTimeClosed().format(dtf);
                    System.out.printf("             Date: %s               \n", date);
                    Day.clear();
                    totalRevenue = 0;
                }
                for (String item : order.getItemLine()) {
                    String[] itemInfo = item.split("_");
                    if (Day.containsKey(itemInfo[1])) { // item exists in map
                        Day.put(itemInfo[1], Day.get(itemInfo[1]) + Integer.parseInt(itemInfo[0]));
                    } else {
                        Day.put(itemInfo[1], Integer.parseInt(itemInfo[0]));
                    }
                }
                totalRevenue += order.getBill();
            }
        }

        if (!Day.isEmpty()) {
            System.out.println("Item Name:                       Quantity Sold:");
            for (String key : Day.keySet()) {
                System.out.printf("%-40s%7d\n", key, Day.get(key));
            }
            System.out.printf("Total Revenue:                       %10.2f%n", totalRevenue);
            System.out.print("-----------------------------------------------\n");
            System.out.println();
        }
    }

    public void printMonth(boolean currentMonth) {

        pastOrderDataList = CSVParserOrder.readFromCSV("orderdata.csv");

        Map<String, Integer> Month = new HashMap<>();
        double totalRevenue = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
        if (pastOrderDataList.size() == 0) {
            System.out.println("No report can be found.");
            return;
        }

        if (currentMonth) {
            System.out.println();
            System.out.print("-----------------------------------------------\n");
            LocalDateTime now = LocalDateTime.now();
            String thisMonth = dtf.format(now);
            System.out.printf("              Month: %s               \n", thisMonth);
            for (int i = pastOrderDataList.size(); i > 0; i--) {
                PastOrderData order = pastOrderDataList.get(i - 1);
                String orderDate = order.getTimeClosed().format(dtf);
                if (thisMonth.equals(orderDate)) {
                    for (String item : order.getItemLine()) {
                        String[] itemInfo = item.split("_");
                        if (Month.containsKey(itemInfo[1])) { // item exists in map
                            Month.put(itemInfo[1], Month.get(itemInfo[1]) + Integer.parseInt(itemInfo[0]));
                        } else {
                            Month.put(itemInfo[1], Integer.parseInt(itemInfo[0]));
                        }
                    }
                    totalRevenue += order.getBill();
                } else {
                    System.out.println("Item Name:                       Quantity Sold:");
                    for (String key : Month.keySet()) {
                        System.out.printf("%-40s%7d\n", key, Month.get(key));
                    }
                    System.out.printf("Total Revenue:                       %10.2f%n", totalRevenue);
                    System.out.print("-----------------------------------------------\n");
                    System.out.println();
                    break;
                }
            }
        } else {
            String month = "";
            for (PastOrderData order : pastOrderDataList) {
                if (month.equals("")) {
                    System.out.println();
                    System.out.print("-----------------------------------------------\n");
                    month = order.getTimeClosed().format(dtf);
                    System.out.printf("              Month: %s              \n", month);
                } else if (!(month.equals(order.getTimeClosed().format(dtf)))) { // next month
                    System.out.println("Item Name:                       Quantity Sold:");
                    for (String key : Month.keySet()) {
                        System.out.printf("%-40s%7d\n", key, Month.get(key));
                    }
                    System.out.printf("Total Revenue:                       %10.2f%n", totalRevenue);
                    System.out.print("-----------------------------------------------\n");
                    month = order.getTimeClosed().format(dtf);
                    System.out.printf("             Month: %s              \n", month);
                    Month.clear();
                    totalRevenue = 0;
                }
                for (String item : order.getItemLine()) {
                    String[] itemInfo = item.split("_");
                    if (Month.containsKey(itemInfo[1])) { // item exists in map
                        Month.put(itemInfo[1], Month.get(itemInfo[1]) + Integer.parseInt(itemInfo[0]));
                    } else {
                        Month.put(itemInfo[1], Integer.parseInt(itemInfo[0]));
                    }
                }
                totalRevenue += order.getBill();
            }
        }

        if (!Month.isEmpty()) {
            System.out.println("Item Name:                       Quantity Sold:");
            for (String key : Month.keySet()) {
                System.out.printf("%-40s%7d\n", key, Month.get(key));
            }
            System.out.printf("Total Revenue:                       %10.2f%n", totalRevenue);
            System.out.print("-----------------------------------------------\n");
            System.out.println();
        }
    }

    public void printReport(boolean byMonth) {
        if (byMonth) {
            printMonth(false);
        } else
            printDay(false);
    }
}