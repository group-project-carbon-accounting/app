package uk.ac.cam.dddc2.carbonapp.fragments;

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
    int progress = 0;
    int goal = 100;
    ImageView image;

    Button testButton1;

    public HomeScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread serverRequest = new Thread() {
            @Override
            public void run() {
                UserData userData = Connections.getUserData();
                progress = (userData.getCarbonCost() - userData.getCarbonOffset()) / goal;

            }
        };
        serverRequest.setDaemon(true);
        serverRequest.start();
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
        progressBar = view.findViewById(R.id.progressBarMain);
        progressLabel = view.findViewById(R.id.progressLabel);
        progressBar.setProgress(progress);
        AtomicReference<String> progressText = new AtomicReference<>(progress + "g / " + goal + 'g');
        progressLabel.setText(progressText.get());




        image = view.findViewById(R.id.imageViewHome);


        testButton1 = view.findViewById(R.id.testButton1);
        testButton1.setOnClickListener(view1 -> {
            progress = (progress + 10) % 100;
            progressText.set(progress + "g / " + goal + 'g');
            progressLabel.setText(progressText.get());
            progressBar.setProgress(progress);
            if (progress >= 50) {
                image.setImageResource(R.drawable.sad);
            }
            if (progress < 50) {
                image.setImageResource(R.drawable.thumbsup);
            }
        });

    }

}