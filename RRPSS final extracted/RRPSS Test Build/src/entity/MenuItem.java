package entity;

public class MenuItem {

    private final int categoryNo;
    private String name;
    private double price;
    private String description;

    public MenuItem(String name, double price, String description, int categoryNo) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryNo = categoryNo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryNo() {
        return this.categoryNo;
    }

    @Override
    public String toString() {
        return String.format("%-30s $%-7.2f \n"
                        + "     %s",
                this.name, this.price, this.description);
    }

    public boolean isEquals(MenuItem item) {
        return (this.getName().equalsIgnoreCase(item.getName())) &&
                (this.getPrice() == item.getPrice()) &&
                (this.getDescription().equalsIgnoreCase(item.getDescription())) &&
                (this.getCategoryNo() == item.getCategoryNo());
    }
}