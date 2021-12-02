package entity;

public class MenuItemLine {

    private MenuItem menuItem = null;
    private PromoSet promoSet = null;
    private int quantity;

    public MenuItemLine(MenuItem item, int quantity) {
        this.menuItem = item;
        this.quantity = quantity;
    }

    // Overloaded constructor for PromoSet
    public MenuItemLine(PromoSet promoSet, int quantity) {
        this.promoSet = promoSet;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public MenuItem getMenuItem() {
        return this.menuItem;
    }

    public PromoSet getPromoSet() {
        return this.promoSet;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;

        if (this.quantity < 0) {
            this.quantity = 0;
        }
    }

    public double calculatePrice() {

        if (menuItem == null) {
            return promoSet.getPrice() * quantity;
        } else {
            return menuItem.getPrice() * quantity;
        }
    }
}
