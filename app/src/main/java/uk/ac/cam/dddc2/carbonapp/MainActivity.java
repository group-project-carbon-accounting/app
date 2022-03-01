package uk.ac.cam.dddc2.carbonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import uk.ac.cam.dddc2.carbonapp.databinding.ActivityMainBinding;
import uk.ac.cam.dddc2.carbonapp.datastores.Offset;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;

    private ArrayList<Offset> offsetList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        recyclerView = findViewById(R.id.offsetListRecyclerView);
        setOffsetInfo();
        setAdaptor();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }

    private void setOffsetInfo() {
        offsetList.add(new Offset(2, "Test1", "First test offset", 10));
        offsetList.add(new Offset(3, "Test2", "Second test offset", 20));
        offsetList.add(new Offset(4, "Test3", "Third test offset", 30));
    }

    private void setAdaptor() {
        RecyclerAdaptor adaptor = new RecyclerAdaptor(offsetList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptor);
    }

}