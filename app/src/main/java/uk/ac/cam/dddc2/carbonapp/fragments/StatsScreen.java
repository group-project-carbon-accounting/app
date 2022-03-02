package uk.ac.cam.dddc2.carbonapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uk.ac.cam.dddc2.carbonapp.R;
import uk.ac.cam.dddc2.carbonapp.TransactionRecyclerAdaptor;
import uk.ac.cam.dddc2.carbonapp.datastores.Transaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsScreen} factory method to
 * create an instance of this fragment.
 */
public class StatsScreen extends Fragment {
    private final ArrayList<Transaction> transactionList = new ArrayList<>();
    private RecyclerView recyclerView;

    public StatsScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransactionInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.transactionListRecyclerView);
        TransactionRecyclerAdaptor adaptor = new TransactionRecyclerAdaptor(transactionList, getActivity().getSupportFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptor);
    }

    private void setTransactionInfo() {
        transactionList.add(new Transaction(1, 10, 15, "Test1", "TestTime1"));
        transactionList.add(new Transaction(2, 20, 25, "Test2", "TestTime2"));
        transactionList.add(new Transaction(3, 30, 35, "Test3", "TestTime3"));
    }
}