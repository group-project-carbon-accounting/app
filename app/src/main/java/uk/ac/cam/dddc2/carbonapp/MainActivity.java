package uk.ac.cam.dddc2.carbonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import uk.ac.cam.dddc2.carbonapp.databinding.ActivityMainBinding;
import uk.ac.cam.dddc2.carbonapp.fragments.GoalScreen;
import uk.ac.cam.dddc2.carbonapp.fragments.HomeScreen;
import uk.ac.cam.dddc2.carbonapp.fragments.OffsetScreen;
import uk.ac.cam.dddc2.carbonapp.fragments.StatsScreen;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Creates the fragments and the navigation bar
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        HomeScreen homeScreen = new HomeScreen();
        GoalScreen goalScreen = new GoalScreen();
        StatsScreen statsScreen = new StatsScreen();
        OffsetScreen offsetScreen = new OffsetScreen();
        replaceFragment(homeScreen);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.homeScreen:
                    replaceFragment(homeScreen);
                    break;
                case R.id.goalScreen:
                    replaceFragment(goalScreen);
                    break;
                case R.id.statsScreen:
                    replaceFragment(statsScreen);
                    break;
                case R.id.offsetScreen:
                    replaceFragment(offsetScreen);
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}