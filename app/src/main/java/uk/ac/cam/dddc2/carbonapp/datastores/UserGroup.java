package uk.ac.cam.dddc2.carbonapp.datastores;

public class UserGroup {
    private String percentileLabel;
    private int carbonValue;

    public UserGroup(String percentileLabel, int carbonValue) {
        this.percentileLabel = percentileLabel;
        this.carbonValue = carbonValue;
    }

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
