package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PastOrderData {

    LocalDateTime timeClosed;
    double bill;
    ArrayList<String> itemLine;

    public PastOrderData(LocalDateTime timeClosed, double bill, ArrayList<String> itemLine) {
        this.timeClosed = timeClosed;
        this.bill = bill;
        this.itemLine = itemLine;
    }

    public LocalDateTime getTimeClosed() {
        return this.timeClosed;
    }

    public ArrayList<String> getItemLine() {
        return this.itemLine;
    }

    public double getBill() {
        return this.bill;
    }
}
