package uk.ac.cam.dddc2.carbonapp.datastores;

// Class used for storing Offset information
public class Offset {
    private final int vendorID;
    private final String vendor;
    private final String description;
    private final int price;

    public Offset(int vendorID, String vendor, String description, int price) {
        this.vendorID = vendorID;
        this.vendor = vendor;
        this.description = description;
        this.price = price;
    }

    // Getter methods
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
