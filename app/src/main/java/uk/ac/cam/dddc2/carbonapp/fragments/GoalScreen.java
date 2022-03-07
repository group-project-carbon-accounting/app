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

    private ProgressBar progressBarLeft1;
    private ProgressBar progressBarLeft2;
    private ProgressBar progressBarCentre;
    private ProgressBar progressBarRight2;
    private ProgressBar progressBarRight1;

    private TextView progressBarLeft1Label;
    private TextView progressBarLeft2Label;
    private TextView progressBarCentreLabel;
    private TextView progressBarRight2Label;
    private TextView progressBarRight1Label;

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

        progressBarLeft1 = view.findViewById(R.id.progressBarTop);
        progressBarLeft2 = view.findViewById(R.id.progressBarTopCentre);
        progressBarCentre = view.findViewById(R.id.progressBarCentre);
        progressBarRight2 = view.findViewById(R.id.progressBarBottom);
        progressBarRight1 = view.findViewById(R.id.progressBarBottomCentre);

        progressBarLeft1Label = view.findViewById(R.id.progressBarTopText);
        progressBarLeft2Label = view.findViewById(R.id.progressBarTopCentreText);
        progressBarCentreLabel = view.findViewById(R.id.progressBarCentreText);
        progressBarRight2Label = view.findViewById(R.id.progressBarBottomCentreText);
        progressBarRight1Label = view.findViewById(R.id.progressBarBottomText);

        Thread serverRequest = new Thread() {
            @Override
            public void run() {
                UserData userData = Connections.getUserData();
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
                Runnable updateView = new Runnable() {
                    @Override
                    public void run() {
                        String text;
                        if (j == 0) {
                            int maxValue = idealUsers.get(j + 2).getCarbonValue();
                            progressBarLeft1.setVisibility(View.INVISIBLE);
                            progressBarLeft2.setVisibility(View.INVISIBLE);

                            progressBarRight2.setProgress(100);
                            text = idealUsers.get(j + 1).getCombinedLabel();
                            progressBarRight2Label.setText(text);

                            progressBarRight1.setProgress((idealUsers.get(j).getCarbonValue()  * 100) / maxValue);
                            text = idealUsers.get(j).getCombinedLabel();
                            progressBarRight1Label.setText(text);

                            // Dividing by 1000 to go from g -> kg and then * 100 for progressBar as %
                            progressBarCentre.setProgress((currentCarbonValue * 100) / maxValue);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);
                        } else if (j == 1) {
                            int maxValue = idealUsers.get(j + 2).getCarbonValue();
                            progressBarLeft1.setVisibility(View.INVISIBLE);

                            progressBarRight1.setProgress(100);
                            text = idealUsers.get(j + 1).getCombinedLabel();
                            progressBarRight1Label.setText(text);

                            progressBarRight2.setProgress((idealUsers.get(j).getCarbonValue()  * 100) / maxValue);
                            text = idealUsers.get(j).getCombinedLabel();
                            progressBarRight2Label.setText(text);

                            // Dividing by 1000 to go from g -> kg and then * 100 for progressBar as %
                            progressBarCentre.setProgress((currentCarbonValue * 100) / maxValue);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);

                            progressBarLeft2.setProgress((idealUsers.get(0).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(0).getCombinedLabel();
                            progressBarLeft2Label.setText(text);
                        } else if (j == idealUserCount) {
                            int maxValue = currentCarbonValue;
                            progressBarRight1.setVisibility(View.INVISIBLE);
                            progressBarRight2.setVisibility(View.INVISIBLE);

                            progressBarCentre.setProgress(100);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);

                            progressBarLeft1.setProgress((idealUsers.get(j - 2).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 2).getCombinedLabel();
                            progressBarLeft1Label.setText(text);

                            progressBarLeft2.setProgress((idealUsers.get(j - 1).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 1).getCombinedLabel();
                            progressBarLeft2Label.setText(text);
                        } else if (j == idealUserCount - 1) {
                            int maxValue = idealUsers.get(j).getCarbonValue();
                            progressBarRight1.setVisibility(View.INVISIBLE);

                            progressBarCentre.setProgress(100);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);

                            progressBarLeft1.setProgress((idealUsers.get(j - 2).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 2).getCombinedLabel();
                            progressBarLeft1Label.setText(text);

                            progressBarLeft2.setProgress((idealUsers.get(j - 1).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 1).getCombinedLabel();
                            progressBarLeft2Label.setText(text);

                            progressBarRight2.setProgress((idealUsers.get(j).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j).getCombinedLabel();
                            progressBarRight2Label.setText(text);
                        } else {
                            int maxValue = idealUsers.get(j + 1).getCarbonValue();
                            progressBarRight1.setProgress(100);
                            text = idealUsers.get(j + 1).getCombinedLabel();
                            progressBarRight1Label.setText(text);

                            progressBarLeft1.setProgress((idealUsers.get(j - 2).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 2).getCombinedLabel();
                            progressBarLeft1Label.setText(text);

                            progressBarLeft2.setProgress((idealUsers.get(j - 1).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j - 1).getCombinedLabel();
                            progressBarLeft2Label.setText(text);

                            progressBarRight2.setProgress((idealUsers.get(j).getCarbonValue() * 100) / maxValue);
                            text = idealUsers.get(j).getCombinedLabel();
                            progressBarRight2Label.setText(text);

                            progressBarCentre.setProgress((currentCarbonValue * 100) / maxValue);
                            text = "Your footprint: " + currentCarbonValue + "kg";
                            progressBarCentreLabel.setText(text);
                        }
                    }
                };
                getActivity().runOnUiThread(updateView);
            }
        };
        serverRequest.start();
    }
}