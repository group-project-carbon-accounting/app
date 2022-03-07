package uk.ac.cam.dddc2.carbonapp.datastores;

public class Transaction {
    private int transactionID;
    private int price;
    private int carbonCostOffset;
    private String vendor;
    private String timestamp;

    public Transaction(int transactionID, int price, int carbonCostOffset, String vendor, String timestamp) {
        this.transactionID = transactionID;
        this.price = price;
        this.carbonCostOffset = carbonCostOffset;
        this.vendor = vendor;
        this.timestamp = timestamp;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public int getPrice() {
        return price;
    }

    public int getCarbonCostOffset() {
        return carbonCostOffset;
    }

    public String getVendor() {
        return vendor;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setNewCarbonCost(int newCost) {
        // assumes that this function is only called with valid newCost
        carbonCostOffset = newCost;
    }
}
