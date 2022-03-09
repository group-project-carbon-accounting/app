package uk.ac.cam.dddc2.carbonapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import uk.ac.cam.dddc2.carbonapp.Connections;
import uk.ac.cam.dddc2.carbonapp.R;
import uk.ac.cam.dddc2.carbonapp.datastores.UserData;
import uk.ac.cam.dddc2.carbonapp.datastores.UserGroup;


public class GoalScreen extends Fragment {

    // A static list of ideal users with carbonValue in kg
    private static List<UserGroup> idealUsers = Arrays.asList(
            new UserGroup("5th percentile: ", 75),
            new UserGroup("10th percentile: ", 110),
            new UserGroup("15th percentile: ", 135),
            new UserGroup("20th percentile: ", 155),
            new UserGroup("30th percentile: ", 190),
            new UserGroup("40th percentile: ", 210),
            new UserGroup("50th percentile: ", 240),
            new UserGroup("60th percentile: ", 270),
            new UserGroup("70th percentile: ", 290),
            new UserGroup("80th percentile: ", 325),
            new UserGroup("85th percentile: ", 345),
            new UserGroup("90th percentile: ", 370),
            new UserGroup("95th percentile: ", 405)
    );

    public static UserGroup getUserGoal() {
        return idealUsers.get(6);
    }

    private ProgressBar progressBarTop;
    private ProgressBar progressBarTopCentre;
    private ProgressBar progressBarCentre;
    private ProgressBar progressBarBottomCentre;
    private ProgressBar progressBarBottom;

    private TextView progressBarTopLabel;
    private TextView progressBarTopCentreLabel;
    private TextView progressBarCentreLabel;
    private TextView progressBarBottomCentreLabel;
    private TextView progressBarBottomLabel;

    int currentCarbonValue;

    public GoalScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Stores references to the view objects
        progressBarTop = view.findViewById(R.id.progressBarTop);
        progressBarTopCentre = view.findViewById(R.id.progressBarTopCentre);
        progressBarCentre = view.findViewById(R.id.progressBarCentre);
        progressBarBottomCentre = view.findViewById(R.id.progressBarBottomCentre);
        progressBarBottom = view.findViewById(R.id.progressBarBottom);

        progressBarTopLabel = view.findViewById(R.id.progressBarTopText);
        progressBarTopCentreLabel = view.findViewById(R.id.progressBarTopCentreText);
        progressBarCentreLabel = view.findViewById(R.id.progressBarCentreText);
        progressBarBottomCentreLabel = view.findViewById(R.id.progressBarBottomCentreText);
        progressBarBottomLabel = view.findViewById(R.id.progressBarBottomText);

        // Thread for doing the server request and then updating the views
        Thread serverRequest = new Thread() {
            @Override
            public void run() {
                UserData userData = Connections.getUserCarbonData(7);
                currentCarbonValue = (userData.getCarbonCost() - userData.getCarbonOffset()) / 1000;
                int idealUserCount = idealUsers.size();
                int temp = -1;
                for (int i = 0; i < idealUserCount; i++) {
                    if (currentCarbonValue <= idealUsers.get(i).getCarbonValue()) {
                        temp = i;
                        i = idealUserCount;
                    }
                }
                int j;
                if (temp != -1) {
                    j = temp;
                } else {
                    j = idealUserCount;
                }
                // Runnable for updating the fragment views
                Runnable updateView = new Runnable() {
                    @Override
                    public void run() {
                        String text;
                        if (j == 0) {
                            // If the user's carbon usage is less than the first percentile value
                            int maxValue = idealUsers.get(j + 2).getCarbonValue();
                            progressBarTop.setVisibility(View.INVISIBLE);
                            progressBarTopCentre.setVisibility(View.INVISIBLE);

                            progressBarBottom.setProgress(100);
                            text = idealUsers.get(j + 1).getCombinedLabel();
                            progressBarBottomLabel.setText(text);

                            progressBarBottomCentre.setProgress((idealUsers.get(j).getCarbonValue()  * 100) / maxValue);
                            text = idealUsers.get(j).getCombinedLabel();
                            progressBarBottomCentreLabel.setText(text);

                            // Dividing by 1000 to go from g -> kg and then * 100 for progressBar as %
                            progressBarCentre.setProgress((currentCarbonValue * 100) / maxValue);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);
                        } else if (j == 1) {
                            // If the user's carbon usage is only greater than the smallest stored percentile value
                            int maxValue = idealUsers.get(j + 2).getCarbonValue();
                            progressBarTop.setVisibility(View.INVISIBLE);

                            progressBarBottom.setProgress(100);
                            text = idealUsers.get(j + 1).getCombinedLabel();
                            progressBarBottomLabel.setText(text);

                            progressBarBottomCentre.setProgress((idealUsers.get(j).getCarbonValue()  * 100) / maxValue);
                            text = idealUsers.get(j).getCombinedLabel();
                            progressBarBottomCentreLabel.setText(text);

                            // Dividing by 1000 to go from g -> kg and then * 100 for progressBar as %
                            progressBarCentre.setProgress((currentCarbonValue * 100) / maxValue);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);

                            progressBarTopCentre.setProgress((idealUsers.get(0).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(0).getCombinedLabel();
                            progressBarTopCentreLabel.setText(text);
                        } else if (j == idealUserCount) {
                            // If the user's carbon usage is greater than all the stored percentile values
                            int maxValue = currentCarbonValue;
                            progressBarBottom.setVisibility(View.INVISIBLE);
                            progressBarBottomCentre.setVisibility(View.INVISIBLE);

                            progressBarCentre.setProgress(100);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);

                            progressBarTop.setProgress((idealUsers.get(j - 2).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 2).getCombinedLabel();
                            progressBarTopLabel.setText(text);

                            progressBarTopCentre.setProgress((idealUsers.get(j - 1).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 1).getCombinedLabel();
                            progressBarTopCentreLabel.setText(text);
                        } else if (j == idealUserCount - 1) {
                            // If the user's carbon usage is greater than all except the highest stored percentile value
                            int maxValue = idealUsers.get(j).getCarbonValue();
                            progressBarBottom.setVisibility(View.INVISIBLE);

                            progressBarCentre.setProgress(100);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);

                            progressBarTop.setProgress((idealUsers.get(j - 2).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 2).getCombinedLabel();
                            progressBarTopLabel.setText(text);

                            progressBarTopCentre.setProgress((idealUsers.get(j - 1).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 1).getCombinedLabel();
                            progressBarTopCentreLabel.setText(text);

                            progressBarBottomCentre.setProgress((idealUsers.get(j).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j).getCombinedLabel();
                            progressBarBottomCentreLabel.setText(text);
                        } else {
                            // If there are least 2 percentile values below and above the user's carbon usage
                            int maxValue = idealUsers.get(j + 1).getCarbonValue();
                            progressBarBottom.setProgress(100);
                            text = idealUsers.get(j + 1).getCombinedLabel();
                            progressBarBottomLabel.setText(text);

                            progressBarTop.setProgress((idealUsers.get(j - 2).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 2).getCombinedLabel();
                            progressBarTopLabel.setText(text);

                            progressBarTopCentre.setProgress((idealUsers.get(j - 1).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 1).getCombinedLabel();
                            progressBarTopCentreLabel.setText(text);

                            progressBarBottomCentre.setProgress((idealUsers.get(j).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j).getCombinedLabel();
                            progressBarBottomCentreLabel.setText(text);

                            progressBarCentre.setProgress((currentCarbonValue * 100) / maxValue);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);
                        }
                    }
                };
                // Updates the UI on the UI thread as this can't be done from another thread
                getActivity().runOnUiThread(updateView);
            }
        };
        // Starts the server request on another thread as this can't be done on the main thread
        serverRequest.start();
    }
}