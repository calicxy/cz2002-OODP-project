package boundary;

import control.RevenueReportManager;
import helper.ScannerPlus;

public class RevenueReportBoundary {

    private static final ScannerPlus scannerPlus = new ScannerPlus();
    private final RevenueReportManager revenueReportManager;

    public RevenueReportBoundary(RevenueReportManager revenueReportManager) {
        this.revenueReportManager = revenueReportManager;
    }

    public void init() {
        int choice;
        String message;
        do {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("-        REVENUE MANAGER       -");
            System.out.println("--------------------------------");

            message = ("""
                    [1] View Today's Revenue Report
                    [2] View This Month's Revenue Report
                    [3] View Revenue Report by Days
                    [4] View Revenue Report by Months
                    [5] Exit Revenue Manager
                    Select option""");
            choice = scannerPlus.nextIntRange(message, 1, 5);
            switch (choice) {
                case 1 -> revenueReportManager.printDay(true);
                case 2 -> revenueReportManager.printMonth(true);
                case 3 -> revenueReportManager.printReport(false);
                case 4 -> revenueReportManager.printReport(true);
                case 5 -> System.out.println("Returning...\n");
            }
        } while (choice != 5);
    }
}