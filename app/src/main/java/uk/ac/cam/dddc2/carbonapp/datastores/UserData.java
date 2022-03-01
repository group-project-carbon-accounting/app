package uk.ac.cam.dddc2.carbonapp.datastores;

public class UserData {
    private String name;
    private int carbonCost;
    private int carbonOffset;

    public UserData(String name, int carbonCost, int carbonOffset) {
        this.name = name;
        this.carbonCost = carbonCost;
        this.carbonOffset = carbonOffset;
    }

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
