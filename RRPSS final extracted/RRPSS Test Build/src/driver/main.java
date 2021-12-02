package driver;

import boundary.*;
import control.*;
import entity.*;
import helper.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

// ****** TO-DO LIST PLEASE READ THIS ******
// 1) Need to check whether reservation in active window before creating order for it
// 2)
// 3) Make UI/Boundary more presentable? 

public class main {

    private static final TableManager tableManager = new TableManager();

    private static final ArrayList<MenuItem> mains = CSVParserMenuItem.readFromCSV("mains.csv");
    private static final ArrayList<MenuItem> desserts = CSVParserMenuItem.readFromCSV("desserts.csv");
    private static final ArrayList<MenuItem> drinks = CSVParserMenuItem.readFromCSV("drinks.csv");
    private static final ArrayList<ArrayList<MenuItem>> menu = new ArrayList<>(Arrays.asList(mains, desserts, drinks));
    private static final ArrayList<PromoSet> promosets = new CSVParserPromoSet(menu).readFromCSV("promosets.csv");
    private static final MenuManager menuManager = new MenuManager(menu, promosets);
    private static final MenuBoundary menuBoundary = new MenuBoundary(menuManager);

    private static final ArrayList<Reservation> reservationList = new CSVParserReservation(tableManager).readFromCSV("reservations.csv");
    private static final ReservationManager reservationManager = new ReservationManager(reservationList, tableManager);
    private static final ReservationBoundary reservationBoundary = new ReservationBoundary(reservationManager, tableManager);

    private static final ArrayList<Staff> staffList = CSVParserStaff.readFromCSV("staff.csv");
    private static final StaffManager staffManager = new StaffManager(staffList);
    private static final StaffBoundary staffBoundary = new StaffBoundary(staffManager);

    private static final ArrayList<Order> openOrderList = new ArrayList<>();
    private static final ArrayList<Order> closedOrderList = new ArrayList<>();
    private static final OrderManager orderManager = new OrderManager(openOrderList, closedOrderList);
    private static final OrderBoundary orderBoundary = new OrderBoundary(menuManager, orderManager, staffManager, reservationManager);

    private static final RevenueReportManager revenueReportManager = new RevenueReportManager();
    private static final RevenueReportBoundary revenueReportBoundary = new RevenueReportBoundary(revenueReportManager);

    public static void main(String[] args) {

        ScannerPlus scannerPlus = new ScannerPlus();

        // Execute ReservationTimeCheck().run() every 60000ms (60s)
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new ReTimer(reservationManager), 0, 60000);

        int userChoice;

        do {
            System.out.println("--------------------------------");
            System.out.println("-       WELCOME TO RRPSS       -");
            System.out.println("--------------------------------");
            System.out.println("[1] View Menu Manager");
            System.out.println("[2] View Staff Manager");
            System.out.println("[3] View Order Manager");
            System.out.println("[4] View Reservation Manager");
            System.out.println("[5] View Revenue Manager");
            System.out.println("[6] Exit RRPSS");

            userChoice = scannerPlus.nextIntRange("Enter choice", 1, 6);

            switch (userChoice) {
                case 1 -> menuBoundary.init();
                case 2 -> staffBoundary.init();
                case 3 -> orderBoundary.init();
                case 4 -> reservationBoundary.init();
                case 5 -> revenueReportBoundary.init();
                case 6 -> {
                    if (openOrderList.size() != 0) {
                        System.out.println("ERROR. There are still active/open orders.");
                        userChoice = 0;
                    } else {
                        System.out.println("Thank you for using the application. Exiting...");
                    }
                }
            }
        } while (userChoice != 6);

        try {
            saveData();
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    /**
     * Writes information of all staff in this list into a .csv file.
     *
     * @throws IOException If an I/O error occurs
     */
    private static void saveData() throws IOException {
        String data;

        // Saving data for staff list
        FileWriter CSVWriter = new FileWriter("staff.csv");
        for (Staff staff : staffList) {
            data = String.format("%s,%d,%s,%d,%s\n",
                    staff.getName(),
                    staff.getContactNo(),
                    staff.getJobTitle(),
                    staff.getEmployeeID(),
                    staff.getGender().name());
            CSVWriter.append(data);
        }

        CSVWriter.flush();
        CSVWriter.close();

        // Saving data for promotional sets
        CSVWriter = new FileWriter("promosets.csv");
        for (PromoSet set : promosets) {
            data = String.format("%s=%f=%s=%d",
                    set.getName(),
                    set.getPrice(),
                    set.getDescription(),
                    set.getCategoryNo());

            for (MenuItem menuItem : set.getIncludedItems()) {
                data = data.concat(String.format("=%dXXX%s", menuItem.getCategoryNo(), menuItem.getName()));
            }

            data = data.concat("=###\n");
            CSVWriter.append(data);
        }

        CSVWriter.flush();
        CSVWriter.close();

        // Saving data for individual menus
        List<String> fileNames = Arrays.asList("mains.csv", "desserts.csv", "drinks.csv");
        int i = 0;
        for (String fileName : fileNames) {
            CSVWriter = new FileWriter(fileName);

            for (MenuItem menuItem : menu.get(i)) {
                data = String.format("%s=%f=%s=%d\n",
                        menuItem.getName(),
                        menuItem.getPrice(),
                        menuItem.getDescription(),
                        menuItem.getCategoryNo());
                CSVWriter.append(data);
            }

            i++;

            CSVWriter.flush();
            CSVWriter.close();
        }

        // Saving data for reservations
        CSVWriter = new FileWriter("reservations.csv");
        for (Reservation re : reservationList) {
            data = String.format("%d,%s,%s,%d,%s,%s,%d,%d\n",
                    re.getTable().getTableId(),
                    re.dateTimeToString(re.getReservationTime()),
                    re.getCustomer().getName(),
                    re.getCustomer().getContactNo(),
                    re.getCustomer().getMembership(),
                    re.getCustomer().getGender().name(),
                    re.getPax(),
                    re.getTable().getCapacity());
            CSVWriter.append(data);
        }
        CSVWriter.flush();
        CSVWriter.close();
    }
}
