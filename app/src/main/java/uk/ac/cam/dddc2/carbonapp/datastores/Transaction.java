package uk.ac.cam.dddc2.carbonapp.datastores;

public class Transaction {
    private int transactionID;
    private float price;
    private int carbonCostOffset;
    private String vendor;
    private String timestamp;

    public Transaction(int transactionID, float price, int carbonCostOffset, String vendor, String timestamp) {
        this.transactionID = transactionID;
        this.price = price;
        this.carbonCostOffset = carbonCostOffset;
        this.vendor = vendor;
        this.timestamp = timestamp;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public float getPrice() {
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
}
