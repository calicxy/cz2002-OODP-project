package control;

import entity.MenuItem;
import entity.PromoSet;

import java.util.ArrayList;

public class MenuManager {

    private final ArrayList<ArrayList<MenuItem>> menu;
    private final ArrayList<PromoSet> promosets;

    public MenuManager(ArrayList<ArrayList<MenuItem>> menu, ArrayList<PromoSet> promosets) {
        this.menu = menu;
        this.promosets = promosets;
    }

    public void addItem(int category, MenuItem newItem) {
        for (MenuItem item : menu.get(category)) {
            if (item.isEquals(newItem)) {
                System.out.println("\n* Error! Menu item already exists. *");
                return;
            }
        }
        menu.get(category).add(newItem);
    }

    // Method Overloading
    public void addItem(PromoSet set) {
        for (PromoSet item : promosets) {
            if (item.isEquals(set)) {
                System.out.println("\n* Error! Promotional set already exists. *");
                return;
            }
        }
        promosets.add(set);
    }

    public void removeItem(int category, int index) {
        menu.get(category).remove(index);
    }

    // Method Overloading
    public void removeItem(int index) {
        promosets.remove(index);
    }

    public void printMenu(int category) {
        int i = 1;
        System.out.printf("\n---- %-30s %-8s\n", "NAME", "PRICE");

        if (category == 3) {
            for (PromoSet set : promosets) {
                System.out.printf("(%-2d) %s\n", i++, set);
                set.printItems();
                System.out.println();
            }
        } else {
            for (MenuItem item : menu.get(category)) {
                System.out.printf("(%-2d) %s\n", i++, item);
                System.out.println();
            }
        }
    }

    public void updateName(int category, int index, String newName) {
        menu.get(category).get(index).setName(newName);
    }

    // Method Overloading
    public void updateName(int index, String newName) {
        promosets.get(index).setName(newName);
    }

    public void updatePrice(int category, int index, double newPrice) {
        menu.get(category).get(index).setPrice(newPrice);
    }

    // Method Overloading
    public void updatePrice(int index, double newPrice) {
        promosets.get(index).setPrice(newPrice);
    }

    public void updateDescription(int category, int index, String newDescription) {
        menu.get(category).get(index).setDescription(newDescription);
    }

    // Method Overloading
    public void updateDescription(int index, String newDescription) {
        promosets.get(index).setDescription(newDescription);
    }

    public PromoSet getPromoSet(int index) {
        return promosets.get(index);
    }

    public MenuItem getMenuItem(int categoryNo, int index) {
        return menu.get(categoryNo).get(index);
    }

    public int getMenuListLength(int categoryNo) {
        if (categoryNo == 3) {
            return promosets.size();
        }
        return menu.get(categoryNo).size();
    }
}