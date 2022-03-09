package uk.ac.cam.dddc2.carbonapp.datastores;

// Class used for storing transaction information
public class Transaction {
    private final int transactionID;
    private final int price;
    private int carbonCostOffset;
    private final String vendor;
    private final String timestamp;

    public Transaction(int transactionID, int price, int carbonCostOffset, String vendor, String timestamp) {
        this.transactionID = transactionID;
        this.price = price;
        this.carbonCostOffset = carbonCostOffset;
        this.vendor = vendor;
        this.timestamp = timestamp;
    }

    // Getter methods
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

    // Setter method
    public void setNewCarbonCost(int newCost) {
        // assumes that this function is only called with valid newCost
        carbonCostOffset = newCost;
    }
}
