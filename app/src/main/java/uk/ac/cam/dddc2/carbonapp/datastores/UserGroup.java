package uk.ac.cam.dddc2.carbonapp.datastores;

// Class used for storing ideal user values to compare to in the GoalsScreen class
public class UserGroup {
    private final String percentileLabel;
    private final int carbonValue;

    public UserGroup(String percentileLabel, int carbonValue) {
        this.percentileLabel = percentileLabel;
        this.carbonValue = carbonValue;
    }

    // Getter methods
    public String getPercentileLabel() {
        return percentileLabel;
    }

    public int getCarbonValue() {
        return carbonValue;
    }

    public String getCombinedLabel() {
        return percentileLabel + carbonValue + "kg";
    }
}
