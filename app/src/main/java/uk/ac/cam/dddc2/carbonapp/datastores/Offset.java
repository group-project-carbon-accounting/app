package uk.ac.cam.dddc2.carbonapp.datastores;

public class Offset {
    private int vendorID;
    private String vendor;
    private String description;
    private int price;

    public Offset(int vendorID, String vendor, String description, int price) {
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

    public int getPrice() {
        return price;
    }
}
