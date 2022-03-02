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

import uk.ac.cam.dddc2.carbonapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeScreen} factory method to
 * create an instance of this fragment.
 */
public class HomeScreen extends Fragment {

    ProgressBar progressBar;
    int progress = 0;
    ImageView image;

    Button testButton1;

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
        progressBar = view.findViewById(R.id.progressBarMain);
        image = view.findViewById(R.id.imageViewHome);


        testButton1 = view.findViewById(R.id.testButton1);
        testButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = (progress + 10) % 100;
                progressBar.setProgress(progress);
                if (progress == 0) {
                    image.setImageResource(R.drawable.sad);
                }
                if (progress == 50) {
                    image.setImageResource(R.drawable.thumbsup);
                }
            }
        });

    }

}