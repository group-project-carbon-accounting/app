package uk.ac.cam.dddc2.carbonapp.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;

import uk.ac.cam.dddc2.carbonapp.Connections;
import uk.ac.cam.dddc2.carbonapp.R;
import uk.ac.cam.dddc2.carbonapp.datastores.UserData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeScreen} factory method to
 * create an instance of this fragment.
 */
public class HomeScreen extends Fragment {

    ProgressBar progressBar;
    TextView progressLabel;
    AtomicReference<String> progressText;
    int progress = 0;
    int goal = GoalScreen.getUserGoal().getCarbonValue();
    ImageView image;
    Activity a;

    public HomeScreen() {
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
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceStates) {
        super.onViewCreated(view, savedInstanceStates);
        a = (Activity) getContext();
        progressBar = view.findViewById(R.id.progressBarMain);
        progressLabel = view.findViewById(R.id.progressLabel);
        progressBar.setProgress(progress);
        progressText = new AtomicReference<>(progress + "kg / " + goal + "kg");
        progressLabel.setText(progressText.get());

        Thread serverRequest = new Thread() {
            @Override
            public void run() {
                UserData userData = Connections.getUserData();
                progress = (userData.getCarbonCost() - userData.getCarbonOffset()) / 1000;
                updateProgress();
            }
        };
        serverRequest.setDaemon(true);
        serverRequest.start();




        image = view.findViewById(R.id.imageViewHome);
    }

    public void updateProgress() {
        Runnable updateView = new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(progress % 100);
                progressText.set(progress + "kg / " + goal + "kg");
                progressLabel.setText(progressText.get());
                if (progress >= 50) {
                    image.setImageResource(R.drawable.sad);
                }
                if (progress < 50) {
                    image.setImageResource(R.drawable.thumbsup);
                }
            }
        };
        a.runOnUiThread(updateView);

    }

}