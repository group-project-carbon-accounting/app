package uk.ac.cam.dddc2.carbonapp.datastores;

public class Offset {
    private int vendorID;
    private String vendor;
    private String description;
    private float price;

    public Offset(int vendorID, String vendor, String description, float price) {
        this.vendorID = vendorID;
        this.vendor = vendor;
        this.description = description;
        this.price = price;
    }

    public int getVendorID() {
        return vendorID;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }
}
