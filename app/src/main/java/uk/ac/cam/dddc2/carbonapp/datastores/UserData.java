package uk.ac.cam.dddc2.carbonapp.datastores;

// Class used for storing information about the user
public class UserData {
    private final String name;
    private final int carbonCost;
    private final int carbonOffset;

    public UserData(String name, int carbonCost, int carbonOffset) {
        this.name = name;
        this.carbonCost = carbonCost;
        this.carbonOffset = carbonOffset;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getCarbonCost() {
        return carbonCost;
    }

    public int getCarbonOffset() {
        return carbonOffset;
    }
}
