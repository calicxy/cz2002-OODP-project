package entity;

import java.util.ArrayList;

public class PromoSet extends MenuItem {

    private final ArrayList<MenuItem> includedItems;

    public PromoSet(String name, double price, String description, int categoryNo) {
        super(name, price, description, categoryNo);
        this.includedItems = new ArrayList<>();
    }

    public void printItems() {
        int i = 1;
        System.out.println("     This promotional set contains : ");

        for (MenuItem menuItem : includedItems) {
            System.out.printf("     - #%d %s\n", i++, menuItem.getName());
        }
    }

    public void addItem(MenuItem itemToAdd) {
        includedItems.add(itemToAdd);
    }

    public void removeItem(int index) {
        includedItems.remove(index);
    }

    public ArrayList<MenuItem> getIncludedItems() {
        return this.includedItems;
    }

    public int getIncludedItemsListSize() {
        return this.includedItems.size();
    }

    public boolean isEquals(PromoSet set) {

        boolean isContentSame = true;

        for (MenuItem item : set.getIncludedItems()) {
            for (MenuItem item_2 : this.includedItems) {
                if (!item.isEquals(item_2)) {
                    isContentSame = false;
                    break;
                }
            }
        }

        return isContentSame;
    }
}
