package boundary;

import control.MenuManager;
import entity.MenuItem;
import entity.PromoSet;
import helper.ScannerPlus;

import java.util.Scanner;

public class MenuBoundary {

    private final MenuManager menuManager;
    private final ScannerPlus scannerPlus = new ScannerPlus();
    private final Scanner sc = new Scanner(System.in);

    public MenuBoundary(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public void init() {
        int choice;

        do {
            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("-         MENU MANAGER         -");
            System.out.println("--------------------------------");
            System.out.println("[1] Create New Item");
            System.out.println("[2] Remove Item");
            System.out.println("[3] Update Item");
            System.out.println("[4] View Menu");
            System.out.println("[5] Exit");

            choice = scannerPlus.nextIntRange("Enter choice", 1, 5);

            switch (choice) {
                case 1 -> addItem();
                case 2 -> removeItem();
                case 3 -> updateItem();
                case 4 -> printMenu();
                case 5 -> System.out.println("Returning...");
            }
        } while (choice != 5);

        System.out.println();
    }

    public void addItem() {
        System.out.println();
        System.out.println("Please select category of new menu item.");
        System.out.println("[1] Main Course");
        System.out.println("[2] Dessert");
        System.out.println("[3] Drink");
        System.out.println("[4] Promotional Set");

        int categoryNo = scannerPlus.nextIntRange("Enter choice", 1, 4) - 1;

        if (categoryNo == 3) {
            addPromotionalItem();
            return;
        }

        System.out.print("Enter new item name :: ");
        String itemName = sc.nextLine();

        double itemPrice = scannerPlus.nextDouble("Enter item price");

        System.out.print("Enter item description :: ");
        String itemDescription = sc.nextLine();

        menuManager.addItem(categoryNo, new MenuItem(itemName, itemPrice, itemDescription, categoryNo));
    }

    public void removeItem() {
        System.out.println();
        System.out.println("Please select category of item to remove.");
        System.out.println("[1] Main Course");
        System.out.println("[2] Dessert");
        System.out.println("[3] Drink");
        System.out.println("[4] Promotional Set");

        int categoryNo = scannerPlus.nextIntRange("Enter choice (0 to abort)", 0, 4) - 1;

        if (categoryNo == -1) {
            System.out.println("Successfully aborted.");
            return;
        }

        if (menuManager.getMenuListLength(categoryNo) == 0) {
            System.out.println("There are no items in this category.");
            return;
        }

        menuManager.printMenu(categoryNo);

        int itemToRemove = scannerPlus.nextIntRange("Enter item choice", 0, menuManager.getMenuListLength(categoryNo)) - 1;

        if (categoryNo == 3) {
            menuManager.removeItem(itemToRemove);
            return;
        }

        menuManager.removeItem(categoryNo, itemToRemove);
    }

    public void updateItem() {
        System.out.println();
        System.out.println("Please select category of item to update.");
        System.out.println("[1] Main Course");
        System.out.println("[2] Dessert");
        System.out.println("[3] Drink");
        System.out.println("[4] Promotional Set");

        int categoryNo = scannerPlus.nextIntRange("Enter choice", 1, 4) - 1;

        if (menuManager.getMenuListLength(categoryNo) == 0) {
            System.out.println("There are no items in this category.");
            return;
        }

        menuManager.printMenu(categoryNo);

        int itemChoice = scannerPlus.nextIntRange("Enter item choice", 0, menuManager.getMenuListLength(categoryNo)) - 1;

        if (categoryNo == 3) {
            updatePromotionalSet(itemChoice);
            return;
        }

        System.out.println("Please select attribute to update.");
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("3. Description");

        int userChoice = scannerPlus.nextIntRange("Enter choice", 1, 3);

        switch (userChoice) {
            case 1 -> {
                System.out.print("Enter new item name :: ");
                String newName = sc.nextLine();
                menuManager.updateName(categoryNo, itemChoice, newName);
            }
            case 2 -> {
                double newPrice = scannerPlus.nextDouble("Enter new item price");
                menuManager.updatePrice(categoryNo, itemChoice, newPrice);
            }
            case 3 -> {
                System.out.print("Enter new item description :: ");
                String newDescription = sc.nextLine();
                menuManager.updateDescription(categoryNo, itemChoice, newDescription);
            }
        }
    }

    public void printMenu() {

        System.out.println();
        System.out.println("Please select category:");
        System.out.println("[1] Main Course");
        System.out.println("[2] Dessert");
        System.out.println("[3] Drink");
        System.out.println("[4] Promotional Set");

        int categoryNo = scannerPlus.nextIntRange("Enter choice", 1, 4) - 1;
        menuManager.printMenu(categoryNo);
    }

    public void addPromotionalItem() {
        System.out.print("Enter new promo set name :: ");
        String name = sc.nextLine();

        double price = scannerPlus.nextDouble("Enter promo set price");

        System.out.print("Enter promo set description :: ");
        String description = sc.nextLine();

        PromoSet tempPromoSet = new PromoSet(name, price, description, 3);

        while (true) {
            System.out.println();
            System.out.println("Select category of item to add to promo set.");
            System.out.println("[1] Main Course");
            System.out.println("[2] Dessert");
            System.out.println("[3] Drink");

            int categoryNo = scannerPlus.nextIntRange("Enter choice (exit with 0)", 0, 3) - 1;

            if (categoryNo == -1) {
                break;
            }

            if (menuManager.getMenuListLength(categoryNo) == 0) {
                System.out.println("There are no items in this category.");
                return;
            }

            menuManager.printMenu(categoryNo);
            int itemChoice = scannerPlus.nextIntRange("Enter item choice", 1, menuManager.getMenuListLength(categoryNo)) - 1;
            tempPromoSet.addItem(menuManager.getMenuItem(categoryNo, itemChoice));
        }
        menuManager.addItem(tempPromoSet);
    }

    public void updatePromotionalSet(int itemChoice) {

        System.out.println("Please select attribute to update.");
        System.out.println("1. Name");
        System.out.println("2. Price");
        System.out.println("3. Description");
        System.out.println("4. Add New Item to Promo Set");
        System.out.println("5. Remove Item from Promo Set");

        int userChoice = scannerPlus.nextIntRange("Enter choice", 1, 5);
        PromoSet promoSet;

        switch (userChoice) {
            case 1 -> {
                System.out.print("Enter new set name :: ");
                String newName = sc.nextLine();
                menuManager.updateName(itemChoice, newName);
            }
            case 2 -> {
                double newPrice = scannerPlus.nextDouble("Enter new set price");
                menuManager.updatePrice(itemChoice, newPrice);
            }
            case 3 -> {
                System.out.print("Enter new set description :: ");
                String newDescription = sc.nextLine();
                menuManager.updateDescription(itemChoice, newDescription);
            }
            case 4 -> {
                System.out.println("Select category of item to add to promo set.");
                System.out.println("[1] Main Course");
                System.out.println("[2] Dessert");
                System.out.println("[3] Drink");
                int categoryNo = scannerPlus.nextIntRange("Enter choice", 1, 3) - 1;
                menuManager.printMenu(categoryNo);
                int newItemChoice = scannerPlus.nextIntRange("Enter item choice", 0, menuManager.getMenuListLength(categoryNo)) - 1;
                promoSet = menuManager.getPromoSet(itemChoice);
                promoSet.addItem(menuManager.getMenuItem(categoryNo, newItemChoice));
            }
            case 5 -> {
                promoSet = menuManager.getPromoSet(itemChoice);
                promoSet.printItems();

                if (promoSet.getIncludedItemsListSize() == 0) {
                    System.out.println("There are no items in this promotional set.");
                    return;
                }

                int itemToRemove = scannerPlus.nextIntRange("Enter item choice", 0, promoSet.getIncludedItemsListSize()) - 1;
                promoSet.removeItem(itemToRemove);
            }
        }
    }
}